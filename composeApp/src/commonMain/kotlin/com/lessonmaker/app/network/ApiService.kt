package com.lessonmaker.app.network


import com.lessonmaker.app.di.CommonLoading
import com.lessonmaker.app.di.SharedPreferenceManager
import com.lessonmaker.app.extensionFunctions.handleNull
import com.lessonmaker.app.models.auth.OtpResponse
import com.lessonmaker.app.shared.platformExtensions.deviceType

import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.headers



class ApiService(
    private val client: HttpClient,
    private val sharedPreferenceManager: SharedPreferenceManager,
    val commonLoading: CommonLoading
) :
    BaseRepository() {

    var appHeader = headers {
        append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        if (sharedPreferenceManager.isLoggedIn) {
            sharedPreferenceManager.accessToken?.let {
                append("Authorization", "Bearer $it")
            }
        }
    }


    suspend fun loginUser(email: String, password: String): ApiResult<OtpResponse> {
        return safeApiCall(commonLoading = commonLoading) {
            client.safePost(
                urlString = NetworkUtils.BaseUrl.plus("auth/email_login"),
                block = {
                    parameter("email", email)
                    parameter("password", password)
                    parameter("device_type", deviceType())
                    parameter("fcm_token", sharedPreferenceManager.getFcmToken.handleNull())
                }
            )
        }
    }


}

/*
class ApiService(private val client: HttpClient,
                 private val sharedPreferenceManager: SharedPreferenceManager,
                 val commonLoading: CommonLoading
) {
    companion object{
        const val BaseUrl="https://dxbitprojects.com/handify_web_new/public/api/v1/"
    }
    var appHeader=headers {
        append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        if (sharedPreferenceManager.isLoggedIn){
            sharedPreferenceManager.accessToken?.let {
                append("Authorization","Bearer $it")
            }
        }
    }



    suspend fun registerUser(registerRequestModel: RegisterRequestModel): RegisterAPIResponse {
        return client.submitForm(
            url = BaseUrl.plus("user/signup"),
            formParameters = Parameters.build{
                append("first_name",registerRequestModel.firstName)
                append("last_name",registerRequestModel.lastName)
                append("dial_code",registerRequestModel.dialCode)
                append("phone",registerRequestModel.phoneNumber)
                append("email",registerRequestModel.email)
                append("password",registerRequestModel.password)
                append("conf_password",registerRequestModel.password)
                append("address",registerRequestModel.location.address)
                append("latitude",registerRequestModel.location.lat)
                append("longitude",registerRequestModel.location.lng)
                append("device_type", Constants.DEVICE_TYPE)
                append("fcm_token","fcm_token")
            }
        ).body()
    }


    suspend fun verifyOTP(otp:String,user_id:String) : OtpResponse {
        return client.submitForm(
            url = BaseUrl.plus("auth/confirm_phone_code"),
            formParameters = Parameters.build{
                append("otp",otp)
                append("user_id",user_id)
                append("device_type",Constants.DEVICE_TYPE)
                append("fcm_token","fcm_token")
            }
        ).body()
    }

    }

    suspend fun loginUser(email: String, password: String): ApiResult<OtpResponse> {
        return safeApiCall(commonLoading = commonLoading) {
            client.safePost(
                urlString = BaseUrl.plus("auth/email_login"),
                block = {
                    parameter("email", email)
                    parameter("password", password)
                    parameter("device_type", deviceType())
                    parameter("fcm_token", sharedPreferenceManager.getFcmToken.handleNull())
                    parameter("device_cart_id", getDeviceId())
                }
            )
        }
    }
    suspend fun getCMSPage(id:String): CMSResponse {
        return client.post(
            urlString = "https://www.urbanmop.com/api/cms/page",
            block = {
                parameter("id",id)
            }
        ).body()
    }
    suspend fun getFAQ(): FAQResponse {
        return client.post(
            urlString = "https://mydrworld.com/api/v1/get_faq",
            block = {
            }
        ).body()
    }
}*/
