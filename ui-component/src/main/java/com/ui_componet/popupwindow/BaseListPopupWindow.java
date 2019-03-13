package com.ui_componet.popupwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;


import com.ui_componet.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tanghongbin on 2017/3/11.
 */

public class BaseListPopupWindow extends PopupWindow {
    private ListView listView;
    private BasePopAdapter adapter;
    private OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setPopupBuilder(PopupBuilder popupBuilder) {
        adapter.setPopupBuilder(popupBuilder);
        adapter.notifyDataSetChanged();
    }
    public void setBackgroundColor(int color){
        listView.setBackgroundColor(color);
    }

    private List mList = new ArrayList<>();

    public void addList(List<? extends BasePopupObj> list) {
        if (list != null) {
            mList.addAll(list);
        }
        adapter.notifyDataSetChanged();
    }

    public BaseListPopupWindow(Context context) {
        super(context);
        init(context);
    }

    /**
     * 设置显示位置，上还是下方
     */
    public void setPosition(Position position) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                listView.getLayoutParams();
        if (position == Position.TOP) {
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        } else {
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        }
    }

    public RelativeLayout.LayoutParams getListViewParams(){
        return (RelativeLayout.LayoutParams) listView.getLayoutParams();
    }

    public void setListviewParams(RelativeLayout.LayoutParams params){
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    public BaseListPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View view = View.inflate(context, R.layout.base_list_popup, null);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BasePopupObj obj = (BasePopupObj) mList.get(position);
                clearStatus();
                if (obj != null) {
                    obj.setCheceked(true);
                }
                adapter.notifyDataSetChanged();
                if (itemClickListener != null){
                    itemClickListener.onItemClick(position,obj);
                }
            }
        });
        adapter = new BasePopAdapter(context, mList);
        listView.setAdapter(adapter);
        listView.setDivider(context.getResources().getDrawable(R.drawable.drawable_line));
        listView.setDividerHeight(1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setContentView(view);
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setBackgroundDrawable(context.getResources().getDrawable(R.drawable.one_of_thrid_back));
        View.OnClickListener clickDismissListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        };
        view.setOnClickListener(clickDismissListener);
    }

    private void clearStatus() {
        Iterator itetator = mList.iterator();
        while (itetator.hasNext()) {
            BasePopupObj obj = (BasePopupObj) itetator.next();
            obj.setCheceked(false);
        }
    }

    private int[] getWidths(Context context) {
        WindowManager manager = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return new int[]{metrics.widthPixels, metrics.heightPixels};
    }

    public enum Position {
        TOP, BOTTOM
    }

    public interface OnItemClickListener{
        void onItemClick(int position,BasePopupObj popupObj);
    }
}
