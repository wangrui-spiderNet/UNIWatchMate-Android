package com.base.sdk.entity;

public class ScanDeviceInfo extends DeviceInfo {
    private String manufactureUrl;

    public ScanDeviceInfo(DeviceMode mode, String address) {
        super(mode, address);
    }

    public String getManufactureUrl() {
        return manufactureUrl;
    }

    public void setManufactureUrl(String manufactureUrl) {
        this.manufactureUrl = manufactureUrl;
    }

}
