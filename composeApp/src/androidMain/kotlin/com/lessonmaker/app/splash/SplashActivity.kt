package com.lessonmaker.app.splash

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.lessonmaker.app.MainActivity
import com.lessonmaker.app.R
import com.lessonmaker.app.theme.Medium
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.TextSize18
import com.lessonmaker.app.theme.TextSize22
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.bgColor
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.semiBold
import com.lessonmaker.app.utility.handleEmpty

import kotlin.jvm.java


class SplashActivity : AppCompatActivity() {
    var SPLASH_DISPLAY_LENGTH: Long = 2200

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        this.hideSystemBars()
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(bgColor)
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.ic_splash_bg),
                    contentDescription = "splash_logo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_splash_main_logo),
                    contentDescription = "splash_logo",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(250.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 30.dp),

                    contentAlignment = Alignment.Center // Center the text within the Box
                )
                {
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(bottom = 15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_youtube),
                                contentDescription = "youtube",
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "/@ettanehsan",
                                style = TextSize18().Medium(),
                                color = White,
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = White
                                    )
                                ) {
                                    append("Created by")
                                }
                                append(" ")
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = White
                                    )
                                ) {
                                    append("Ehsan Adouane,")
                                }
                                append("\n")
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = White
                                    )
                                ) {
                                    append("Science & Tech Innovator")
                                }
                            },

                            color = White,
                            style = TextSize18(),
                            textAlign = TextAlign.Center
                        )
                    }
                }

            }
        }

        callToNextActivity()
    }


    private fun callToNextActivity() {
        Handler(mainLooper).postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java).putExtras(intent.extras.handleEmpty()))
            finish()
        }, SPLASH_DISPLAY_LENGTH)


    }

}
fun AppCompatActivity.hideSystemBars() {
    val actionBar = supportActionBar
    if (actionBar != null) actionBar.hide()

    val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView) ?: return
    windowInsetsController.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())



    val window: Window = getWindow()
    window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    window.statusBarColor = Color.TRANSPARENT
}