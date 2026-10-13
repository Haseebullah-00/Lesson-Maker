//package com.lessonmaker.app.authFLow
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.imePadding
//import androidx.compose.foundation.layout.navigationBarsPadding
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import com.lessonmaker.app.common.MainButton
//import com.lessonmaker.app.common.MainGradientBg
//import com.lessonmaker.app.common.SimpleInputFieldWithError
//import com.lessonmaker.app.common.ageAskingField
//import com.lessonmaker.app.navigationControler.Screen
//import com.lessonmaker.app.theme.PrimaryColor
//import com.lessonmaker.app.theme.Regular
//import com.lessonmaker.app.theme.TextSize18
//import com.lessonmaker.app.theme.backClr
//import com.lessonmaker.app.theme.buttonBackClr
//import org.koin.compose.viewmodel.koinViewModel
//
//@Composable
//fun AgeAskingScreen(newRouts: (String) -> Unit) {
//    var age by remember { mutableStateOf("") }
//
//    val booksVM= koinViewModel<AllBooksVM>()
//    LaunchedEffect(Unit){
//        booksVM.getAllBooks()
//    }
//    val books= booksVM.books?:emptyList()
//
//    ///
//    ///
//    ///
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .imePadding(),
//        contentAlignment = Alignment.Center
//    ) {
//        MainGradientBg()
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(12.dp),
//            verticalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//           books.foreach{ book ->
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(
//                            Color.White,
//                            RoundedCornerShape(12.dp)
//                        )
//                        .padding(16.dp)
//                ) {
//                    Text(text = book.title, color = Color.Black)
//                    Text(text = "Author: ${book.author}", color = Color.Black)
//                    Text(text = "Category: ${book.category}", color = Color.Black)
//                    Text(text = "Price: ${book.price}", color = Color.Black)
//                    Text(text = "Publisher: ${book.publisher}", color = Color.Black)
//                    Text(text = "Year: ${book.publishedYear}", color = Color.Black)
//                }
//            }
//        }
////        Column(
////            modifier = Modifier
////                .fillMaxWidth()
////                .padding(horizontal = 12.dp),
////            horizontalAlignment = Alignment.CenterHorizontally
////        ) {
////            Text(
////                text = "How old are you?",
////                color = Color.White,
////                style = TextSize18().Regular()
////            )
////            Spacer(modifier = Modifier.height(20.dp))
////            ageAskingField(age, "Enter your age", onValueChange = { age = it })
////
////            Spacer(modifier = Modifier.height(20.dp))
////            MainButton(
////                buttonBackClr,
////                Color.Transparent,
////                "Continue",
////                60.dp,
////                0.dp,
////                onClick = {
////                    newRouts.invoke(Screen.RegisterFLowScreen.route)
////                }
////            )
////        }
//    }
//}



package com.lessonmaker.app.authFLow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.common.MainGradientBg
import kotlinx.coroutines.flow.forEach
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AgeAskingScreen(newRouts: (String) -> Unit) {

    val booksVM = koinViewModel<AllBooksVM>()

    LaunchedEffect(Unit) {
        booksVM.getAllBooks()
    }

    val books by booksVM.books.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        MainGradientBg()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(books) { book ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(16.dp)
                ) {
                    Text(book.title, color = Color.Black)
                    Text("Author: ${book.author}", color = Color.Black)
                    Text("Category: ${book.category}", color = Color.Black)
                    Text("Price: ${book.price}", color = Color.Black)
                    Text("Publisher: ${book.publisher}", color = Color.Black)
                    Text("Year: ${book.publishedYear}", color = Color.Black)
                }
            }
        }
    }
}