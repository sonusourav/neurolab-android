package io.neurolab.main;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;

import com.felhr.usbserial.UsbSerialDevice;
import java.util.HashMap;
import java.util.Map;

public class DeviceConnector {

    Context context;
    private UsbManager usbManager;
    private UsbDevice device;
    private UsbSerialDevice serialPort;
    private UsbDeviceConnection connection;
    private int baudRate = 9600;
    private int arduinoVid = 0x2341;

    private final String ACTION_USB_PERMISSION = "io.neurolab.USB_PERMISSION";

    public DeviceConnector(UsbManager usbManager) {
        this.usbManager = usbManager;
    }

    public void checkConnection(Context context) {
        this.context = context;
        HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();
        if (!usbDevices.isEmpty()) {
            boolean keep = true;
            for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                device = entry.getValue();
                int deviceVID = device.getVendorId();
                if (deviceVID == arduinoVid) { //Arduino Vendor ID
                    PendingIntent pi = PendingIntent.getBroadcast(context, 0,
                            new Intent(ACTION_USB_PERMISSION), 0);
                    usbManager.requestPermission(device, pi);
                    keep = false;
                } else {
                    connection = null;
                    device = null;
                }
                if (!keep)
                    break;
            }
        }
    }

    public UsbDevice getDevice() {
        return device;
    }

    public void setDevice(UsbDevice device) {
        this.device = device;
    }

    public UsbSerialDevice getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(UsbSerialDevice serialPort) {
        this.serialPort = serialPort;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    public int getArduinoVid() {
        return arduinoVid;
    }
}
