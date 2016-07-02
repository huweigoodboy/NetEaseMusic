package com.huwei.neteasemusic.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.huwei.neteasemusic.adapter.base.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * @author jerry
 * @date 2016/03/24
 */
public abstract class HeaderBottomAdapter<T, VH extends RecyclerView.ViewHolder> extends BaseRecyclerAdapter<T, VH> {

    public static final int TYPE_HEADER = 17898;
    public static final int TYPE_FOOTER = 17899;

    private List<View> mHeaders = new ArrayList<View>();
    private List<View> mFooters = new ArrayList<View>();

    public HeaderBottomAdapter(Context mContext) {
        super(mContext);
    }

    public int getHeadSize() {
        return mHeaders.size();
    }

    public int getFootSize() {
        return mFooters.size();
    }

    // add a header to the adapter
    public void addHeader(View header) {
        if (!mHeaders.contains(header)) {
            mHeaders.add(header);
            // animate
            notifyItemInserted(mHeaders.size() - 1);
        }
    }

    // remove a header from the adapter
    public void removeHeader(View header) {
        if (mHeaders.contains(header)) {
            // animate
            notifyItemRemoved(mHeaders.indexOf(header));
            mHeaders.remove(header);
        }
    }

    public void removeAllHeaders() {
        for (View view : mHeaders) {
            removeHeader(view);
        }
        mHeaders.clear();
    }

    // add a footer to the adapter
    public void addFooter(View footer) {
        if (!mFooters.contains(footer)) {
            mFooters.add(footer);
            // animate
            notifyItemInserted(mHeaders.size() + getItemCountHF() + mFooters.size() - 1);
        }
    }

    // remove a footer from the adapter
    public void removeFooter(View footer) {
        if (mFooters.contains(footer)) {
            // animate
            notifyItemRemoved(mHeaders.size() + getItemCountHF() + mFooters.indexOf(footer));
            mFooters.remove(footer);
        }
    }

    public void removeAllFooters() {
        for (View view : mFooters) {
            removeFooter(view);
        }
        mFooters.clear();
    }

    private boolean isHeader(int position) {
        return (position < mHeaders.size());
    }

    private boolean isFooter(int position) {
        return (position >= mHeaders.size() + getItemCountHF());
    }

    /**
     * 判断是否有数据
     *
     * @return
     */
    protected boolean isNoData() {
        return (noDataContainHF() ?
                getDataCount() + mHeaders.size() + mFooters.size() : getDataCount()) == 0
                && isDataSet && needNoDataView();
    }

    /**
     * 无数据判断是否包含 头部 和 尾部
     *
     * @return
     */
    protected boolean noDataContainHF() {
        return true;
    }

    /**
     * 获取除了头和尾以外的视图个数
     *
     * @return
     */
    private int getItemCountHF() {
        return super.getItemCount();
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER || viewType == TYPE_FOOTER) {
            // create a new framelayout, or inflate from a resource
            FrameLayout frameLayout = new FrameLayout(parent.getContext());
            // make sure it fills the space
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                    .LayoutParams.WRAP_CONTENT));
            return new HeaderFooterViewHolder(frameLayout);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
//        if (type == TYPE_NODATA) {
//            LogUtils.i(TAG, "needNoDataView:" + needNoDataView() + " itemView visiable:" + holder.itemView.getVisibility());
//            if (needNoDataView()) {
//                holder.itemView.setVisibility(View.VISIBLE);
//                initNoDataViewHolder((NoDataViewHolder) holder);
//            } else {
//                holder.itemView.setVisibility(View.GONE);
//            }
//        } else
        if (type == HeaderBottomAdapter.TYPE_HEADER || type == HeaderBottomAdapter.TYPE_FOOTER) {
            if (isHeader(position)) {
                View v = mHeaders.get(position);
                // add our view to a header view and display it
                prepareHeaderFooter((HeaderFooterViewHolder) holder, v);
            } else if (isFooter(position)) {
                View v = mFooters.get(position - getItemCountHF() - mHeaders.size());
                // add our view to a footer view and display it
                prepareHeaderFooter((HeaderFooterViewHolder) holder, v);
            }
        } else {
            int cPos = position - getHeadSize(); //转化为dataList的pos
            final T item = getItem(cPos);
            bindItemData((VH) holder, item, cPos);
            setupOnItemClick((VH) holder, cPos);
        }
    }

    @Override
    public int getItemCount() {
        return mHeaders.size() + getItemCountHF() + mFooters.size();
    }

    @Override
    public final int getItemViewType(int position) {
        // check what type our position is, based on the assumption that the
        // order is headers > items > footers
        if (isHeader(position)) {
            return TYPE_HEADER;
        } else if (isFooter(position)) {
            return TYPE_FOOTER;
        }
        int type = super.getItemViewType(position);
        if (type == TYPE_HEADER || type == TYPE_FOOTER) {
            throw new IllegalArgumentException("Item type cannot equal " + TYPE_HEADER + " or " + TYPE_FOOTER);
        }
        return type;
    }

    private void prepareHeaderFooter(HeaderFooterViewHolder vh, View view) {
        // if the view already belongs to another layout, remove it
         if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }

        // empty out our FrameLayout and replace with our header/footer
        vh.base.removeAllViews();
        vh.base.addView(view);
    }


    // our header/footer RecyclerView.ViewHolder is just a FrameLayout
    public static class HeaderFooterViewHolder extends RecyclerView.ViewHolder {
        FrameLayout base;

        public HeaderFooterViewHolder(View itemView) {
            super(itemView);
            base = (FrameLayout) itemView;
        }
    }
}
