package com.lessonmaker.app.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lessonmaker.app.common.MainAppBackground
import com.lessonmaker.app.common.MainGradientBg
import com.lessonmaker.app.mainScreen.account.AccountScreenFlow
import com.lessonmaker.app.mainScreen.account.changePassword.ChangePasswordScreen
import com.lessonmaker.app.mainScreen.account.editProfile.EditProfileScreen
import com.lessonmaker.app.mainScreen.account.privacyAndPolicy.PrivacyAndPolicy
import com.lessonmaker.app.mainScreen.account.termsAndConditions.TermsAndCondition
import com.lessonmaker.app.mainScreen.bottomNavigation.NavigationWidget
import com.lessonmaker.app.mainScreen.category.CategoryScreen
import com.lessonmaker.app.mainScreen.category.subCategory.SubCategoryScreen
import com.lessonmaker.app.mainScreen.category.subCategory.subCategoryDetail.SubCategoryDetailScreen
import com.lessonmaker.app.mainScreen.category.subCategory.subCategoryDetail.quizDetail.QuizDetailScreen
import com.lessonmaker.app.mainScreen.category.subCategory.subCategoryDetail.quizDetail.quizQuestion.QuizQuestionsScreen
import com.lessonmaker.app.mainScreen.category.subCategory.subCategoryDetail.quizDetail.quizQuestion.yourPositionScreen.YourPositionScreen
import com.lessonmaker.app.mainScreen.home.AboutMe.AboutMeScreen
import com.lessonmaker.app.mainScreen.home.LaunchHomeScreenFlow
import com.lessonmaker.app.mainScreen.leaderBoard.LeaderBoardScreenFlow
import com.lessonmaker.app.mainScreen.notification.NotificationScreen
import com.lessonmaker.app.mainScreen.toolbar.ToolBar
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.navigationControler.Screen
import com.lessonmaker.app.shared.statusBarStyle.StatusBarIconColor
import com.lessonmaker.app.shared.statusBarStyle.setStatusBarIconColor
import com.lessonmaker.app.singleTopNavigation
import com.lessonmaker.app.theme.PrimaryColor
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DashboardScreen(destination: String? = null, newRouts: (String) -> Unit)
{
    val dashboardViewModel = koinViewModel<DashboardViewModel>()
    // val userViewModel = koinViewModel<UserModel>()
    val navController = rememberNavController()
    var alertMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    setStatusBarIconColor(StatusBarIconColor.Light)


    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->

        }
    }

    val scope = rememberCoroutineScope()

    val topModifier = if (dashboardViewModel.toolBarViewModel.showToolbar) {
        Modifier
            .fillMaxSize()
        //.statusBarsPadding()
        /*.padding(
            if (dashboardViewModel.showBottomNavigation) {
                PaddingValues(0.dp)
            } else {
                WindowInsets.navigationBars.asPaddingValues()
            }
        )*/
    } else {
        Modifier
            .fillMaxSize()
        /*.padding(
            if (dashboardViewModel.showBottomNavigation) {
                PaddingValues(0.dp)
            } else {
                WindowInsets.navigationBars.asPaddingValues()
            }
        )*/
    }


    var backColor = PrimaryColor


    Box(modifier = Modifier.background(color = backColor))
    {
        MaterialTheme() {
            Box {
                MainGradientBg()
                Scaffold(
                    modifier = topModifier,
                    containerColor = Transparent,
                    bottomBar = {
                        if (dashboardViewModel.showBottomNavigation) {
                            NavigationWidget(navigationViewModel = dashboardViewModel.navigationViewModel) {
                                if (it == "ADD") {

                                } else {
                                    navController.clearBackIfAtDashBoard(
                                        newPath = it,
                                        dashboardViewModel = dashboardViewModel
                                    )?.let {

                                        navController.singleTopNavigation(it)
                                    }
                                }
                            }
                        }

                    },

                    topBar = {
                        if (dashboardViewModel.toolBarViewModel.showToolbar) {
                            ToolBar(toolBarViewModel = dashboardViewModel.toolBarViewModel) {
                                when (it) {
                                    DashboardSubScreen.POPBackStack.route -> {
                                        navController.popBackStack()
                                    }

                                    DashboardSubScreen.ShowSideBar.route -> {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    }

                                    else -> {
                                        navController.navigate(it)
                                    }
                                }
                            }
                        }
                    }
                )
                { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize().background(Transparent)
                    ) {

                        val topBarPadding =
                            if (dashboardViewModel.toolBarViewModel.showToolbar) paddingValues.calculateTopPadding() else 0.dp

                        val bottomNavigationPadding =
                            if (dashboardViewModel.showBottomNavigation) 52.dp else 0.dp
                        Box(
                            modifier = Modifier.fillMaxSize()
                                .padding(top = topBarPadding)
                                .padding(
                                    bottom =
                                        if (dashboardViewModel.showBottomNavigation) {
                                            75.dp
                                        } else {
                                            0.dp
                                        }
                                )
                            /*.then(
                                if (dashboardViewModel.showBottomNavigation) {
                                    Modifier
                                        .padding(bottom = 75.dp)
                                        .navigationBarsPadding()
                                } else {
                                    Modifier
                                }
                            )*/
                            /*.then(
                                if (dashboardViewModel.showBottomNavigation) {
                                    Modifier.navigationBarsPadding()
                                } else {
                                    Modifier
                                }
                            )*/
                        )
                        {
                            Navigations(
                                destination = destination,
                                navController = navController,
                                dashboardViewModel
                            ) {
                                newRouts.invoke(it)
                            }
                        }

                    }
                }
            }

        }
    }


}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Navigations(
    destination: String?,
    navController: NavHostController,
    dashboardViewModel: DashboardViewModel,
    mainNavigationRoute: (String) -> Unit
) {
    /*LaunchedEffect(destination) {
        when {
            destination.handleNull()
                .startsWith(DashboardSubScreen.MyOrderDetailScreen.baseRoute) -> {
                navController.singleTopNavigation(destination.handleNull())
            }

            destination.handleNull()
                .startsWith(DashboardSubScreen.NotificationDetailsScreen.START_ROUTE) -> {
                navController.singleTopNavigation(destination.handleNull())
            }

            destination.handleNull()
                .startsWith(DashboardSubScreen.ProductDetailsScreenFlow.START_ROUTE) -> {
                navController.singleTopNavigation(destination.handleNull())
            }

            else -> {

            }
        }
    }*/



    NavHost(
        navController = navController,
        startDestination = DashboardSubScreen.LaunchHomeScreenFlow.route
    ) {

        composable(DashboardSubScreen.LaunchHomeScreenFlow.route) {
            dashboardViewModel.toolBarViewModel.updateToolBar(
                showToolbar = true,
                showAppLogo = false,
                showNotification = false,
                showBackText = false,
                backHeading = "Home"
            )
            dashboardViewModel.updateBottomNavigation(showBottomNavigation = true)

            LaunchHomeScreenFlow(dashboardViewModel = dashboardViewModel) {
                navController.clearBackIfAtDashBoard(
                    newPath = it,
                    dashboardViewModel = dashboardViewModel
                )?.let { newRoute ->
                    when {
                        newRoute == Screen.LoginScreenFlow.route -> {
                            mainNavigationRoute.invoke(newRoute)
                        }

                        else -> {
                            navController.navigate(newRoute)
                        }
                    }

                }
            }
        }
        composable(DashboardSubScreen.AboutMeScreen.route){

            dashboardViewModel.toolBarViewModel.updateToolBar(
                showToolbar = true,
                showBackImage = true,
                showShare = true,
                showBackText = true,
                backHeading = "About Me"
            )
            dashboardViewModel.updateBottomNavigation(showBottomNavigation = false)
            AboutMeScreen(dashboardViewModel=dashboardViewModel){
                navController.clearBackIfAtDashBoard(
                    newPath = it,
                    dashboardViewModel = dashboardViewModel
                )?.let { newRoute ->
                    when {
                        newRoute == Screen.LoginScreenFlow.route -> {
                            mainNavigationRoute.invoke(newRoute)
                        }

                        else -> {
                            navController.navigate(newRoute)
                        }
                    }

                }
            }
        }
        composable(DashboardSubScreen.CategoryScreen.route){
            dashboardViewModel.toolBarViewModel.updateToolBar(
                showToolbar = true,
                showBackImage = false,
                showShare = false,
                showBackText = true,
                backHeading = "Discover"
            )
            dashboardViewModel.updateBottomNavigation(showBottomNavigation = true)
            CategoryScreen(dashboardViewModel = dashboardViewModel){
                navController.clearBackIfAtDashBoard(
                    newPath = it,
                    dashboardViewModel = dashboardViewModel
                )?.let { newRoute ->
                    when {
                        newRoute == Screen.LoginScreenFlow.route -> {
                            mainNavigationRoute.invoke(newRoute)
                        }

                        else -> {
                            navController.navigate(newRoute)
                        }
                    }

                }

            }

        }
        composable (DashboardSubScreen.SubCategoryScreen.route){
            dashboardViewModel.toolBarViewModel.updateToolBar(
                showToolbar = true,
                showBackImage = true,
                showShare = true,
                showBackText = true,
                backHeading = "Sports"
            )
            dashboardViewModel.updateBottomNavigation(showBottomNavigation = true)
            SubCategoryScreen(dashboardViewModel = dashboardViewModel){

                navController.clearBackIfAtDashBoard(
                    newPath = it,
                    dashboardViewModel = dashboardViewModel
                )?.let { newRoute ->
                    when {
                        newRoute == Screen.LoginScreenFlow.route -> {
                            mainNavigationRoute.invoke(newRoute)
                        }

                        else -> {
                            navController.navigate(newRoute)
                        }
                    }

                }
            }

        }
        composable (DashboardSubScreen.SubCategoryDetailScreen.route){
            dashboardViewModel.toolBarViewModel.updateToolBar(
                showToolbar = true,
                showBackImage = true,
                showShare = true,
                showBackText = true,
                backHeading = "Soccer"
            )
            dashboardViewModel.updateBottomNavigation(showBottomNavigation = true)
            SubCategoryDetailScreen(dashboardViewModel = dashboardViewModel){
                navController.clearBackIfAtDashBoard(
                    newPath = it,
                    dashboardViewModel = dashboardViewModel
                )?.let { newRoute ->
                    when {
                        newRoute == Screen.LoginScreenFlow.route -> {
                            mainNavigationRoute.invoke(newRoute)
                        }

                        else -> {
                            navController.navigate(newRoute)
                        }
                    }

                }

            }
        }
        composable(DashboardSubScreen.QuizDetailScreen.route){
            dashboardViewModel.toolBarViewModel.updateToolBar(
                showToolbar = true,
                showBackImage = true,
                showShare = true,
                showBackText = true,
                backHeading = "Quiz Details"
            )
            dashboardViewModel.updateBottomNavigation(showBottomNavigation = false)
            QuizDetailScreen(dashboardViewModel = dashboardViewModel){
                navController.clearBackIfAtDashBoard(
                    newPath = it,
                    dashboardViewModel = dashboardViewModel
                )?.let { newRoute ->
                    when {
                        newRoute == Screen.LoginScreenFlow.route -> {
                            mainNavigationRoute.invoke(newRoute)
                        }

                        else -> {
                            navController.navigate(newRoute)
                        }
                    }

                }

            }

        }
        composable(DashboardSubScreen.QuizQuestionsScreen.route){
            dashboardViewModel.toolBarViewModel.updateToolBar(
                showToolbar = true,
                showBackImage = true,
                showShare = true,
                showBackText = true,
                backHeading = "Quiz Details"
            )
            dashboardViewModel.updateBottomNavigation(showBottomNavigation = false)
            QuizQuestionsScreen(dashboardViewModel = dashboardViewModel){
                navController.clearBackIfAtDashBoard(
                    newPath = it,
                    dashboardViewModel = dashboardViewModel
                )?.let { newRoute ->
                    when {
                        newRoute == Screen.LoginScreenFlow.route -> {
                            mainNavigationRoute.invoke(newRoute)
                        }

                        else -> {
                            navController.navigate(newRoute)
                        }
                    }

                }

            }

        }
        composable(DashboardSubScreen.YourPositionScreen.route){
            dashboardViewModel.toolBarViewModel.updateToolBar(
                showToolbar = true,
                showBackImage = true,
                showShare = true,
                showBackText = true,
                backHeading = "Quiz Details"
            )
            dashboardViewModel.updateBottomNavigation(showBottomNavigation = false)
            YourPositionScreen(dashboardViewModel = dashboardViewModel){
                navController.clearBackIfAtDashBoard(
                    newPath = it,
                    dashboardViewModel = dashboardViewModel
                )?.let { newRoute ->
                    when {
                        newRoute == Screen.LoginScreenFlow.route -> {
                            mainNavigationRoute.invoke(newRoute)
                        }

                        else -> {
                            navController.navigate(newRoute)
                        }
                    }

                }

            }

        }
        composable(DashboardSubScreen.NotificationScreen.route){
            dashboardViewModel.toolBarViewModel.updateToolBar(
                showToolbar = true,
                showBackImage = false,
                showShare = false,
                showBackText = true,
                backHeading = "Notifications"
            )
            dashboardViewModel.updateBottomNavigation(showBottomNavigation = true)
            NotificationScreen(dashboardViewModel = dashboardViewModel){
                navController.clearBackIfAtDashBoard(
                    newPath = it,
                    dashboardViewModel = dashboardViewModel
                )?.let { newRoute ->
                    when {
                        newRoute == Screen.LoginScreenFlow.route -> {
                            mainNavigationRoute.invoke(newRoute)
                        }

                        else -> {
                            navController.navigate(newRoute)
                        }
                    }

                }

            }

        }
        composable(DashboardSubScreen.AccountScreenFlow.route){
            dashboardViewModel.toolBarViewModel.updateToolBar(
            showToolbar = true,
            showBackImage = false,
            showShare = false,
            showBackText = true,
            backHeading = "Profile"
            )
            dashboardViewModel.updateBottomNavigation(showBottomNavigation = true)
            AccountScreenFlow(dashboardViewModel = dashboardViewModel){
                navController.clearBackIfAtDashBoard(
                    newPath = it,
                    dashboardViewModel = dashboardViewModel
                )?.let { newRoute ->
                    when {
                        newRoute == Screen.LoginScreenFlow.route -> {
                            mainNavigationRoute.invoke(newRoute)
                        }

                        else -> {
                            navController.navigate(newRoute)
                        }
                    }

                }

            }

        }
        composable(DashboardSubScreen.EditProfileScreen.route){
            dashboardViewModel.toolBarViewModel.updateToolBar(
                showToolbar = true,
                showBackImage = true,
                showShare = true,
                showBackText = true,
                backHeading = "Edit Profile"
            )
            dashboardViewModel.updateBottomNavigation(showBottomNavigation = false)
            EditProfileScreen(dashboardViewModel = dashboardViewModel){
                navController.clearBackIfAtDashBoard(
                    newPath = it,
                    dashboardViewModel = dashboardViewModel
                )?.let { newRoute ->
                    when {
                        newRoute == Screen.LoginScreenFlow.route -> {
                            mainNavigationRoute.invoke(newRoute)
                        }

                        else -> {
                            navController.navigate(newRoute)
                        }
                    }

                }

            }
        }
        composable(DashboardSubScreen.ChangePasswordScreen.route){
            dashboardViewModel.toolBarViewModel.updateToolBar(
                showToolbar = true,
                showBackImage = true,
                showShare = true,
                showBackText = true,
                backHeading = "Change Password"
            )
            dashboardViewModel.updateBottomNavigation(showBottomNavigation = false)
            ChangePasswordScreen(dashboardViewModel = dashboardViewModel){
                navController.clearBackIfAtDashBoard(
                    newPath = it,
                    dashboardViewModel = dashboardViewModel
                )?.let { newRoute ->
                    when {
                        newRoute == Screen.LoginScreenFlow.route -> {
                            mainNavigationRoute.invoke(newRoute)
                        }

                        else -> {
                            navController.navigate(newRoute)
                        }
                    }

                }

            }

        }
        composable(DashboardSubScreen.PrivacyAndPolicy.route){
            dashboardViewModel.toolBarViewModel.updateToolBar(
            showToolbar = true,
            showBackImage = true,
            showShare = true,
            showBackText = true,
            backHeading = "Privacy Policy"
            )
            dashboardViewModel.updateBottomNavigation(showBottomNavigation = false)
            PrivacyAndPolicy(dashboardViewModel = dashboardViewModel){
                navController.clearBackIfAtDashBoard(
                    newPath = it,
                    dashboardViewModel = dashboardViewModel
                )?.let { newRoute ->
                    when {
                        newRoute == Screen.LoginScreenFlow.route -> {
                            mainNavigationRoute.invoke(newRoute)
                        }

                        else -> {
                            navController.navigate(newRoute)
                        }
                    }

                }
            }

        }


        composable(DashboardSubScreen.TermsAndConditions.route){
            dashboardViewModel.toolBarViewModel.updateToolBar(
                showToolbar = true,
                showBackImage = true,
                showShare = true,
                showBackText = true,
                backHeading = "Terms And Conditions"
            )
            dashboardViewModel.updateBottomNavigation(showBottomNavigation = false)
            TermsAndCondition(dashboardViewModel = dashboardViewModel){
                navController.clearBackIfAtDashBoard(
                    newPath = it,
                    dashboardViewModel = dashboardViewModel
                )?.let { newRoute ->
                    when {
                        newRoute == Screen.LoginScreenFlow.route -> {
                            mainNavigationRoute.invoke(newRoute)
                        }

                        else -> {
                            navController.navigate(newRoute)
                        }
                    }

                }
            }

        }
        composable(DashboardSubScreen.LeaderBoardScreenFlow.route){
            dashboardViewModel.toolBarViewModel.updateToolBar(
                showToolbar = true,
                showBackImage = false,
                showShare = true,
                showBackText = true,
                backHeading = "Leaderboard"
            )
            dashboardViewModel.updateBottomNavigation(showBottomNavigation = true)
            LeaderBoardScreenFlow(dashboardViewModel = dashboardViewModel){
                navController.clearBackIfAtDashBoard(
                    newPath = it,
                    dashboardViewModel = dashboardViewModel
                )?.let { newRoute ->
                    when {
                        newRoute == Screen.LoginScreenFlow.route -> {
                            mainNavigationRoute.invoke(newRoute)
                        }

                        else -> {
                            navController.navigate(newRoute)
                        }
                    }

                }

            }

        }

















    }
}

fun NavHostController.clearBackIfAtDashBoard(
    newPath: String,
    dashboardViewModel: DashboardViewModel
): String? {
    if (this.currentDestination?.route.equals(newPath)) {
        return null
    }
    if (newPath.equals(DashboardSubScreen.POPBackStack.route)) {
        //dashboardViewModel.toolBarViewModel.backListener = null
        this.popBackStack()

        return null
    }
    val dashboardScreens = ArrayList<String>()
    dashboardScreens.add(DashboardSubScreen.LaunchHomeScreenFlow.route)


    /*dashboardScreens.add(DashboardSubScreen.AccountScreen.route)
    dashboardScreens.add(DashboardSubScreen.SavedVehiclesScreen.route)
    dashboardScreens.add(DashboardSubScreen.ThankyouScreenFlow.route)*/
    if (dashboardScreens.contains(newPath)) {
        this.navigate(newPath) {
            popUpTo(newPath) {
                inclusive = false
            }
            launchSingleTop = true
        }
        return null
    }
    return newPath
}

