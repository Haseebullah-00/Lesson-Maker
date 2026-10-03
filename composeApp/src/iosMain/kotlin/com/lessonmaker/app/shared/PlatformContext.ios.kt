package com.lessonmaker.app.shared

import androidx.compose.runtime.Composable

@Composable
actual fun getPlatformContext(): PlatformContext {
    return PlatformContext()
}

actual fun recreateActivity(context: PlatformContext) {
//    val window = UIApplication.sharedApplication.delegate?.window
//    val rootViewController = window?.rootViewController
//
//    // Create a new instance of your root view controller
//    val newRootVC = rootViewController?.storyboard?.instantiateInitialViewController()
//
//    if (newRootVC != null) {
//        window.rootViewController = newRootVC
//        window.makeKeyAndVisible()
//
//        // Optional: animate transition
//        UIView.transitionWithView(
//            view = window,
//            duration = 0.5,
//            options = UIViewAnimationOptionTransitionCrossDissolve,
//            animations = null,
//            completion = null
//        )
//    }
}

//actual class PlatformContext actual constructor()
actual class PlatformContext