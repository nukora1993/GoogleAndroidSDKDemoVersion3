package com.example.googlesdkversion3demo.chapter5;

import androidx.appcompat.app.AppCompatActivity;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.googlesdkversion3demo.R;

import java.util.ArrayList;
import java.util.List;

public class Chapter5WifiConnectActivity extends AppCompatActivity {
    private Button button;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayListTask;
    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter5_wifi_connect);

        button=findViewById(R.id.button);
        listView=findViewById(R.id.list_view);

        wifiManager=(WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        //本意时扫描并连接wifi，但是实测发现得到的时历史wifi
        wifiManager.startScan();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayListTask=new ArrayList<String>();
                List<WifiConfiguration> wifiConf=wifiManager.getConfiguredNetworks();
                if(wifiConf.size()>0){
                    if(!wifiManager.isWifiEnabled()){
                        if(wifiManager.getWifiState()!=WifiManager.WIFI_STATE_ENABLING){
                            wifiManager.setWifiEnabled(true);
                        }
                    }

                    int intNetworkId=wifiConf.get(0).networkId;
                    //连接wifi
                    wifiManager.enableNetwork(intNetworkId,true);
                    int i=1;
                    for(WifiConfiguration amTask:wifiConf){
                        arrayListTask.add(""+(i++)+":"+amTask.SSID+"(ID="+amTask.networkId+")");
                    }

                    arrayAdapter=new ArrayAdapter<String>(Chapter5WifiConnectActivity.this,R.layout.simple_list_item_1_small,arrayListTask);
                    listView.setAdapter(arrayAdapter);
                }
            }
        });
    }
}
