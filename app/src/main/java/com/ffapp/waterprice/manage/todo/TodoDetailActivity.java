package com.ffapp.waterprice.manage.todo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.bean.BaseListData;
import com.ffapp.waterprice.bean.BaseListDataListBean;
import com.ffapp.waterprice.common.AdapterCommonDetail;

import butterknife.BindView;
import butterknife.OnClick;
import my.DialogUtils;
import my.MySharedPreferences;

/***
 * 运维管理-待办任务-详情
 * **/
public class TodoDetailActivity extends BasisActivity {


    BaseListData mListData;

    @BindView(R.id.recyclerview_detail)
    RecyclerView recyclerview_detail;

    boolean isExcute = false;
    boolean isPost = false;

    @Override
    public void initViews() {
        // TODO Auto-generated method stub
        super.initViews();
        setContentView(R.layout.manage_todo_detail_activity);
        setTitle("待办任务详情");
        setTitleLeftButton(null);
        recyclerview_detail.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.initData(savedInstanceState);

        mListData = (BaseListData) getIntent().getSerializableExtra("data");
        setViews();
//        getDetail();

        isExcute = new MySharedPreferences(mContext).getBoolean("excute",false);
        isPost = new MySharedPreferences(mContext).getBoolean("post",false);
        setBtnViews();
    }


    private void setViews() {
        if (mListData == null) return;
//        ((TextView)findViewById(R.id.edit_repair_postcontent)).setText(mListData.getCONTENT());
        BaseListDataListBean listBean = mListData.getListInfoTodo_Detail();
        AdapterCommonDetail adapterCommonDetail = new AdapterCommonDetail(mContext, listBean);
        recyclerview_detail.setAdapter(adapterCommonDetail);
    }

    void setBtnViews(){
        findViewById(R.id.btn_ok).setEnabled(!isExcute);
        findViewById(R.id.btn_post).setEnabled(!isPost);
    }

    @OnClick(R.id.btn_ok)
    void okClick(){
        if(isExcute)return;
        isExcute = true;
        new MySharedPreferences(mContext).putBoolean("excute",true);
        setBtnViews();
        DialogUtils.DialogOkMsg(mContext,"执行任务成功");
        setResult(Activity.RESULT_OK);
    }
    @OnClick(R.id.btn_post)
    void onPostClick(){
        if(!isExcute){
            DialogUtils.DialogOkMsg(mContext,"请先执行任务");
            return;
        }
        if(isPost)return;
        isPost = true;
        new MySharedPreferences(mContext).putBoolean("post",true);
        setBtnViews();
        DialogUtils.DialogOKmsgFinish(mContext,"提交任务成功");
        setResult(Activity.RESULT_OK);
    }
}
