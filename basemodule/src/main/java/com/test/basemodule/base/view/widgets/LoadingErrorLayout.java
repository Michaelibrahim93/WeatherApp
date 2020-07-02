package com.test.basemodule.base.view.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.test.basemodule.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Michael on 12/27/18.
 */

public class LoadingErrorLayout extends FrameLayout {
    public static final int STAT_DEFAULT = 0;
    public static final int STAT_LOADING = 1;
    public static final int STAT_ERROR = 2;
    public static final int STAT_LOADING_OVERLAY = 3;
    public static final int STAT_ERROR_OVERLAY = 4;

    @IntDef({STAT_DEFAULT, STAT_ERROR, STAT_LOADING, STAT_LOADING_OVERLAY, STAT_ERROR_OVERLAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewStat{}

    @LayoutRes int defaultRes;
    @LayoutRes int loadingRes;
    @LayoutRes int errorRes;

    View loadingView;
    View errorView;
    View defaultView;

    @ViewStat int viewStat;

    public LoadingErrorLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        extractAttrsValues(attrs);
        initView();
    }

    public LoadingErrorLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LoadingErrorLayout(@NonNull Context context, int defaultRes, int loadingRes, int errorRes) {
        super(context);
        this.defaultRes = defaultRes;
        this.loadingRes = loadingRes;
        this.errorRes = errorRes;
        initView();
    }

    private void extractAttrsValues(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LoadingErrorLayout);
        loadingRes = a.getResourceId(R.styleable.LoadingErrorLayout_loading_layout, -1);
        errorRes = a.getResourceId(R.styleable.LoadingErrorLayout_error_layout, -1);
        defaultRes = a.getResourceId(R.styleable.LoadingErrorLayout_default_layout, -1);
        a.recycle();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (defaultView == null)
            setDefaultViewValue();
    }

    private void setDefaultViewValue() {
        for (int i=0; i < getChildCount(); i++)
            if ( !getChildAt(i).equals(loadingView) && !getChildAt(i).equals(errorView))
                defaultView = getChildAt(i);
        if (defaultView != null){
            removeView(defaultView);
            addView(defaultView, 0);
        }
    }

    private void initView(){
        if (getChildCount() > 1 || (getChildCount() > 0 && defaultRes != -1))
            throw new IllegalArgumentException("LoadingErrorLayout can't hold more than one view");

        if (defaultRes != -1) {
            defaultView = inflate(getContext(), defaultRes, null);
            addView(defaultView);
        }
        else if (getChildCount() > 0)
            defaultView = getChildAt(0);

        if (errorRes != -1) {
            errorView = inflate(getContext(), errorRes, null);
            addView(errorView);
            errorView.setVisibility(GONE);
        }

        if (loadingRes != -1) {
            loadingView = inflate(getContext(), loadingRes, null);
            addView(loadingView);
            loadingView.setVisibility(GONE);
        }

        if (defaultView != null)
            defaultView.setVisibility(VISIBLE);
    }

    public void setViewStat(@ViewStat int viewStat) {
        this.viewStat = viewStat;

        boolean isDataVisible = viewStat == STAT_DEFAULT || viewStat == STAT_ERROR_OVERLAY
                || viewStat == STAT_LOADING_OVERLAY;
        boolean isLoadingVisible = viewStat == STAT_LOADING || viewStat == STAT_LOADING_OVERLAY;
        boolean isErrorVisible = viewStat == STAT_ERROR || viewStat == STAT_ERROR_OVERLAY;

        if (defaultView != null)
            defaultView.setVisibility(isDataVisible? VISIBLE : GONE);

        if (loadingView != null)
            loadingView.setVisibility(isLoadingVisible? VISIBLE : GONE);

        if (errorView != null)
            errorView.setVisibility(isErrorVisible? VISIBLE : GONE);
    }

    public int getViewStat() {
        return viewStat;
    }
}
