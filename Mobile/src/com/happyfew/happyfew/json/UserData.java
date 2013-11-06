package com.happyfew.happyfew.json;

import android.os.Environment;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by alexandre on 19/10/13.
 */
public class UserData {
    public String id="";
    public String imei="";
    public String serialNumber="";
    public String simSerial="";

    public boolean mode=false;
    public boolean garden=false;
    public boolean tech=false;
    public boolean travel=false;
    public boolean show=false;
}/*
@JsonProperty("androidId") String androidId,
@JsonProperty("imei") String imei,
@JsonProperty("serialNumber") String serialNumber,
@JsonProperty("simSerial") String simSerial,
@JsonProperty("mode") boolean mode,
@JsonProperty("garden") boolean garden,
@JsonProperty("tech") boolean tech,
@JsonProperty("travel") boolean travel,
@JsonProperty("show") boolean show*/