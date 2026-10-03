package com.lessonmaker.app

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lessonmaker.app.authFLow.AgeAskingScreen
import com.lessonmaker.app.authFLow.LoginScreenFlow
import com.lessonmaker.app.authFLow.RegisterFLowScreen
import com.lessonmaker.app.authFLow.WelcomeScreen
import com.lessonmaker.app.base.BaseDialogInterface
import com.lessonmaker.app.base.HandleDialogs
import com.lessonmaker.app.di.CommonLoading
import com.lessonmaker.app.di.SharedPreferenceManager
import com.lessonmaker.app.di.isInitialized
import com.lessonmaker.app.di.koinConfig
import com.lessonmaker.app.mainScreen.DashboardScreen
import com.lessonmaker.app.navigationControler.Screen

import org.koin.compose.KoinApplication
import org.koin.compose.koinInject


@Composable

fun App() {
    if (org.koin.core.KoinApplication.Companion.isInitialized()) {
        com.lessonmaker.app.AfterKoinInject()
    } else {
      KoinApplication(application = koinConfig()) {
         com.lessonmaker.app.AfterKoinInject()
     }
    }

}
@Composable
fun AfterKoinInject(destination: String? = null) {
    var localDestination by remember { mutableStateOf(destination) }

    val commonLoading = koinInject<CommonLoading>()

    val sharedPreferenceManager: SharedPreferenceManager = koinInject()
    val navController = rememberNavController()


    var startPoint = Screen.WelcomeScreen.route
    if (sharedPreferenceManager.isLoggedIn) {
        startPoint = Screen.DashboardScreen.route
    }


    NavHost(navController = navController, startDestination = startPoint) {



        composable(route = Screen.DashboardScreen.route) { backStackEntry ->
            DashboardScreen(destination = destination) {
                localDestination = null

                when (it) {
                    Screen.POPBackStack.route -> navController.popBackStack()
                    Screen.LoginScreenFlow.route -> {
                        navController.singleTopNavigation(it)
                    }

                    else -> navController.singleTopNavigation(it)
                }


                /*      when (it) {
                          Screen.POPBackStack.route -> navController.popBackStack()
                          *//*Screen.LoginScreenFlow.route -> {
                        navController.singleTopNavigation(it)
                    }*//*
                    Screen.LoginScreenFlow.route -> {
                        // Clear ALL back stacks including dashboard and move to login
                        navController.navigate(it) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }

                    else -> navController.singleTopNavigation(it)
                }*/
            }
        }
        composable(route = Screen.WelcomeScreen.route) {  backStackEntry ->
              localDestination = null
            WelcomeScreen{
                when (it) {
                    Screen.DashboardScreen.route -> {
                        navController.clearAllAndMoveToDashboard()
                    }

                    else -> navController.navigate(it)
                }

            }

        }

        composable(route = Screen.LoginScreenFlow.route) { backStackEntry ->
            localDestination = null
            LoginScreenFlow {
                when (it) {
                    Screen.POPBackStack.route -> navController.popBackStack()
                    Screen.DashboardScreen.route -> {
                        navController.clearAllAndMoveToDashboard()
                    }

                    else -> navController.singleTopNavigation(it)
                }
            }
        }
        composable (route = Screen.AgeAskingScreen.route){
            localDestination = null
            AgeAskingScreen{
                when (it) {
                    Screen.POPBackStack.route -> navController.popBackStack()
                    Screen.DashboardScreen.route -> {
                        navController.clearAllAndMoveToDashboard()
                    }

                    else -> navController.singleTopNavigation(it)
                }
            }
        }

//        composable(route = Screen.ProfileScreen.route) { backStackEntry ->
//            localDestination = null
//            CompleteProfileScreen {
//                when (it) {
//                    Screen.POPBackStack.route -> navController.popBackStack()
//                    Screen.DashboardScreen.route -> {
//                        navController.clearAllAndMoveToDashboard()
//                    }
//
//                    else -> navController.singleTopNavigation(it)
//                }
//            }
//        }
        composable(route = Screen. RegisterFLowScreen.route) { backStackEntry ->
            localDestination = null
            RegisterFLowScreen{
                when (it) {
                    Screen.POPBackStack.route -> navController.popBackStack()
                    Screen.DashboardScreen.route -> {
                        navController.clearAllAndMoveToDashboard()
                    }

                    else -> navController.singleTopNavigation(it)
                }
            }
        }

    }

    HandleDialogs(commonLoading, object : BaseDialogInterface {
        override fun moveToLogin() {
            navController.clearAllAndMoveToLogin()
        }
    })

}

fun NavHostController.clearAllAndMoveToDashboard() {
    this.navigate(Screen.DashboardScreen.route) {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
    }
}

fun NavHostController.clearAllAndMoveToLogin() {
    this.navigate(Screen.LoginScreenFlow.route) {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
    }
}

fun NavHostController.singleTopNavigation(rout: String) {
    this.navigate(rout) {
        launchSingleTop = true
    }
}

fun NavHostController.clearAllAndMoveNew(route: String) {
    this.navigate(route) {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
    }
}


