package com.lessonmaker.app.network



import com.lessonmaker.app.common.dialogue.UiState
import com.lessonmaker.app.di.CommonLoading
import com.lessonmaker.app.extensionFunctions.handleNull
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.errors.IOException

class ApiException(val statusCode: HttpStatusCode,val customMessage: String,val apiName:String="",val parameters:String="") : Exception(customMessage)

suspend inline fun <reified T> HttpClient.safePost(
    urlString: String,
    crossinline block: HttpRequestBuilder.() -> Unit = {}
): T {
    return try {
        val response: HttpResponse = this.post(urlString) { block() }
        when (response.status) {
            HttpStatusCode.OK, HttpStatusCode.Created -> response.body()
            else -> throw ApiException(response.status, response.bodyAsText())
        }
    } catch (e: ClientRequestException) { // 4xx Errors
        throw ApiException(statusCode = e.response.status, customMessage =  e.response.bodyAsText(), apiName = urlString, parameters = e.response.request.url.parameters.toString())
    } catch (e: ServerResponseException) { // 5xx Errors
        throw ApiException(statusCode = e.response.status, customMessage =  e.response.bodyAsText(), apiName = urlString, parameters = e.response.request.url.parameters.toString())
    }catch (e: IOException) { // Other Errors (Timeout, No Internet, etc.)
        throw ApiException(statusCode = HttpStatusCode(0,""), customMessage =  "Time out", apiName = urlString)
    }
    catch (e: Exception) { // Other Errors (Timeout, No Internet, etc.)
        throw ApiException(statusCode = HttpStatusCode(0,""), customMessage =  "canceled", apiName = urlString)
    }
}


abstract class BaseRepository {

    suspend inline fun <T> safeApiCall(commonLoading: CommonLoading, ignoreError:Boolean=false, crossinline apiCall: suspend () -> T): ApiResult<T> {
        return try {
            ApiResult.Success(apiCall())
        } catch (e: ApiException) {
            if (ignoreError.not()){
                e.statusCode.value.let {
                    if (it!=0){
                        commonLoading.apiException=e
                    }
                }
                commonLoading.apiCall= UiState.Failure(e.message.handleNull())
            }
            ApiResult.Error(e.statusCode, e.message ?: "Unknown error")
        }
    }

}

