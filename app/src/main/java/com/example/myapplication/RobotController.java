package com.example.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class RobotController {
    private String BT_ADDRESS = "B8:27:EB:CE:E6:77";
    // private String BT_ADDRESS = "88:B1:11:87:2A:F7";
    private String ROBOT_UUID = "848d828b-c486-44df-83fa-5413b06146e0";
    public static char STOP_MOVING = 0;
    public static char MOVE_FORWARD = 1;
    public static char MOVE_BACKWARDS = 2;
    public static char MOVE_LEFT = 3;
    public static char MOVE_RIGHT = 4;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private byte[] buffer;

    public void finalize() {
        closeConnection();
    }

    public void establishConnection(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                device = bluetoothAdapter.getRemoteDevice(BT_ADDRESS);
                try {
                    UUID uuid = UUID.fromString(ROBOT_UUID);
                    socket = device.createRfcommSocketToServiceRecord(uuid);
                    socket.connect();
                    inputStream = socket.getInputStream();
                    outputStream = socket.getOutputStream();
                    buffer = new byte[1024];
                    handler.obtainMessage(0, "connected!!!").sendToTarget();
                    while (true) {
                        inputStream.read(buffer);
                    }
                } catch (IOException e) {
                    handler.obtainMessage(0, "sukablyat :(").sendToTarget();
                    // connection problems
                    Log.e(null, "connection problems", e);
                    closeConnection();
                }
            }
        }).start();
    }

    public void closeConnection() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {

            }
        }
    }

    public void sendCommand(final char command) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (outputStream == null) return;
                byte[] cmd = { (byte)command };
                try {
                    outputStream.write(cmd);
                } catch (IOException e) {
                    // closeConnection();
                }
            }
        }).start();
    }

}
