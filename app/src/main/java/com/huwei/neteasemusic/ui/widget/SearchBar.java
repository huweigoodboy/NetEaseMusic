package com.huwei.neteasemusic.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.util.LogUtils;
import com.huwei.neteasemusic.util.StringUtils;

/**
 * actionbar 搜索栏
 *
 * @author jerry
 * @date 2016/06/30
 */
public class SearchBar extends LinearLayout implements View.OnClickListener{

    public static final String TAG = "SearchBar";

    private Context mContext;
    private EditText mEtInput;
    private ImageView mIvDelete;
    private ImageView mIvSearchGo;

    private boolean mSearchGoOn = true;
    private String mHint;

    public SearchBar(Context context) {
        this(context, null);
    }

    public SearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        setOrientation(HORIZONTAL);

        initAttrs(attrs);
        initView();
        initListener();

        changeBg(mEtInput.hasFocus());
    }

    void initAttrs(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.SearchBar, 0, 0);

        mHint = a.getString(R.styleable.SearchBar_hint);
        mSearchGoOn = a.getBoolean(R.styleable.SearchBar_isSearchGoON, true);
        a.recycle();
    }

    void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_search_bar, this, true);
        mIvSearchGo = (ImageView) findViewById(R.id.iv_search_go);
        mIvDelete = (ImageView) findViewById(R.id.iv_delete);
        mEtInput = (EditText) findViewById(R.id.et_input);

        if (StringUtils.isNotEmpty(mHint)) {
            mEtInput.setHint(mHint);
        }
    }

    void initListener() {
        mEtInput.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                LogUtils.i(TAG, "onFocusChange:" + hasFocus);

                changeBg(hasFocus);
                if (mSearchGoOn) {
                    mIvSearchGo.setVisibility(hasFocus ? VISIBLE : GONE);
                }
            }
        });
        mEtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtils.isNotEmpty(s.toString())) {
                    mIvDelete.setVisibility(VISIBLE);
                } else {
                    mIvDelete.setVisibility(GONE);
                }
            }
        });

        mIvDelete.setOnClickListener(this);
    }

    private void changeBg(boolean hasFocus) {
        if (hasFocus) {
            setBackgroundResource(R.drawable.search_bar_line_focused);
        } else {
            setBackgroundResource(R.drawable.search_bar_line_normal);
        }
    }

    public void clearFocusR() {
        if (mEtInput != null) {
            mEtInput.clearFocus();
        }
    }

    public void requestFocusR() {
        if (mEtInput != null) {
            mEtInput.requestFocus();
        }
    }

    public boolean isFocusedR() {
        if (mEtInput != null) {
            return mEtInput.isFocused();
        }
        return false;
    }

    public EditText getEtinput(){
        return mEtInput;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_delete:
                mEtInput.setText("");
                break;
            default:
                break;
        }
    }
}
