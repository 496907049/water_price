package com.mic.adressselectorlib;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Author: Blincheng.
 * Date: 2017/5/9.
 * Description:
 */

public interface OnItemClickListener {
    /**
     * @param city 返回地址列表对应点击的对象
     * @param tabPosition 对应tab的位置
     * */
    void itemClick(AddressSelector addressSelector,CityInterface city,int position, int tabPosition) throws JSONException;

    /**
     * @param cityList 返回地址列表对应点击的集合
     * */
    void itemClick(AddressSelector addressSelector, ArrayList<City> cityList) throws JSONException;

    /**
     * @param cityList 返回地址列表对应点击的集合
     *     @param allSiteName   地址
     * */
    void itemClick(AddressSelector addressSelector, ArrayList<City> cityList,String allSiteName) throws JSONException;
}
