package com.example.suitmediamaganghub_md.ui.pages.ListUserPage

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.suitmediamaganghub_md.data.model.DataItem
import com.example.suitmediamaganghub_md.ui.common.UserItem
import com.example.suitmediamaganghub_md.BuildConfig
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListUserPage(
    viewModel: ListUserPageViewModel = viewModel(), onUserSelected: (DataItem) -> Unit,
    onBackClick: () -> Unit
) {
    val users by viewModel.users.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isLoadMore by viewModel.isLoadingMore.collectAsState()
    val isRefresh by viewModel.isRefresh.collectAsState()
    val isError by viewModel.isError.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.getUsers(
            apikey = BuildConfig.REQRES_API_KEY
        )
    }

    LaunchedEffect(listState) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo

            val lastVisibleIndex =
                layoutInfo.visibleItemsInfo
                    .lastOrNull()
                    ?.index
                    ?: 0

            val totalItems =
                layoutInfo.totalItemsCount

            totalItems > 0 &&
                    lastVisibleIndex >= totalItems - 2
        }
            .distinctUntilChanged()
            .filter { it }
            .collect {
                viewModel.loadMoreUsers()
            }
    }

    BackHandler {
        onBackClick()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = Color.White
                ),
                title = {
                    Text(
                        text = "List Users Page",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = isRefresh,
            onRefresh = {
                viewModel.refreshUsers()
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            when {

                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                isError && users.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = errorMessage
                                ?: "Something went wrong"
                        )
                    }
                }

                users.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No users found"
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize()
                    ) {

                        items(
                            items = users,
                            key = { user ->
                                user.id ?: user.hashCode()
                            }
                        ) { user ->

                            UserItem(
                                user = user,
                                onClick = {
                                    onUserSelected(user)
                                }
                            )
                        }

                        if (isLoadMore) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }
        }

//        when {
//            isLoading -> {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(padding),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator()
//                }
//            }
//
//            isError -> {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(padding),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        errorMessage ?: "Something went wrong"
//                    )
//                }
//            }
//
//            else -> {
//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(padding)
//                ) {
//                    items(users) { user ->
//                        UserItem(
//                            user = user,
//                            onClick = {
//                                onUserSelected(user)
//                            }
//                        )
//                    }
//                }
//            }
//        }
    }
}

