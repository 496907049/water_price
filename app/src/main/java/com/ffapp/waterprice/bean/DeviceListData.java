package com.ffapp.waterprice.bean;

import android.text.TextUtils;

import com.amap.api.maps2d.model.LatLng;
public class DeviceListData extends BasisBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String lgtd;
	private String lttd;
	private String stlc;
	private String stnm;

	private boolean isCheck;
	private int dsfl;



	public String getLgtd() {
		return lgtd;
	}

	public void setLgtd(String lgtd) {
		this.lgtd = lgtd;
	}

	public String getLttd() {
		return lttd;
	}

	public void setLttd(String lttd) {
		this.lttd = lttd;
	}

	public String getStlc() {
		if(TextUtils.isEmpty(stlc))return "-";
		return stlc;
	}

	public void setStlc(String stlc) {
		this.stlc = stlc;
	}

	public String getStnm() {
		if(TextUtils.isEmpty(stnm))return "-";
		return stnm;
	}

	public void setStnm(String stnm) {
		this.stnm = stnm;
	}


	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean check) {
		isCheck = check;
	}

	public int getMapMarkerResid(){
		switch (dsfl){
//			case 0:return R.drawable.map_icon_location_grey;
//			case 1:return R.drawable.map_icon_location_blue;
//			case 2:return R.drawable.map_icon_location_red;
//			case 3:return R.drawable.map_icon_location_blue;
//			default:return R.drawable.map_icon_location_grey;
		}
		return 0;
	}

	public LatLng getLatlng() {
		if(TextUtils.isEmpty(lttd)){
			return  new LatLng(0,0);
		}
		LatLng latLng = new LatLng(Double.valueOf(lttd),Double.valueOf(lgtd));
		return latLng;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
