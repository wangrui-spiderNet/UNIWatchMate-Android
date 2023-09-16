package com.base.sdk.entity;

public class DeviceInfo {
    private String address;
    private String name;
    private DeviceMode mode;
    private boolean isRecognized;

    public DeviceInfo(DeviceMode mode,String address) {
        this.address = address;
        this.mode = mode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeviceMode getMode() {
        return mode;
    }

    public void setMode(DeviceMode mode) {
        this.mode = mode;
    }

    public boolean isRecognized() {
        return isRecognized;
    }

    public void setRecognized(boolean recognized) {
        isRecognized = recognized;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", mode=" + mode +
                '}';
    }
}
