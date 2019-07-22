package com.ffapp.waterprice.home.site;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSONException;
import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.DeviceTreeListBean;
import com.ffapp.waterprice.bean.DeviceTreeListData;
import com.mic.adressselectorlib.City;
import com.mic.adressselectorlib.AddressSelector;
import com.mic.adressselectorlib.CityInterface;
import com.mic.adressselectorlib.DeviceTreeChildListData;
import com.mic.adressselectorlib.OnItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import my.ActivityTool;
import my.http.MyHttpListener;
import my.http.OkGoClient;

public class SiteActivity extends BasisActivity {

    ArrayList<DeviceTreeListData> treeList = new ArrayList<>();


    private ArrayList<City> cities1 = new ArrayList<>();
    private ArrayList<City> cities2 = new ArrayList<>();
    private ArrayList<City> cities3 = new ArrayList<>();
    private ArrayList<City> cities4 = new ArrayList<>();

    @BindView(R.id.address)
    AddressSelector addressSelector;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_site);
        setTitle("站点信息");
        setTitleLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTitleRightButton(R.mipmap.top_icon_search, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityTool.skipActivityForResult(mContext,SiteSearchActivity.class,Constants.SITE_CALLBACK);
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        showProgress();
        OkGoClient.get(mContext, Constants.URL_GET_TREE, new MyHttpListener() {
            @Override
            public void onSuccess(int httpWhat, Object result) {
                DeviceTreeListBean listBean = (DeviceTreeListBean) result;
                treeList = listBean.getList();
                 City city;
                 for (DeviceTreeChildListData children : treeList.get(0).getChildren()){
                     city = new City();
                     city.setName(children.getName());
                     city.setId(children.getValue());
                     city.setChildren(children.getChildren());
                     cities1.add(city);
                 }
                initAddressSelector();
            }

            @Override
            public void onFinish(int httpWhat) {
                dismissProgress();
            }
        }, 0, DeviceTreeListBean.class);
    }

    
    void initAddressSelector(){
        addressSelector.setTabAmount(3);
        addressSelector.setTopImg(com.mic.adressselectorlib.R.mipmap.tab_icon_station);
        addressSelector.setCities(cities1);
        addressSelector.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClick(AddressSelector addressSelector, CityInterface city,int position, int tabPosition) throws JSONException {
                switch (tabPosition) {
                    case 0:
                        addressSelector.setCities(getCityList(cities2,city.getCityChildren()));
                        break;
                    case 1:
                        addressSelector.setCities(getCityList(cities3,city.getCityChildren()));
                        break;
                    case 2:
                        addressSelector.setCitiesTwo(getCityList(cities4,city.getCityChildren()));
                        break;
                    case 3:
                        Intent data = new Intent();
                        data.putExtra("areaId",city.getAreaId());
                        setResult(Constants.SITE_CALLBACK,data);
                        finish();
                        break;
                }
            }

            @Override
            public void itemClick(AddressSelector addressSelector, ArrayList<City> cityList) throws org.json.JSONException {

            }

            @Override
            public void itemClick(AddressSelector addressSelector, ArrayList<City> cityList, String allSiteName) throws org.json.JSONException {

            }
        });
        addressSelector.setOnTabSelectedListener(new AddressSelector.OnTabSelectedListener() {
            @Override
            public void onTabSelected(AddressSelector addressSelector, AddressSelector.Tab tab) {
                switch (tab.getIndex()) {
                    case 0:
                        addressSelector.setCities(cities1);
                        break;
                    case 1:
                        addressSelector.setCities(cities2);
                        break;
                    case 2:
                        addressSelector.setCities(cities3);
                        break;
                }
            }

            @Override
            public void onTabReselected(AddressSelector addressSelector, AddressSelector.Tab tab) {

            }
        });
    }

    private ArrayList<City> getCityList(ArrayList<City> cityList,ArrayList<DeviceTreeChildListData> currentTreeList){
        cityList.clear();
        if(currentTreeList == null){
            return cityList;
        }

        City children;
        for (int i = 0; i < currentTreeList.size(); i++) {
            children = new City();
            children.setName(currentTreeList.get(i).getName());
            children.setId(currentTreeList.get(i).getValue());
            children.setChildren(currentTreeList.get(i).getChildren());
            cityList.add(children);
        }
        return cityList;
    }
}
