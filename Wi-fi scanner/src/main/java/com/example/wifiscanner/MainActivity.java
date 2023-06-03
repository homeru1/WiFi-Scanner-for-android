package com.example.wifiscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.List;
import android.net.wifi.ScanResult;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    ListView ListOfNames;
    public String [] NetworkName;
    public String [] Sec;
    public String [] Lvl;
    public String [] Bssid;
    public String [] Freq;
    public String [] TimeStamp;
    public WifiManager wifiManager;
    public List<ScanResult> wifiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListOfNames = findViewById(R.id.ListOfNames);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.ACCESS_WIFI_STATE},
                    10);
        }

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.CHANGE_WIFI_STATE},
                    10);
        }

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION},
                    10);
        }

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.ACCESS_COARSE_LOCATION},
                    10);
        }
        Scan_WiFi();
        Button Scan_button = findViewById(R.id.scan);
        Scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    Scan_WiFi();
                }
                catch (Exception e){
                    Snackbar.make(view, "Error: " + e.toString() +
                                    ".\nTry to restart the application :)", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, this.NetworkName);
        ListOfNames.setAdapter(adapter);
        ListOfNames.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("VIEW",NetworkName[position]);
                CustomDialogFragment dialog = CustomDialogFragment.newInstance(
                        " "  + NetworkName[position] +
                        "\n" + Bssid[position] +
                        "\n" + Sec[position] +
                        "\n" + Lvl[position] +
                        "\n" + Freq[position] +
                        "\n" + TimeStamp[position]);
                dialog.show(getSupportFragmentManager(), "CustomDialogFragment");
            }
        });
    }


    private void Scan_WiFi(){
        this.wifiManager = (WifiManager)getApplicationContext().getSystemService(
                Context.WIFI_SERVICE);
        this.wifiManager.startScan();
        this.wifiList = this.wifiManager.getScanResults();
        if(this.wifiManager.getScanResults().isEmpty()){
            return;
        }
        this.NetworkName = new String[wifiList.size()];
        this.Sec = new String[wifiList.size()];
        this.Lvl = new String[wifiList.size()];
        this.Freq = new String[wifiList.size()];
        this.TimeStamp = new String[wifiList.size()];
        this.Bssid = new String[wifiList.size()];
        Log.d("RESULT", wifiList.toString());

        for (int i = 0; i< wifiList.size(); i++){
            String entry = wifiList.get(i).toString();
            String[] vector_item = entry.split(",");
            NetworkName[i] = vector_item[0];
            Bssid[i] = vector_item[1];
            Sec[i] = vector_item[2];
            Lvl[i] = vector_item[3];
            Freq[i] = vector_item[4];
            TimeStamp[i] = vector_item[5];
        }
        Log.d("RESULT", "End scanning");
    }
}