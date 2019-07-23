package com.mic.adressselectorlib;

import java.util.ArrayList;

/**
 * Author: Blincheng.
 * Date: 2017/5/9.
 * Description:
 */

public interface CityInterface {
    String getCityName();

    ArrayList<DeviceTreeChildListData> getCityChildren();

    String getAreaId();

    String getDeviceCode();


}
