package com.sobolev.beerratings.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.sobolev.beerratings.domain.model.Rating
import com.sobolev.beerratings.ui.RegionScreen
import com.sobolev.beerratings.ui.StartScreen
import com.sobolev.beerratings.ui.details.DetailScreen
import com.sobolev.beerratings.ui.theme.MainTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    private val store by lazy {
        Firebase.firestore
    }

    private val ratingsQuery by lazy {
        store.collection("ratings")
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: ?Loading start screen
        setContent {
            MainTheme {
                Scaffold {
                    Navigation(viewModel)
                }
            }
        }

        ratingsQuery.get()
            .addOnSuccessListener {
                val test = it.toObjects<Rating>()
                Log.d("DATAAA", "onCreate: $test")
                viewModel.postRatings(test)
            }.addOnFailureListener {
                Log.d("DATAAA", "onFailure: $it")
            }
    }
}

@ExperimentalAnimationApi
@Composable
fun Navigation(viewModel: MainViewModel) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = "main",
    ) {
        composable(
            "main",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Down,
                    animationSpec = tween(700)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(700)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Down,
                    animationSpec = tween(700)
                )
            }
        ) {
            MainScreen(navController = navController, viewModel)
        }
        composable(
            "start",
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Down,
                    animationSpec = tween(700)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(700)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Down,
                    animationSpec = tween(700)
                )
            }
        ) {
            val isRegionChosen = viewModel.chosenRegion.collectAsState(initial = "").value != ""
            val route = if (isRegionChosen) "main" else "region"

            StartScreen(
                onLogin = { username ->

                    navController.navigate(route) {
                        popUpTo("start") {
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onSignUp = {

                },
                onSkipped = {
                    navController.navigate(route) {
                        popUpTo("start") {
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }
        composable("region") {
            RegionScreen(
                onItemClicked = { chosenRegion ->
                    viewModel.changeRegion(chosenRegion.region)

                    navController.navigate("main") {
                        popUpTo("start") {
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }
        composable(
            "details/{ratingName}",
            arguments = listOf(navArgument("ratingName") { type = NavType.StringType })
        ) { backStackEntry ->

            backStackEntry.arguments?.getString("ratingName")?.let { ratingTitle ->

                viewModel.rating.firstOrNull { it.title == ratingTitle }?.let {
                    DetailScreen(
                        rating = it
                    )
                }
            }
        }
    }
}