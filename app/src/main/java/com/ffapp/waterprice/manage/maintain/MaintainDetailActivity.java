package com.ffapp.waterprice.manage.maintain;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.basis.GlideImageLoaderForimgpickup;
import com.ffapp.waterprice.bean.BaseListData;
import com.ffapp.waterprice.bean.BaseListDataListBean;
import com.ffapp.waterprice.bean.UploadImgData;
import com.ffapp.waterprice.common.AdapterCommonDetail;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.DialogUtils;
import my.LogUtil;
import my.ViewUtils;

/**
 * 维护管理-详情-提交
 */
public class MaintainDetailActivity extends BasisActivity {


    BaseListData mListData;


    @BindView(R.id.recyclerview_pic)
    RecyclerView recyclerview_pic;


    @BindView(R.id.recyclerview_detail)
    RecyclerView recyclerview_detail;

    boolean isNew = true;

    int MAX_SIZE = 6;
   MyAdapterList adapter_post;

   BaseListData mParamsPatrolResult;

    @Override
    public void initViews() {
        // TODO Auto-generated method stub
        super.initViews();
        setContentView(R.layout.manage_patrol_post_activity);
        setTitle("巡检管理");
        setTitleLeftButton(null);

        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3);
        recyclerview_pic.setLayoutManager(layoutManager);
        recyclerview_detail.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.initData(savedInstanceState);


        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoaderForimgpickup());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(MAX_SIZE);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

        mListData = (BaseListData) getIntent().getSerializableExtra("data");
        if (mListData == null) {
            mListData = new BaseListData();
            setViews();
        } else {
//            setTitle("");
            isNew = false;
            setViews();
        }


    }

    private void setViews() {
        if (mListData == null) return;
//        ((EditText) findViewById(R.id.text_project_name)).setText(mDetailBeean.getName());
//        ((TextView) findViewById(R.id.text_contact_person)).setText(mDetailBeean.getLinkman());

        adapter_post = new MyAdapterList(new ArrayList<UploadImgData>());
        recyclerview_pic.setAdapter(adapter_post);
        if(isNew){
        }


        recyclerview_detail.setAdapter(new AdapterCommonDetail(mContext,mListData.getListInfoMaintain_Detail()));
    }



    @OnClick({R.id.view_patrol_result})
    void patrolResultChoose() {

        final BaseListDataListBean listBean = new BaseListDataListBean();
        listBean.getList().add(new BaseListData("正常","正常"));
        listBean.getList().add(new BaseListData("故障","故障"));

        OptionsPickerView pickerView = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mParamsPatrolResult = listBean.getList().get(options1);
                ((TextView) findViewById(R.id.text_patrol_result_text)).setText(mParamsPatrolResult.getName());
            }
        })
                .build();
        pickerView.setPicker(listBean.getListString());
        pickerView.show();
        ViewUtils.hideInput(mContext);
//        hideInputZone();
    }



    @OnClick(R.id.btn_ok)
    void checkPost() {
//        if(mPrintRecordDeviceListData == null){
//            showToast("请选择报警主机");
//            return;
//        }

//        if(adapter_post.mListBean.size() == 0){
//            showToast(" 请上传图片");
//            return;
//        }
        DialogUtils.DialogOKmsgFinish(mContext,"提交成功");
//        postImgPost();
    }

    @OnClick(R.id.btn_save)
    void onSaveClick(){
        DialogUtils.DialogOkMsg(mContext,"保存成功");
    }

    @SuppressLint("StaticFieldLeak")
    private void postImgPost() {
        showProgress();
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... strings) {
                UploadImgData data;
                for (int i = 0, l = adapter_post.mListBean.size(); i < l; i++) {
                    data = adapter_post.mListBean.get(i);
                    if (data.isNeedpost()) {
//                        postImg(BUSY_TYPE,  adapter_post.mListBean.get(i));
                    }
                }
//                    }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                LogUtil.i("onPostExecute--->");
//                postReal();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }.execute("");

    }

//    private void postImg(String bizType, UploadImgData data) {
//        RequestParams params = new RequestParams();
//        params.add("bizType", bizType);
//        try {
//            params.put("uploadFile", new File(data.getPath()));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        HttpRestClient.postSync(Constants.URL_WORKORDER_UPLOAD_FILE, params, new MyHttpListener() {
//                    @Override
//                    public void onSuccess(int httpWhat, Object result) {
//                        ProjectFileData bean = (ProjectFileData) result;
//                        data.setPath(bean.getFileUrl());
//                        mListData.getLIST_PIC_FILES().add(bean);
//                    }
//
//                    @Override
//                    public void onFinish(int httpWhat) {
//                    }
//                },
//                0, ProjectFileData.class);
//
//    }



    private final static int REQUEST_PIC = 12;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PIC) {
//            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
//            for (int i = 0, l = selectList.size(); i < l; i++) {
//                adapter_post.mListBean.add(new UploadImgData("", selectList.get(i).getPath()));
//            }
//            adapter_post.notifyDataSetChanged();

                        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                if (data != null) {
                    ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    for (int i = 0, l = images.size(); i < l; i++) {
                        adapter_post.mListBean.add(new UploadImgData("", images.get(i).path));
                    }
                    adapter_post.notifyDataSetChanged();
                }
                return;
            }
        }
    }

    public class MyAdapterList extends RecyclerView.Adapter<MyAdapterList.ViewHolder> {

        public ArrayList<UploadImgData> mListBean = new ArrayList<>();

        public MyAdapterList(ArrayList<UploadImgData> list) {
            mListBean = list;
        }

        //创建新View，被LayoutManager所调用
        @Override
        public MyAdapterList.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.setting_feedback_img_grid_item, viewGroup, false);
            return new MyAdapterList.ViewHolder(view);
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(MyAdapterList.ViewHolder viewHolder, int position) {
            if (mListBean.size() < MAX_SIZE && position == mListBean.size()) {
//                Glide.with(mContext).load(R.drawable.img_add).into(viewHolder.img_icon);
                viewHolder.img_icon.setImageResource(R.drawable.img_add);
                viewHolder.img_delete.setVisibility(View.INVISIBLE);
            } else {
                UploadImgData data = mListBean.get(position);
                viewHolder.list_item.setTag(data);
                Glide.with(mContext).load(data.getPath()).placeholder(R.drawable.base_img_default_square).into(viewHolder.img_icon);
                viewHolder.img_delete.setVisibility(View.VISIBLE);
                viewHolder.img_delete.setTag(position);
//            viewHolder.list_item.setBackgroundResource(R.drawable.item_shadow_bluelight_selector);
            }

            viewHolder.img_icon.setTag(R.id.img_icon, position);
        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            if (mListBean.size() >= MAX_SIZE)
                return MAX_SIZE;
            return mListBean.size() + 1;
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.list_item)
            public View list_item;
            @BindView(R.id.img_icon)
            public ImageView img_icon;
            @BindView(R.id.img_delete)
            public ImageView img_delete;


            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }

            @OnClick(R.id.img_icon)
            public void onItemClick(View v) {
                int position = (int) v.getTag(R.id.img_icon);
                if (position == mListBean.size()) {
                    ImagePicker.getInstance().setSelectLimit(MAX_SIZE - mListBean.size());
                    Intent intent = new Intent(mContext, ImageGridActivity.class);
                    startActivityForResult(intent, 1);
//                    PictureSelector.create(mContext).openGallery(PictureMimeType.ofImage()).maxSelectNum(MAX_SIZE).isCamera(true)
////                            .compress(true).compressSavePath(Constants.DIR_TEMP).minimumCompressSize(100)// 小于100kb的图片不压缩
//                            .forResult(REQUEST_PIC);

                }
            }

            @OnClick(R.id.img_delete)
            public void onItemDelete(View v) {
//                    AppListData data = (AppListData) v.getTag();
//                    data.toDetail(mContext);
                int position = (int) v.getTag();
                UploadImgData data = mListBean.get(position);
//                mListData.deleteImgPost(data);
                mListBean.remove(position);
                notifyDataSetChanged();
            }
        }
    }
}
