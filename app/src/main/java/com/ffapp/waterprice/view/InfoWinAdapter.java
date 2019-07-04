package com.ffapp.waterprice.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.ffapp.waterprice.R;


/**
 * Created by Teprinciple on 2016/8/23.
 * 地图上自定义的infowindow的适配器
 */
public class InfoWinAdapter implements AMap.InfoWindowAdapter, View.OnClickListener {

    private Context mContext;
    private LatLng latLng;
    private String agentName;
    private String snippet;

    TextView tvInfoWinTitle;
    TextView tvField;
    TextView tvContent;
    TextView tvContent3;
    TextView tvContent2;
    Button btnChoose;

    public InfoWinAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public View getInfoWindow(Marker marker) {
        initData(marker);
        View view = initView();
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private void initData(Marker marker) {
//        soilMoistureListData = (SoilMoistureListData) marker.getObject();
        latLng = marker.getPosition();
        snippet = marker.getSnippet();
        agentName = marker.getTitle();
    }

    @NonNull
    private View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_infowindow, null);
        tvInfoWinTitle = (TextView) view.findViewById(R.id.tv_info_win_title);
        tvField = (TextView) view.findViewById(R.id.tv_field);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        tvContent2 = (TextView) view.findViewById(R.id.tv_content2);
        tvContent3 = (TextView) view.findViewById(R.id.tv_content3);
        btnChoose = (Button) view.findViewById(R.id.btn_choose);
        btnChoose.setOnClickListener(this);

        tvInfoWinTitle.setText(agentName);
//        tvContent3.setText(snippet);
//        tvContent.setText(soilMoistureListData.getNodeId());
//        tvContent2.setText(soilMoistureListData.getGkNumber());
//        tvContent3.setText(soilMoistureListData.getPosition());
        return view;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_choose:  //点击选择该站点
//                SoilListActivity.newInstant(mContext,new SoilStationListData(soilMoistureListData.getGkNumber(),soilMoistureListData.getNodeId(),soilMoistureListData.getSoilName()));
//                SoilMoistureMapActivity activity = (SoilMoistureMapActivity) mContext;
//                Intent intent = new Intent();
//                intent.putExtra("data", new SoilStationListData(soilMoistureListData.getGkNumber(),soilMoistureListData.getNodeId(),soilMoistureListData.getSoilName()));
//                activity.setResult(SoilListActivity.CALLBACK,intent);
//                activity.finish();
                break;
        }
    }


}
