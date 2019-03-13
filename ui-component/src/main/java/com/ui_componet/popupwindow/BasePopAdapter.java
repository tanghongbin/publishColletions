package com.ui_componet.popupwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ui_componet.R;

import java.util.List;

/**
 * Created by tanghongbin on 2017/3/11.
 */

public class BasePopAdapter extends BaseAdapter {
    List mList;
    Context mContext;

    public BasePopAdapter(Context mContext, List mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    PopupBuilder popupBuilder;


    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.popup_item, null);
        }
        TextView view = (TextView) convertView.findViewById(R.id.text);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        RelativeLayout popup_item_rl = (RelativeLayout) convertView.findViewById(R.id.popup_item_rl);
        BasePopupObj obj = (BasePopupObj) mList.get(position);
        if (obj != null) {
            view.setText(obj.getPopTitle());
        }
        setPopupStyle(popup_item_rl, view, image, popupBuilder, obj.isCheceked());
        return convertView;
    }

    private void setPopupStyle(RelativeLayout popup_item_rl, TextView view, ImageView image, PopupBuilder popupBuilder, boolean checked) {
        if (popupBuilder != null) {
            if (popupBuilder.getTextSize() != 0) {
                view.setTextSize(popupBuilder.getTextSize());
            }
            if (popupBuilder.getTextColor() != 0) {
                view.setTextColor(popupBuilder.getTextColor());
            }
            if (popupBuilder.getCheckTextColor() != 0 && checked) {
                view.setTextColor(popupBuilder.getCheckTextColor());
            }

            if (checked) {
                Drawable checkedBackgroundDrawable = null;
                if (popupBuilder.getCheckBackgroundDrawable() != null) {
                    checkedBackgroundDrawable = popupBuilder.getCheckBackgroundDrawable();
                } else {
                    if (popupBuilder.getBackgroundDrawable() != null) {
                        checkedBackgroundDrawable = popupBuilder.getBackgroundDrawable();
                    }
                }
                popup_item_rl.setBackgroundDrawable(checkedBackgroundDrawable == null ?
                new ColorDrawable(Color.WHITE):checkedBackgroundDrawable);
            } else {
                Drawable backgroundDrawable = null;
                if (popupBuilder.getBackgroundDrawable() != null) {
                    backgroundDrawable = popupBuilder.getBackgroundDrawable();
                }
                popup_item_rl.setBackgroundDrawable(backgroundDrawable == null ?
                        new ColorDrawable(Color.WHITE):backgroundDrawable);
            }
            if (checked) {
                if (popupBuilder.getCheckDrawable() != 0) {
                    Drawable drawable = mContext.getResources().getDrawable(popupBuilder.getCheckDrawable());
                    image.setImageDrawable(drawable);
                } else {
                    image.setImageDrawable(null);
                }
            } else {
                image.setImageDrawable(null);
            }
        }
    }

    public void setPopupBuilder(PopupBuilder popupBuilder) {
        this.popupBuilder = popupBuilder;
    }
}
