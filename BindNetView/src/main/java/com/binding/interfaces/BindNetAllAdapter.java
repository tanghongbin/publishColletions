package com.binding.interfaces;

/**
 * Created by tanghongbin on 2017/9/9.
 *
 */
public interface BindNetAllAdapter extends BindNetAdapter{

    void customNotifyItemChanged(int position);

    void customNotifyItemInsert(int position);

    void customNotifyItemMove(int fromPositon, int toPosition);

    void customNotifyItemRemoved(int position);

    void customNotityRangeChanged(int startIndex, int changeCount);

    void customNotifyRangeInsert(int startIndex, int changeCount);

    void customNotifyRangeRemove(int startIndex, int changeCount);


}
