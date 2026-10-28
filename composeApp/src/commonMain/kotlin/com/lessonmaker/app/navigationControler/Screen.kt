package com.lessonmaker.app.navigationControler

import com.lessonmaker.app.navigationControler.DashboardSubScreen.HomeDetailScreen.Companion.ID
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {


    @Serializable
    data object LoginScreenFlow: Screen("LoginScreenFlow")

    @Serializable
    data object DataCellLoginScreenFlow: Screen("DataCellLoginScreenFlow")
    @Serializable
    data object WelcomeScreen: Screen("WelcomeScreen")

    @Serializable
    data object AgeAskingScreen: Screen("AgeAskingScreen")


    @Serializable
    data object ProfileScreen : Screen("ProfileScreen")

    @Serializable
    data object  RegisterFLowScreen: Screen(" RegisterFLowScreen")


    @Serializable
    data object DashboardScreen : Screen("DashboardScreen")


    @Serializable
    data object POPBackStack : Screen("POPBackStack")


}

@Serializable
sealed class DashboardSubScreen(val route: String) {
    @Serializable
    data object LaunchHomeScreenFlow : DashboardSubScreen("LaunchHomeScreenFlow")

    @Serializable
    data object TeacherMyCoursesScreenFlow: DashboardSubScreen("TeacherMyCoursesScreenFlow")
    @Serializable
    data object  AboutMeScreen: DashboardSubScreen(" AboutMeScreen")
    @Serializable
    data object CategoryScreen: DashboardSubScreen("CategoryScreen")
    @Serializable
    data object SubCategoryScreen: DashboardSubScreen("SubCategoryScreen")
    @Serializable
    data object SubCategoryDetailScreen: DashboardSubScreen("SubCategoryDetailScreen")
    @Serializable
    data object  QuizDetailScreen: DashboardSubScreen(" QuizDetailScreen")
    @Serializable
    data object  QuizQuestionsScreen: DashboardSubScreen(" QuizQuestionsScreen")
    @Serializable
    data object YourPositionScreen: DashboardSubScreen("YourPositionScreen")
    @Serializable
    data object NotificationScreen: DashboardSubScreen("NotificationScreen")

    @Serializable
    data object LaunchSearchFlow : DashboardSubScreen("LaunchSearchFlow")

    @Serializable
    data object LaunchAddListingFlow : DashboardSubScreen("LaunchAddListingFlow")

    @Serializable
    data object LaunchWishListFlow : DashboardSubScreen("LaunchWishListFlow")


    @Serializable
    data object AccountScreenFlow : DashboardSubScreen("AccountScreenFlow")
    @Serializable
    data object  EditProfileScreen: DashboardSubScreen(" EditProfileScreen")
    @Serializable
    data object ChangePasswordScreen: DashboardSubScreen("ChangePasswordScreen")
    @Serializable
    data object  PrivacyAndPolicy: DashboardSubScreen(" PrivacyAndPolicy")
    @Serializable
    data object  TermsAndConditions: DashboardSubScreen("TermsAndConditions")
    @Serializable
    data object LeaderBoardScreenFlow: DashboardSubScreen("LeaderBoardScreenFlow")

    @Serializable
    data object LoginScreenFlow : Screen("LoginScreenFlow")



    @Serializable
    data object ProfileScreen : Screen("ProfileScreen")

    @Serializable
    data class HomeDetailScreen(val id: String) : DashboardSubScreen(START_ROUTE) {
        companion object {
            const val ID = "ID"
            const val START_ROUTE = "HomeDetailScreen"
            const val BASE_ROUTE = "$START_ROUTE/{$ID}"
            fun createRoute(id: String) = "$START_ROUTE/$id"
        }
    }


    @Serializable
    data class TeacherLessonScreenFlow(
        val courseId: String,
        val courseCode: String,
        val courseName: String
    ) : DashboardSubScreen(START_ROUTE) {

        companion object {
            const val START_ROUTE = "TeacherLessonScreenFlow"
            const val COURSE_ID = "courseId"
            const val COURSE_CODE = "courseCode"
            const val COURSE_NAME = "courseName"

            const val BASE_ROUTE =
                "$START_ROUTE/{$COURSE_ID}/{$COURSE_CODE}/{$COURSE_NAME}"

            fun createRoute(
                courseId: String,
                courseCode: String,
                courseName: String
            ) = "$START_ROUTE/$courseId/$courseCode/$courseName"
        }
    }


    @Serializable
    data class  TeacherAddLessonScreenFLow(
        val courseId: String,
        val courseCode: String,
        val courseName: String
    ) : DashboardSubScreen(START_ROUTE) {

        companion object {
            const val START_ROUTE = "TeacherAddLessonScreenFLow"
            const val COURSE_ID = "courseId"
            const val COURSE_CODE = "courseCode"
            const val COURSE_NAME = "courseName"

            const val BASE_ROUTE =
                "$START_ROUTE/{$COURSE_ID}/{$COURSE_CODE}/{$COURSE_NAME}"

            fun createRoute(
                courseId: String,
                courseCode: String,
                courseName: String
            ) = "$START_ROUTE/$courseId/$courseCode/$courseName"
        }
    }

    @Serializable
    data object TeacherRequestsScreenFlow : DashboardSubScreen("TeacherRequestsScreenFlow")

    @Serializable
    data object TeacherProfileScreenFlow : DashboardSubScreen("TeacherProfileScreenFlow")
    @Serializable
    data object ColleaguesCoursesScreenFlow: DashboardSubScreen("ColleaguesCoursesScreenFlow")
    @Serializable
    data object AddColleaguesCoursesScreenFlow: DashboardSubScreen("AddColleaguesCoursesScreenFlow")

    @Serializable
    data object NotesApprovalsScreenFlow: DashboardSubScreen("NotesApprovalsScreenFlow")
    @Serializable
    data object AllowAccessScreenFlow: DashboardSubScreen("AllowAccessScreenFlow")
    @Serializable
    data object AllowedAccessScreenFlow: DashboardSubScreen("AllowedAccessScreenFlow")

    @Serializable
    data class  ReviewEditRequestScreenFlow(
        val requestId: String
    ) : DashboardSubScreen(START_ROUTE) {

        companion object {
            const val START_ROUTE = "ReviewEditRequestScreenFlow"
            const val REQUEST_ID = "requestId"
            const val BASE_ROUTE = "$START_ROUTE/{$REQUEST_ID}"

            fun createRoute(requestId: String) = "$START_ROUTE/$requestId"
        }
    }

    @Serializable
    data object TeacherArchivedScreenFlow: DashboardSubScreen("TeacherArchivedScreenFlow")
    @Serializable
    data object LaunchLatestListingScreen : Screen("LaunchLatestListingScreen")

    @Serializable
    data object POPBackStack : DashboardSubScreen("POPBackStack")

    @Serializable
    data object ShowSideBar : DashboardSubScreen("ShowSideBar")

    @Serializable
    data object PickLocation : DashboardSubScreen("PickLocation")


}