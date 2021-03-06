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
import com.mic.adressselectorlib.AddressSelector;
import com.mic.adressselectorlib.City;
import com.mic.adressselectorlib.CityInterface;
import com.mic.adressselectorlib.DeviceTreeChildListData;
import com.mic.adressselectorlib.OnItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import my.http.MyHttpListener;
import my.http.OkGoClient;

public class WaterActivity extends BasisActivity {

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

        showProgress();
        OkGoClient.get(mContext, Constants.URL_GET_WATER_TREE, new MyHttpListener() {
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
        addressSelector.setTopImg(com.mic.adressselectorlib.R.mipmap.tab_icon_area);
        addressSelector.setCities(cities1);
        addressSelector.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClick(AddressSelector addressSelector, CityInterface city,int position, int tabPosition) throws JSONException {
                switch (tabPosition) {
                    case 0:
                        addressSelector.setCities(getCityList(cities2,city.getCityChildren()));
                        break;
                    case 1:
                        String level = "child";
                        for (DeviceTreeChildListData data : city.getCityChildren()){
                            if(data.getChildren() != null){
                                level = "parent";
                                break;
                            }
                        }
                        switch (level){
                            case "child":
                                addressSelector.setCitiesThree(getCityList(cities3,city.getCityChildren()));
                                break;
                            case "parent":
                                addressSelector.setCities(getCityList(cities3,city.getCityChildren()));
                                break;
                        }
                        break;
                    case 2:
                        addressSelector.setCitiesThree(getCityList(cities4,city.getCityChildren()));
                        break;
                }
            }

            @Override
            public void itemClick(AddressSelector addressSelector, ArrayList<City> cityList) throws org.json.JSONException {

            }

            @Override
            public void itemClick(AddressSelector addressSelector, ArrayList<City> cityList, String allSiteName) throws org.json.JSONException {

                if (cityList.size()>5){
                    showToast("最多选5个");
                    return;
                }

                ArrayList<String> deviceCodeList = new ArrayList<>();
                for (City city : cityList){
                    deviceCodeList.add(city.getId());
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
}
