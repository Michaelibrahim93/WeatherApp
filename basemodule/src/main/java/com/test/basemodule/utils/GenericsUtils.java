package com.test.basemodule.utils;

import android.annotation.SuppressLint;

import com.google.gson.reflect.TypeToken;
import com.test.basemodule.base.viewmodel.BaseViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * Created by Michael on 12/27/17.
 */

public abstract class GenericsUtils {
    private static final String TAG = "GenericsUtils";

    @SuppressWarnings("unchecked")
    public static<T> Class<T> getViewModelClass(Class<?> genericClass) {

        Class<T> viewModelClass = null;

        Type[] genericTypes = ((ParameterizedType) Objects.requireNonNull(genericClass.getGenericSuperclass())).getActualTypeArguments();

        for(Type type : genericTypes) {
            try {
                if (type instanceof Class && BaseViewModel.class.isAssignableFrom((Class<?>) type)) {
                    viewModelClass = (Class<T>) type;
                }
            }catch (Throwable t){
                LogUtils.w(TAG, t);
            }
        }
        return viewModelClass;
    }
}

