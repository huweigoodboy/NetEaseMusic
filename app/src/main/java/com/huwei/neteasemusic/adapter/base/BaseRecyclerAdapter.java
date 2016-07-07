package com.huwei.neteasemusic.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.huwei.neteasemusic.BaseActivity;
import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.manager.ImageLoader;
import com.huwei.neteasemusic.util.LogUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * recyclerView的adapter的抽象
 * <p/>
 * 1,继承此类的获取ImageLoader 调用getImageLoader()方法
 * 2,由于加入nodataView getItemCount（）和　getDataCount（）有本质区别
 *
 * @author jerry
 * @date 2016/03/14
 */
public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = "BaseRecyclerAdapter";

    protected List<T> mDatas = new ArrayList<>();
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    private ImageLoader mImageLoader;

    AdapterView.OnItemClickListener mItemClickListener;

    protected boolean isDataSet = false; //数据被设置过 无论有没有改变

    public static final int TYPE_NODATA = -0x111111;    //无数据时的type

    public BaseRecyclerAdapter(Context mContext) {
        this.mContext = mContext;

        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtils.i(TAG, "onCreateViewHolder type:" + viewType);
//        if (viewType == TYPE_NODATA) {
//            //默认为parent的高度
//            View viewNoData = mLayoutInflater.inflate(R.layout.layout_nodata, null, true);
//            viewNoData.setLayoutParams(new ViewGroup.LayoutParams(parent.getMeasuredWidth(), parent.getMeasuredHeight()));
//            NoDataViewHolder noDataViewHolder = new NoDataViewHolder(viewNoData);
//            return noDataViewHolder;
//        }
        return onCreateContentViewHolder(parent, viewType);
    }

    public abstract VH onCreateContentViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        int type = getItemViewType(position);
//        if (type == TYPE_NODATA) {
//            LogUtils.i(TAG, "needNoDataView:" + needNoDataView() + " itemView visiable:" + holder.itemView.getVisibility());
//            if (needNoDataView()) {
//                holder.itemView.setVisibility(View.VISIBLE);
//                initNoDataViewHolder((NoDataViewHolder) holder);
//            } else {
//                holder.itemView.setVisibility(View.GONE);
//            }
//        } else if (type != RecyclerAdapterWithHF.TYPE_HEADER && type != RecyclerAdapterWithHF.TYPE_FOOTER) {



            final T item = getItem(position);
            bindItemData((VH) holder, item, position);
            setupOnItemClick((VH) holder, position);
//        }
    }

    protected abstract void bindItemData(VH viewHolder, T data, int position);

    protected boolean isNeedRipple(){
        return true;
    }

    protected boolean needNoDataView() {
        return false;
    }

//    protected void initNoDataViewHolder(NoDataViewHolder noDataViewHolder) {
//
//    }

    /**
     * 判断什么是否无数据
     *
     * @return
     */
    protected boolean isNoData() {
        return getDataCount() == 0 && isDataSet && needNoDataView();
    }

    protected void setupOnItemClick(final VH viewHolder, final int position) {
        if(isNeedRipple()){
            viewHolder.itemView.setBackgroundResource(R.drawable.common_ripple_dark_selector);
            viewHolder.itemView.setClickable(true);
        }

        if (mItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(null, viewHolder.itemView, position, position);
                }
            });
        }
    }

    /**
     * 获取视图个数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        int count = 0;
        if (isNoData()) {
            count = 1;
            return count;
        }
        count = mDatas.size();
        //LogUtils.i(TAG, "getItemCount:" + count + " className:" + getClass().getSimpleName());
        return count;
    }

    /**
     * 子类覆盖此方法　一定要注意　调用super.getItemViewType()　否则nodataView可能失效
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (isNoData()) {
            return TYPE_NODATA;
        }
        return super.getItemViewType(position);
    }

    /**
     * 获取数据个数
     *
     * @return
     */
    public int getDataCount() {
        return mDatas.size();
    }

    public T getItem(int position) {
        position = Math.max(0, position);
        return mDatas.get(position);
    }

    public List<T> getDataList() {
        return mDatas;
    }

    public void addData(T t) {
        if (t != null) {
            mDatas.add(t);
            isDataSet = true;
        }
    }

    public void addDataList(List<T> newItems) {
        if (newItems != null) {
            mDatas.addAll(newItems);
            isDataSet = true;
        }
    }

    public void addData(int index, T t) {
        if (t != null) {
            mDatas.add(index, t);
            isDataSet = true;
        }
    }

    public void addDataList(int index, List<T> newItems) {
        if (newItems != null) {
            mDatas.addAll(index, newItems);
            isDataSet = true;
        }
    }

    public void setDataList(List<T> lists) {
        mDatas.clear();
        isDataSet = true;
        if (lists != null) {
            mDatas.addAll(lists);
        }
    }

    public void clear() {
        mDatas.clear();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public ImageLoader getImageLoader() {
        if (mImageLoader == null) {
            if(mContext instanceof BaseActivity){
                mImageLoader = ((BaseActivity)mContext).getImageLoader();
            }
        }
        return mImageLoader;
    }

//    /**
//     * 其他　nodataView视图样式　extends　此类
//     */
//    public static class NoDataViewHolder extends BaseViewHolder {
//
//        public TextView tvNodata;
//
//        public NoDataViewHolder(View itemView) {
//            super(itemView);
//
//            tvNodata = (TextView) findViewById(R.id.tv_nodata);
//        }
//    }
}
