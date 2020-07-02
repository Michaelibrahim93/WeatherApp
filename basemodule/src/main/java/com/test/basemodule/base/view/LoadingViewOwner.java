package com.test.basemodule.base.view;

/**
 * Created by Michael on 12/24/17.
 */

public interface LoadingViewOwner {
    int LOADING_PROGRESS_DIALOG = 0;
    int LOADING_OVERLAY = 1;

    void showLoading(int loadingMode);
    void hideLoading(int loadingMode);
}
