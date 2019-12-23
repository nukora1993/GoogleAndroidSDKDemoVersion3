package com.example.googlesdkversion3demo.chapter5;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyDeviceAdminReceiver extends DeviceAdminReceiver {
    void showToast(Context context,CharSequence msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(@NonNull Context context, @NonNull Intent intent) {
//        super.onEnabled(context, intent);
        showToast(context,"receiver启动");
    }

    @Nullable
    @Override
    public CharSequence onDisableRequested(@NonNull Context context, @NonNull Intent intent) {
//        return super.onDisableRequested(context, intent);
        return "是否确定停用";
    }

    @Override
    public void onDisabled(@NonNull Context context, @NonNull Intent intent) {
//        super.onDisabled(context, intent);
        showToast(context,"receiver停用");
    }

    @Override
    public void onPasswordChanged(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle user) {
//        super.onPasswordChanged(context, intent, user);
        showToast(context,"receiver修改密码");
    }

    @Override
    public void onPasswordFailed(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle user) {
//        super.onPasswordFailed(context, intent, user);
        showToast(context,"receiver密码错误");
    }

    @Override
    public void onPasswordSucceeded(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle user) {
//        super.onPasswordSucceeded(context, intent, user);
        showToast(context,"receiver密码正确");
    }
}
