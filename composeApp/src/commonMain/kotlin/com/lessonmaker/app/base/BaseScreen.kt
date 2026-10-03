package com.lessonmaker.app.base


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun <T : ShareViewModelInterface> BaseScreen(viewModel: SharedBaseViewModel<T>, content: @Composable () -> Unit) {
    content()
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T : ShareViewModelInterface>  BaseScreenWithRefresh(viewModel: SharedBaseViewModel<T>, refreshCall:()->Unit, content: @Composable () -> Unit) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.isRefreshing,
        onRefresh = {
            viewModel.isRefreshing = true
            refreshCall.invoke()
        }
    )
    Box(modifier = Modifier.fillMaxSize().pullRefresh(pullRefreshState)) {
        content()
        PullRefreshIndicator(
            refreshing = viewModel.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T : ShareViewModelInterface> BaseListWithRefresh(viewModel: SharedBaseViewModel<T>, refreshCall:()->Unit, emptyView: @Composable () -> Unit, content: @Composable () -> Unit) {

    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.isRefreshing,
        onRefresh = {
            viewModel.isRefreshing = true
            refreshCall.invoke()
        }
    )
    Box(modifier = Modifier.fillMaxSize().pullRefresh(pullRefreshState)) {
        content()
        PullRefreshIndicator(
            refreshing = viewModel.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        emptyView()
    }
}