package com.dlovan.fullmovie.network;

/**
 * Created by dlovan on 7/11/17.
 */

public interface MovieCallback<T> {

    void onSuccess(T t);

    void onError(Throwable e);
}
