package com.ffapp.waterprice.data.area;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.mic.adressselectorlib.City;
import com.mic.adressselectorlib.AddressSelector;
import com.mic.adressselectorlib.CityInterface;
import com.mic.adressselectorlib.OnItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;

public class AreaActivity extends BasisActivity {

    private ArrayList<City> cities1 = new ArrayList<>();
    private ArrayList<City> cities2 = new ArrayList<>();
    private ArrayList<City> cities3 = new ArrayList<>();
    private ArrayList<City> cities4 = new ArrayList<>();

    @BindView(R.id.address)
    AddressSelector addressSelector;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_area);
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

      //  拿到本地JSON 并转成String
        try {
          JSONArray jsonArray = JSON.parseArray(getString(R.string.cities1));
            for (int i = 0; i < jsonArray.size(); i++) {
//                cities1.add(new Gson().fromJson(jsonArray.get(i).toString(), City.class));
                City ac = JSON.parseObject(jsonArray.get(i).toString(), new TypeReference<City>() {});
                cities1.add( JSON.parseObject(jsonArray.get(i).toString(), new TypeReference<City>() {}));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                                cities2.add( JSON.parseObject(jsonArray2.get(i).toString(), new TypeReference<City>() {}));
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
                                cities3.add( JSON.parseObject(jsonArray3.get(i).toString(), new TypeReference<City>() {}));
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
                                cities4.add( JSON.parseObject(jsonArray4.get(i).toString(), new TypeReference<City>() {}));
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
                Toast.makeText(mContext, "aaaaa=== "+cityList.toString() , Toast.LENGTH_SHORT).show();
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
