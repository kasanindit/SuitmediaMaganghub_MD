package com.example.suitmediamaganghub_md.ui.navigation

import android.net.Uri
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.suitmediamaganghub_md.ui.pages.Homepage
import com.example.suitmediamaganghub_md.ui.pages.ListUserPage.ListUserPage
import com.example.suitmediamaganghub_md.ui.pages.LoginPage

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login_page",
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left
            )
        },

        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left
            )
        },

        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right
            )
        },

        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right
            )
        }
    ){
        composable("login_page"){
            LoginPage(onNextClick = { name -> navController.navigate("home_page/${Uri.encode(name)}")})
        }

        composable("home_page/{name}") { backStackEntry ->

            val name = backStackEntry.arguments
                ?.getString("name")
                .orEmpty()

            val selectedName by backStackEntry
                .savedStateHandle
                .getStateFlow(
                    "selectedUsername",
                    "Selected User Name"
                )
                .collectAsState()

            Homepage(
                name = name,

                onBackClick = {
                    navController.popBackStack()
                },

                onChooseUser = {
                    navController.navigate("list_users")
                },

                selectedName = selectedName
            )
        }

        composable("list_users"){
            ListUserPage(onUserSelected = { user ->
                val selectedName = "${user.firstName} ${user.lastName}"

                navController.previousBackStackEntry?.savedStateHandle?.set("selectedUsername", selectedName)

                navController.popBackStack()
            },

                onBackClick = {
                    navController.popBackStack()
                })
        }
    }
}