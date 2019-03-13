package com.android_base.core.views.bindingViews.adpters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.android_base.core.interfaces.BindNetAdapter;

import java.util.List;

/**
 * Created by tanghongbin on 2017/9/25.
 */

public abstract class XBaseRecycleAdapter<ItemOBJ, RecycleHolder extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<RecycleHolder> implements BindNetAdapter {
    protected Context mContext;
    protected List<ItemOBJ> mList;
    protected AdapterView.OnItemClickListener onItemClickListener;

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public XBaseRecycleAdapter(Context mContext, List<ItemOBJ> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    /***
     * 判断不是listview之类的就可以点击
     * @param itemView
     * @return
     */
    private boolean assetCouldItemClickView(View itemView) {
        return itemView instanceof ListView == false &&
                itemView instanceof GridView == false &&
                itemView instanceof RecyclerView == false;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void notifyMetaDataChange() {
        notifyDataSetChanged();
    }
}
