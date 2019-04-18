package com.ffapp.waterprice.jpush;


import android.content.Context;


import java.io.Serializable;

public class JpushMessageBean  implements Serializable {


	/**
	 * relateId : 38
	 * type : 1
	 */

	private String relateId;
	private int type;

	public String getRelateId() {
		return relateId;
	}

	public void setRelateId(String relateId) {
		this.relateId = relateId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void toDetail(Context context){
		switch (type){
			case 1:
//				MsgDetailActivity.toDetail(context,relateId);
				break;
		}
	}
}
