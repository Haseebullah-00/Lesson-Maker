package com.lessonmaker.app.network


import com.lessonmaker.app.common.dataCellSheetSpecs.UploadBundle
import com.lessonmaker.app.di.CommonLoading
import com.lessonmaker.app.di.SharedPreferenceManager
import com.lessonmaker.app.models.CommonAPIResponse
import com.lessonmaker.app.models.auth.LoginAPIResponse
import com.lessonmaker.app.models.book.BooksResponse
import com.lessonmaker.app.models.course.AllowCourseAccessApiResponse
import com.lessonmaker.app.models.course.AllowedStudentCourseAccessApiResponse
import com.lessonmaker.app.models.student.StudentMyCoursesApiResponse
import com.lessonmaker.app.models.student.editAccess.EditAccessCoursesApiResponse
import com.lessonmaker.app.models.student.lesson.StudentMyCourseLessonApiResponse
import com.lessonmaker.app.models.student.lesson.lessonContent.StudentLessonContentApiResponse
import com.lessonmaker.app.models.teacher.AddLessonRequest
import com.lessonmaker.app.models.teacher.TeacherMyCourseLessonApiResponse
import com.lessonmaker.app.models.teacher.archivedCourses.TeacherArchivedCoursesApiResponse

import com.lessonmaker.app.models.teacher.myCourses.TeacherMyCoursesApiResponse

import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
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


    suspend fun dataCellLogin(email: String, password: String): ApiResult<CommonAPIResponse> {
        return safeApiCall(commonLoading = commonLoading) {
            client.safePost(
                urlString = NetworkUtils.BaseUrl.plus("AuthFlow/dataCellLogin"),
                block = {
                    parameter("email", email)
                    parameter("password", password)


                }
            )
        }
    }

    suspend fun uploadRecords(data: UploadBundle): ApiResult<CommonAPIResponse> {
        return safeApiCall(commonLoading = commonLoading) {
            client.safePost(
                urlString = NetworkUtils.BaseUrl.plus("DataCell/importData"),
                block = {
                    contentType(io.ktor.http.ContentType.Application.Json)
                    setBody(data)

                }
            )
        }
    }

    suspend fun login(email: String, password: String,role: String): ApiResult<LoginAPIResponse> {
        return safeApiCall(commonLoading = commonLoading) {
            client.safePost(
                urlString = NetworkUtils.BaseUrl.plus("AuthFlow/Login"),
                block = {
                    parameter("email", email)
                    parameter("password", password)
                    parameter("role",role)

                }
            )
        }
    }

    suspend fun getTeacherMyCourses(teacherId: Int): ApiResult<TeacherMyCoursesApiResponse> {
        return safeApiCall(commonLoading = commonLoading) {
            client.safeGet(
                urlString = NetworkUtils.BaseUrl.plus("Teacher/teacherCourses"),
                block = {
                    parameter("teacherId", teacherId)


                }
            )
        }
    }

    suspend fun getTeacherArchivedCourses(teacherId: Int): ApiResult<TeacherArchivedCoursesApiResponse> {
        return safeApiCall(commonLoading = commonLoading) {
            client.safeGet(
                urlString = NetworkUtils.BaseUrl.plus("Teacher/archivedCourses"),
                block = {
                    parameter("teacherId", teacherId)


                }
            )
        }
    }

    suspend fun getTeacherMyCoursesLessons(teacherId: Int,courseId: Int): ApiResult<TeacherMyCourseLessonApiResponse> {
        return safeApiCall(commonLoading = commonLoading) {
            client.safeGet(
                urlString = NetworkUtils.BaseUrl.plus("Teacher/getCourseLessons"),
                block = {
                    parameter("teacherId", teacherId)
                    parameter("courseId",courseId)


                }
            )
        }
    }
    suspend fun addLessonApi(teacherId: Int, courseId: Int, title: String, content: String): ApiResult<CommonAPIResponse> {
        return safeApiCall(commonLoading = commonLoading) {
            client.safePost(
                urlString = NetworkUtils.BaseUrl.plus("Teacher/addLesson"),
                block = {
                    parameter("teacherId", teacherId)      // short values can stay in the URL
                    parameter("courseId", courseId)
                    contentType(io.ktor.http.ContentType.Application.Json)
                    setBody(AddLessonRequest(title = title, content = content))
                }
            )
        }
    }

    suspend fun editLessonApi(lessonId: Int, title: String, content: String): ApiResult<CommonAPIResponse> {
        return safeApiCall(commonLoading = commonLoading) {
            client.safePost(
                urlString = NetworkUtils.BaseUrl.plus("Teacher/editLesson"),
                block = {
                    parameter("lessonId", lessonId)
                    contentType(io.ktor.http.ContentType.Application.Json)
                    setBody(AddLessonRequest(title = title, content = content))
                }
            )
        }
    }

//    suspend fun addLessonApi(teacherId: Int,courseId: Int,title: String,content: String): ApiResult<CommonAPIResponse> {
//        return safeApiCall(commonLoading = commonLoading) {
//            client.safePost(
//                urlString = NetworkUtils.BaseUrl.plus("Teacher/addLesson"),
//                block = {
//                    parameter("teacherId", teacherId)
//                    parameter("courseId",courseId)
//                    parameter("title",title)
//                    parameter("content",content)
//
//
//                }
//            )
//        }
//    }

    suspend fun deleteLessonApi(
        lessonId: Int
    ): ApiResult<CommonAPIResponse> {
        return safeApiCall(commonLoading = commonLoading) {
            client.safePost(urlString = NetworkUtils.BaseUrl.plus("Teacher/deleteLesson"), block = {
                parameter("lessonId", lessonId)

            })
        }
    }

    suspend fun allowAccessApi(
        teacherId: Int,
        studentId: Int,
        courseId: Int
    ): ApiResult<CommonAPIResponse> {
        return safeApiCall(commonLoading = commonLoading) {
            client.safePost(urlString = NetworkUtils.BaseUrl.plus("Course/allowAccess"), block = {
                parameter("teacherId", teacherId)
                parameter("studentId", studentId)
                parameter("courseId", courseId)

            })
        }
    }
    suspend fun revokeAccessApi(
        accessId: Int,
    ): ApiResult<CommonAPIResponse> {
        return safeApiCall(commonLoading = commonLoading) {
            client.safePost(urlString = NetworkUtils.BaseUrl.plus("Course/revokeAccess"), block = {
                parameter("accessId",accessId)


            })
        }
    }



    suspend fun getStudentMyCoursesApi(studentId: Int): ApiResult<StudentMyCoursesApiResponse> {
        return safeApiCall(commonLoading = commonLoading) {
            client.safeGet(
                urlString = NetworkUtils.BaseUrl.plus("Student/studentCourses"),
                block = {
                    parameter("studentId", studentId)


                }
            )
        }
    }
    suspend fun getStudentMyCoursesLessonsApi(teacherId: Int,courseId: Int,search: String): ApiResult<StudentMyCourseLessonApiResponse> {
        return safeApiCall(commonLoading = commonLoading) {
            client.safeGet(
                urlString = NetworkUtils.BaseUrl.plus("Student/getStudentCoursesLesson"),
                block = {
                    parameter("teacherId", teacherId)
                    parameter("courseId",courseId)
                    parameter("search",search)


                }
            )
        }
    }

    suspend fun getStudentMyCoursesLessonsContentApi(lessonId: Int): ApiResult<StudentLessonContentApiResponse> {
        return safeApiCall(commonLoading = commonLoading) {
            client.safeGet(
                urlString = NetworkUtils.BaseUrl.plus("Student/getLessonContent"),
                block = {
                    parameter("lessonId", lessonId)

                }
            )
        }
    }

    suspend fun getStudentToAllowCourseApi(teacherId: Int,courseId: Int?): ApiResult<AllowCourseAccessApiResponse> {
        return safeApiCall(commonLoading = commonLoading) {
            client.safeGet(
                urlString = NetworkUtils.BaseUrl.plus("Course/getAllowStudents"),
                block = {
                    parameter("teacherId",teacherId)
                    if(courseId !=null)
                    {
                        parameter("courseId",courseId)
                    }

                }
            )
        }
    }

    suspend fun getAllowedStudentApi(teacherId: Int,courseId: Int?): ApiResult<AllowedStudentCourseAccessApiResponse> {
        return safeApiCall(commonLoading = commonLoading) {
            client.safeGet(
                urlString = NetworkUtils.BaseUrl.plus("Course/getAllowedStudents"),
                block = {
                    parameter("teacherId",teacherId)
                    if(courseId !=null)
                    {
                        parameter("courseId",courseId)
                    }

                }
            )
        }
    }
    suspend fun getStudentEditAccessCoursesApi(studentId: Int): ApiResult<EditAccessCoursesApiResponse> {
        return safeApiCall(commonLoading = commonLoading) {
            client.safeGet(
                urlString = NetworkUtils.BaseUrl.plus("Student/getEditCourses"),
                block = {
                    parameter("studentId", studentId)


                }
            )
        }
    }

    suspend fun getStudentEditAccessCoursesLessonApi(teacherId: Int,studentId: Int,courseId: Int,search: String): ApiResult<StudentMyCourseLessonApiResponse> {
        return safeApiCall(commonLoading = commonLoading) {
            client.safeGet(
                urlString = NetworkUtils.BaseUrl.plus("Student/getEditCoursesLesson"),
                block = {
                    parameter("teacherId", teacherId)
                    parameter("courseId",courseId)
                    parameter("studentId",studentId)
                    parameter("search",search)


                }
            )
        }
    }


    suspend fun getAllBooks(): ApiResult<BooksResponse> {
        return safeApiCall(commonLoading = commonLoading) {
            client.safeGet(
                urlString = NetworkUtils.BaseUrl.plus("Book/GetBooks"),
                block = {

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
