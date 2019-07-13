package com.ffapp.waterprice.home.site;

import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.BaseListBean;
import com.ffapp.waterprice.bean.BasisBean;
import com.ffapp.waterprice.bean.DeviceTreeListBean;
import com.ffapp.waterprice.bean.DeviceTreeListData;
import com.mic.adressselectorlib.City;
import com.mic.adressselectorlib.AddressSelector;
import com.mic.adressselectorlib.CityInterface;
import com.mic.adressselectorlib.OnItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import my.http.MyHttpListener;
import my.http.OkGoClient;

public class SiteActivity extends BasisActivity {

    ArrayList<DeviceTreeListData> treeList = new ArrayList<>();
    ArrayList<DeviceTreeListData> selectTreeList = new ArrayList<>();

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
                    for (int i = 0; i < treeList.size(); i++) {
                        city = new City();
                        city.setName(treeList.get(i).getName());
                        cities1.add(city);
                    }
            }

            @Override
            public void onFinish(int httpWhat) {
                dismissProgress();
            }
        }, 0, BaseListBean.class);


        AddressSelector addressSelector = (AddressSelector) findViewById(R.id.address);
        addressSelector.setTabAmount(3);
        addressSelector.setTopImg(com.mic.adressselectorlib.R.mipmap.tab_icon_station);
        addressSelector.setCities(cities1);
        addressSelector.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClick(AddressSelector addressSelector, CityInterface city, int tabPosition) throws JSONException {
                switch (tabPosition) {
                    case 0:
                        try {
                            JSONArray jsonArray2 = JSON.parseArray(getString(R.string.cities2));
                            for (int i = 0; i < jsonArray2.size(); i++) {
//                                cities2.add(new Gson().fromJson(jsonArray2.get(i).toString(), City.class));
                                cities2.add(JSON.parseObject(jsonArray2.get(i).toString(), new TypeReference<City>() {
                                }));
                            }
                            addressSelector.setCities(cities2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                    case 1:

                        try {
                            JSONArray jsonArray3 = JSON.parseArray(getString(R.string.cities3));
                            for (int i = 0; i < jsonArray3.size(); i++) {
//                                cities3.add(new Gson().fromJson(jsonArray3.get(i).toString(), City.class));
                                cities3.add(JSON.parseObject(jsonArray3.get(i).toString(), new TypeReference<City>() {
                                }));
                            }
                            addressSelector.setCities(cities3);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                    case 2:
                        try {
                            JSONArray jsonArray4 = JSON.parseArray(getString(R.string.cities4));
                            for (int i = 0; i < jsonArray4.size(); i++) {
//                                cities4.add(new Gson().fromJson(jsonArray4.get(i).toString(), City.class));
                                cities4.add(JSON.parseObject(jsonArray4.get(i).toString(), new TypeReference<City>() {
                                }));
                            }
                            addressSelector.setCitiesTwo(cities4);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(MainActivity.this, "tabPosition ：" + tabPosition + " " + city.getCityName(), Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        showToast("tabPosition ：" + tabPosition + " " + city.getCityName());
                        break;
                }
            }

            @Override
            public void itemClick(AddressSelector addressSelector, ArrayList<City> cityList) throws org.json.JSONException {

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
}
