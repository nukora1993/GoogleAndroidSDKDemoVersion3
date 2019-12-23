package com.example.googlesdkversion3demo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.googlesdkversion3demo.chapter3.Chapter3GalleryActivity;
import com.example.googlesdkversion3demo.chapter3.Chapter3MainActivity;
import com.example.googlesdkversion3demo.chapter3.Chapter3ViewFlipperActivity;
import com.example.googlesdkversion3demo.chapter4.Chapter4ClockActivity;
import com.example.googlesdkversion3demo.chapter4.Chapter4DatePickerActivity;
import com.example.googlesdkversion3demo.chapter4.Chapter4DecodeFileActivity;
import com.example.googlesdkversion3demo.chapter4.Chapter4FileExplorerListActivity;
import com.example.googlesdkversion3demo.chapter4.Chapter4GridViewActivity;
import com.example.googlesdkversion3demo.chapter4.Chapter4ImageRotateActivity;
import com.example.googlesdkversion3demo.chapter4.Chapter4ImageScaleActivity;
import com.example.googlesdkversion3demo.chapter4.Chapter4InputDeviceActivity;
import com.example.googlesdkversion3demo.chapter4.Chapter4KeyActivity;
import com.example.googlesdkversion3demo.chapter4.Chapter4ListActivity;
import com.example.googlesdkversion3demo.chapter4.Chapter4ListViewActivity;
import com.example.googlesdkversion3demo.chapter4.Chapter4ProgressBarActivity;
import com.example.googlesdkversion3demo.chapter4.Chapter4ScrollActivity;
import com.example.googlesdkversion3demo.chapter4.Chapter4SearchActivity;
import com.example.googlesdkversion3demo.chapter4.Chapter4SlidingDrawerLayout;
import com.example.googlesdkversion3demo.chapter4.Chapter4SpinnerActivity;
import com.example.googlesdkversion3demo.chapter4.Chapter4UIAppWidgetProvider;
import com.example.googlesdkversion3demo.chapter5.Chapter5AutoCompleteContactActivity;
import com.example.googlesdkversion3demo.chapter5.Chapter5ClipBoardActivity;
import com.example.googlesdkversion3demo.chapter5.Chapter5DeviceManagerActivity;
import com.example.googlesdkversion3demo.chapter5.Chapter5FileExplorer;
import com.example.googlesdkversion3demo.chapter5.Chapter5GestureActivity;
import com.example.googlesdkversion3demo.chapter5.Chapter5GetContactActivity;
import com.example.googlesdkversion3demo.chapter5.Chapter5LinkifyActivity;
import com.example.googlesdkversion3demo.chapter5.Chapter5OrientationActivity;
import com.example.googlesdkversion3demo.chapter5.Chapter5PackageManagerActivity;
import com.example.googlesdkversion3demo.chapter5.Chapter5ProgressNotificationActivity;
import com.example.googlesdkversion3demo.chapter5.Chapter5ShellActivity;
import com.example.googlesdkversion3demo.chapter5.Chapter5SmsActivity;
import com.example.googlesdkversion3demo.chapter5.Chapter5TasksActivity;
import com.example.googlesdkversion3demo.chapter5.Chapter5TelephonyActivity;
import com.example.googlesdkversion3demo.chapter5.Chapter5ToastUIActivity;
import com.example.googlesdkversion3demo.chapter5.Chapter5VibratorActivity;
import com.example.googlesdkversion3demo.chapter5.Chapter5WallPaperActivity;
import com.example.googlesdkversion3demo.chapter5.Chapter5WifiActivity;
import com.example.googlesdkversion3demo.chapter5.Chapter5WifiConnectActivity;
import com.example.googlesdkversion3demo.chapter6.Chapter6AudioManagerActivity;
import com.example.googlesdkversion3demo.chapter6.Chapter6BatteryActivity;
import com.example.googlesdkversion3demo.chapter6.Chapter6GesturePredictionActivity;
import com.example.googlesdkversion3demo.chapter6.Chapter6PhoneCallActivity;
import com.example.googlesdkversion3demo.chapter6.Chapter6SendSmsActivity;
import com.example.googlesdkversion3demo.chapter6.Chapter6SensorActivity;
import com.example.googlesdkversion3demo.chapter6.Chapter6SmsServiceActivity;
import com.example.googlesdkversion3demo.chapter7.Chapter7AudioVolumeActivity;
import com.example.googlesdkversion3demo.chapter7.Chapter7BitmapDrawableActivity;
import com.example.googlesdkversion3demo.chapter7.Chapter7CameraActivity;
import com.example.googlesdkversion3demo.chapter7.Chapter7CustomButtonActivity;
import com.example.googlesdkversion3demo.chapter7.Chapter7ExifActivity;
import com.example.googlesdkversion3demo.chapter7.Chapter7GameActivity;
import com.example.googlesdkversion3demo.chapter7.Chapter7GestureDetectorActivity;
import com.example.googlesdkversion3demo.chapter7.Chapter7GetPictureActivity;
import com.example.googlesdkversion3demo.chapter7.Chapter7GraphicsActivity;
import com.example.googlesdkversion3demo.chapter7.Chapter7MoveImageActivity;
import com.example.googlesdkversion3demo.chapter7.Chapter7MultiTouchActivity;
import com.example.googlesdkversion3demo.chapter7.Chapter7RecorderActivity;
import com.example.googlesdkversion3demo.chapter7.Chapter7SurfaceViewActivity;
import com.example.googlesdkversion3demo.chapter7.Chapter7TextToSpeechActivity;
import com.example.googlesdkversion3demo.chapter7.Chapter7VoiceRecogActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},0);
//        }
        Intent intent=new Intent(this, Chapter7VoiceRecogActivity.class);
        startActivity(intent);
    }
}
