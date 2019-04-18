package my;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.ffapp.waterprice.R;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.NormalListDialog;


/**
 * Created by zhengyy on 2017/7/27.
 */

public class DialogUtils {

    public static NormalDialog DialogTwo(Context context, String title, String msg,
                                           String positiveButtonText, String negativeButtonText,
                                           final OnBtnClickL listenerOk, final OnBtnClickL listenerCancel) {

        final NormalDialog dialog = new NormalDialog(context);

        if (!TextUtils.isEmpty(title)) {
            dialog.isTitleShow(true);
            dialog.title(title);
        } else {
            dialog.isTitleShow(false);
        }
        dialog.content(msg);
        dialog.btnNum(2).btnText(positiveButtonText, negativeButtonText);
        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {

                if (listenerOk != null) {
                    listenerOk.onBtnClick();
                }
                dialog.dismiss();
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                if (listenerCancel != null) {
                    listenerCancel.onBtnClick();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }

    public static NormalDialog DialogOne(Context context, String title, String msg, String btntext, final OnBtnClickL listener) {
        final NormalDialog dialog = new NormalDialog(context);
        if (!TextUtils.isEmpty(title)) {
            dialog.isTitleShow(true);
            dialog.title(title);
        } else {
            dialog.isTitleShow(false);
        }
        dialog.content(msg);
        dialog.btnNum(1).btnText(btntext);
        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                if (listener != null) {
                    listener.onBtnClick();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }


    public static NormalDialog DialogOkMsg(Context context, String msg, final OnBtnClickL listener) {
        final NormalDialog dialog = new NormalDialog(context);
        dialog.isTitleShow(false);
        dialog.content(msg);
        dialog.btnNum(1).btnText(context.getString(R.string.app_ok));
        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                if (listener != null) {
                    listener.onBtnClick();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }
    public static NormalDialog DialogOkMsg(Context context, String msg) {
        final NormalDialog dialog = new NormalDialog(context);
        dialog.isTitleShow(false);
        dialog.content(msg);
        dialog.btnNum(1).btnText(context.getString(R.string.app_ok));
        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }

    public static NormalDialog DialogOKmsgFinish(final Activity context,
                                                   String msg) {

        final NormalDialog dialog = new NormalDialog(context);
        dialog.isTitleShow(false);
        dialog.content(msg);
        dialog.btnNum(1).btnText(context.getString(R.string.app_ok));
        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog.dismiss();
                context.finish();
            }
        });
        dialog.show();
        return dialog;
    }

    public static NormalListDialog DialogStringList(final Activity mContext, String[] items, final OnOperItemClickL onOperItemClickL) {
        final NormalListDialog dialog = new NormalListDialog(mContext, items);
        dialog.title("请选择")//
                .layoutAnimation(null)
                .setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        onOperItemClickL.onOperItemClick(parent, view, position, id);
                        dialog.dismiss();
                    }
                });
        dialog.show(); 
        return dialog;
    }
}
