package com.binding.adapter;

import android.support.annotation.Nullable;

import com.binding.interfaces.BindNetAdapter;
import com.binding.interfaces.BindNetAllAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public abstract class BaseRecycleAapter<T> extends BaseQuickAdapter<T,BaseViewHolder>
 implements BindNetAllAdapter {
    public BaseRecycleAapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    public void customNotifyItemChanged(int position) {
        notifyItemChanged(position);
    }

    @Override
    public void customNotifyItemRemoved(int position) {
        notifyItemRemoved(position);
    }

    @Override
    public void notifyMetaDataChange() {
        notifyDataSetChanged();
    }

    @Override
    public void customNotifyItemInsert(int position) {
        notifyItemInserted(position);
    }

    @Override
    public void customNotifyItemMove(int fromPositon, int toPosition) {
        notifyItemMoved(fromPositon,toPosition);
    }

    @Override
    public void customNotityRangeChanged(int startIndex, int changeCount) {
        notifyItemRangeChanged(startIndex,changeCount);
    }

    @Override
    public void customNotifyRangeInsert(int startIndex, int changeCount) {
        notifyItemRangeInserted(startIndex,changeCount);
    }

    @Override
    public void customNotifyRangeRemove(int startIndex, int changeCount) {
        customNotifyRangeRemove(startIndex,changeCount);
    }
}
