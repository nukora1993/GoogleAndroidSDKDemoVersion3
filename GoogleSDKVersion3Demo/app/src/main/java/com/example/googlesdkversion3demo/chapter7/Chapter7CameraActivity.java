package com.example.googlesdkversion3demo.chapter7;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.googlesdkversion3demo.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class Chapter7CameraActivity extends AppCompatActivity implements
        SurfaceHolder.Callback {

    private Camera camera;
    private Button button1,button2,button3;

    private ImageView imageView;
    private TextView textView;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;

    private boolean isPreview;

    private int numberOfCameras;
    private Camera.CameraInfo cameraInfo;
    private int defaultCameraId;
    private int facingCamerId;
    private int cameraCurrentlyLocked;
    private int screenRotation;
    private Camera.Size previewSize;
    private List<Camera.Size> supportedPreviewSizes;


    private Camera.ShutterCallback shutterCallback=new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            //快门关闭
        }
    };

    private Camera.PictureCallback rawCallback=new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //处理raw data
        }
    };

    //处理拍照jpeg数据，将jpeg数据压缩并保存到指定路径
    private Camera.PictureCallback jpegCallback=new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);
            File captureFile=new File(captureFilePath);
            try{
                BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(
                        new FileOutputStream(captureFile)
                );
                //压缩并存储
                bitmap.compress(Bitmap.CompressFormat.JPEG,80,bufferedOutputStream);
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                imageView.setImageBitmap(bitmap);
                resetCamera();
                initCamera();

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    };

    private Camera.AutoFocusCallback autoFocusCallback=new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            if(success){
                takePicture();
            }
        }
    };


    private String captureFilePath= Environment.getExternalStorageDirectory()+"/1.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CAMERA},0);
            }
        }
        super.onCreate(savedInstanceState);
        //对AppCompatActivity无效
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chapter7_camera);

        //获取相机信息
        getCameraInfo();
        //获取屏幕参数
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        textView=findViewById(R.id.text_view);
        imageView=findViewById(R.id.image_view);

        surfaceView=findViewById(R.id.surface_view);

        surfaceHolder=surfaceView.getHolder();

        surfaceHolder.addCallback(this);

        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        button1=findViewById(R.id.button_1);
        button2=findViewById(R.id.button_2);
        button3=findViewById(R.id.button_3);

        //初始化
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCamera();
            }
        });

        //重置
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCamera();
            }
        });

        //拍照
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });




    }

    //遍历，获得最后一个后置相机和最后一个前置
    private void getCameraInfo(){
        //相机数量，在s10上显示4个
        numberOfCameras= Camera.getNumberOfCameras();
        //cameraInfo包含了正反面，能否关闭快门声音和方向信息
        //back=0，front=1
        cameraInfo=new Camera.CameraInfo();
        for (int i = 0; i <numberOfCameras ; i++) {
            Camera.getCameraInfo(i,cameraInfo);
            if(cameraInfo.facing== Camera.CameraInfo.CAMERA_FACING_BACK){
                defaultCameraId=i;
            }else if(cameraInfo.facing== Camera.CameraInfo.CAMERA_FACING_FRONT){
                facingCamerId=i;
            }
        }
    }

    private void initCamera(){
        if(!isPreview){
            try{
                cameraCurrentlyLocked=defaultCameraId;
                //很古怪，这里干嘛要加1，后置相机+1变成了前置相机，还不如直接打开指定id
//                camera=Camera.open((cameraCurrentlyLocked+1)%numberOfCameras);
                camera=Camera.open(cameraCurrentlyLocked);
                setCameraDisplayOrientation(camera);
//                cameraCurrentlyLocked=(cameraCurrentlyLocked+1)%numberOfCameras;
                //自动对焦
                camera.autoFocus(autoFocusCallback);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if(camera!=null&&!isPreview){
            //获得相机支持的尺寸，在S10上支持13种
            supportedPreviewSizes=camera.getParameters().getSupportedPreviewSizes();
            if(supportedPreviewSizes!=null){
                //根据给定尺寸，获得最接近的尺寸
                previewSize=getOptimalPreviewSize(supportedPreviewSizes,320,240);
                Camera.Parameters parameters=camera.getParameters();
                //设定尺寸
                parameters.setPreviewSize(
                        previewSize.width,previewSize.height
                );
                //设定格式
                parameters.setPictureFormat(PixelFormat.JPEG);

                camera.setParameters(parameters);

                try{
                    //camera绑定surfaceview
                    camera.setPreviewDisplay(surfaceHolder);
                }catch(Exception e){
                    e.printStackTrace();
                }

                camera.startPreview();
                isPreview=true;
            }

        }


    }

    private void takePicture(){
        if(camera!=null&&isPreview){
            camera.takePicture(shutterCallback,rawCallback,jpegCallback);
        }
    }

    private void resetCamera(){
        if(camera!=null&&isPreview){
            camera.stopPreview();
            camera.release();
            camera=null;
            isPreview=false;
        }
    }

    private void setCameraDisplayOrientation(Camera camera){
        //屏幕方向
        screenRotation=getWindowManager().getDefaultDisplay().getOrientation();

        int degrees=0;
        int result;

        switch(screenRotation){
            case Surface.ROTATION_0:{
                degrees=0;
                break;
            }
            case Surface.ROTATION_90:{
                degrees=90;
                break;
            }
            case Surface.ROTATION_180:{
                degrees=180;
                break;
            }
            case Surface.ROTATION_270:{
                degrees=270;
                break;
            }
        }
        cameraInfo=new Camera.CameraInfo();
        //这里也很古怪，为什么遍历一遍，不是应该根据指定的camera的相机设置么
//        for (int i = 0; i <numberOfCameras ; i++) {
//            if(cameraInfo.facing== Camera.CameraInfo.CAMERA_FACING_FRONT){
//                result=(cameraInfo.orientation+degrees)%360;
//                result=(360-result)%360;
//            }else{
//                result=(cameraInfo.orientation-degrees+360)%360;
//            }
//
//
//        }
        Camera.getCameraInfo(cameraCurrentlyLocked,cameraInfo);
        result=(cameraInfo.orientation+degrees)%360;

        //设置图片的旋转角度
        camera.setDisplayOrientation(result);


    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h){
        //容忍的宽高比差距
        final double ASPECT_TOLERANCE=0.1;
        //给定的宽高比
        double targetRatio=(double)w/h;
        if(sizes==null) return null;

        Camera.Size optimalSize=null;
        double minDiff=Double.MAX_VALUE;

        int targetHeight=h;

        for(Camera.Size size:sizes){
            double ratio=(double)size.width/size.height;
            //首先找到最接近给定宽高比的size
            if(Math.abs(ratio-targetRatio)>ASPECT_TOLERANCE) continue;
            //在满足宽高比的前提下找到高度差距最小的size
            if(Math.abs(size.height-targetHeight)<minDiff){
                optimalSize=size;
                minDiff=Math.abs(size.height-targetHeight);
            }
        }

        //如果没有找到指定宽高比，那么找到最接近指定高度的size
        if(optimalSize==null){
            minDiff=Double.MAX_VALUE;
            for(Camera.Size size:sizes){
                if(Math.abs(optimalSize.height-targetHeight)<minDiff){
                    optimalSize=size;
                    minDiff=Math.abs(size.height-targetHeight);
                }
            }
        }

        return optimalSize;
    }

    private void delFile(String fileName){
        try{
            File file=new File(fileName);
            if(file.exists()){
                file.delete();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //implement surfaceHolder.callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try{
            if(camera!=null){
                camera.setPreviewDisplay(surfaceHolder);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try{
            delFile(captureFilePath);
            camera.stopPreview();
            camera.release();
            camera=null;

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    protected void onPause() {
        try{
            resetCamera();
            camera.release();
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onPause();
    }
}
