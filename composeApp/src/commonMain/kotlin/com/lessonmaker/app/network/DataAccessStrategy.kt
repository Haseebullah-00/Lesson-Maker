package com.lessonmaker.app.network

import com.lessonmaker.app.di.CommonLoading


fun <T> getOnlySuccess(data: ApiResult<T>, commonLoading: CommonLoading, success:(T)->Unit) {
    when(data){
        is ApiResult.Success ->{
            data.data?.let {
                success.invoke(it)
            }
        }
        is ApiResult.Error ->{
            commonLoading.alertMessage=data.statusCode.toString()+data.message
        }
    }
}

inline fun <T> ApiResult<T>.getOnlySuccess():T? {
    return when (this) {
        is ApiResult.Success -> {
            data
        }
        is ApiResult.Error -> {
            null
        }
    }
}

