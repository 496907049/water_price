package com.ffapp.waterprice.data.area;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSONException;
import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.DeviceTreeListBean;
import com.ffapp.waterprice.bean.DeviceTreeListData;
import com.ffapp.waterprice.home.site.SiteSearchActivity;
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

public class AreaActivity extends BasisActivity {

    String title;

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
        setTitle("区域信息");
        setTitleLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        title = getIntent().getStringExtra("title");
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
                    city.setCode(children.getCode());
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
        addressSelector.setTopImg(com.mic.adressselectorlib.R.mipmap.tab_icon_area);
        addressSelector.setCities(cities1);
        addressSelector.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClick(AddressSelector addressSelector, CityInterface city,int position, int tabPosition) throws JSONException {
                switch (tabPosition) {
                    case 0:
                        goToChildren(city,cities2);
                        break;
                    case 1:
                        goToChildren(city,cities3);
                        break;
                    case 2:
                        goToChildren(city,cities4);
                        break;
                    case 3:

                        break;
                }
            }

            @Override
            public void itemClick(AddressSelector addressSelector, ArrayList<City> cityList) throws org.json.JSONException {

            }

            @Override
            public void itemClick(AddressSelector addressSelector, ArrayList<City> cityList, String allSiteName) throws org.json.JSONException {

                if(title.equals("土壤墒情分析")){
                    if (cityList.size()>1){
                        showToast("最多选1个");
                        return;
                    }
                }

                if (cityList.size()>5){
                    showToast("最多选5个");
                    return;
                }

                ArrayList<String> deviceCodeList = new ArrayList<>();
                for (City city : cityList){
                    deviceCodeList.add(city.getDeviceCode());
                }
                Intent data = new Intent();
                data.putStringArrayListExtra("deviceCodeList",deviceCodeList);
                data.putExtra("allSiteName",allSiteName);
                setResult(Constants.AREA_CALLBACK,data);
                finish();
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
            children.setCode(currentTreeList.get(i).getCode());

            cityList.add(children);
        }
        return cityList;
    }


    void goToChildren(CityInterface city,ArrayList<City> nextCityList){
        if(city.getCityChildren() == null){
            ArrayList<String> deviceCodeList = new ArrayList<>();
            deviceCodeList.add(city.getDeviceCode());
            Intent data = new Intent();
            data.putStringArrayListExtra("deviceCodeList",deviceCodeList);
            data.putExtra("allSiteName",city.getCityName());
            setResult(Constants.AREA_CALLBACK,data);
            finish();
        }else {
            addressSelector.setCities(getCityList(nextCityList,city.getCityChildren()));
        }
    }
}
