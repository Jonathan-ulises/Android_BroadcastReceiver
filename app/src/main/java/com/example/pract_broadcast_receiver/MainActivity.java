package com.example.pract_broadcast_receiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;

import com.example.pract_broadcast_receiver.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onPause() {
        super.onPause();
//        unregisterReceiver(getBatteryLevel);
//        unregisterReceiver(getAirplaneMode);
//        unregisterReceiver(getWifiState);
        unregisterRecibers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(getBatteryLevel, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        registerReceiver(getAirplaneMode, new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED));
        registerReceiver(getWifiState, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
    }

    private BroadcastReceiver getBatteryLevel = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int batteryLebel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

            binding.txtBatteryLevel.setText("Porcentaje de Bateria: " + batteryLebel);
        }
    };

    private BroadcastReceiver getAirplaneMode = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean airplanemode = intent.getBooleanExtra("state", false);

            if (airplanemode) {
                binding.imageAirplane.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_airplanemode_active));
            } else {
                binding.imageAirplane.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_airplanemode_inactive));
            }
        }
    };

    private BroadcastReceiver getWifiState = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);

            switch (wifiState) {
                case WifiManager.WIFI_STATE_ENABLED:
                    binding.txtWifiStatus.setText("WIFI: Activado");
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    binding.txtWifiStatus.setText("WIFI: Desactivado");
                    break;
                case WifiManager.WIFI_STATE_UNKNOWN:
                    binding.txtWifiStatus.setText("WIFI: Error");
                    break;
            }
        }
    };

    private void unregisterRecibers() {
        unregisterReceiver(getBatteryLevel);
        unregisterReceiver(getAirplaneMode);
        unregisterReceiver(getWifiState);
    }
}