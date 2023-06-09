package com.example.newsapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.com.example.newsapp.ArticlePage
import com.example.newsapp.com.example.newsapp.MainPage
import com.example.newsapp.com.example.newsapp.view_models.DataLoaderViewModel
import com.example.newsapp.com.example.newsapp.view_models.Result
import com.example.newsapp.modules.Article


sealed class Destination(val route: String) {
    object MainPage: Destination("mainPage")
    object ArticlePage: Destination("articlePage/{articleId}") {
        fun createRoute(articleIndex: Int) : String {
            println("articlePage/$articleIndex")
            return  "articlePage/$articleIndex";
        }
    }
}
class MainActivity : ComponentActivity() {

    val dataLoaderViewModel by viewModels<DataLoaderViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //dataLoaderViewModel.getArticles(dataLoaderViewModel.searchCategory.value)

        setContent {

            val navController = rememberNavController();
            NavigationAppHost(navController = navController)

        }

    }
}

@Composable
fun NavigationAppHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = "mainPage") {
        composable(Destination.MainPage.route) { MainPage(navController)
            println("NAVIGATION APP HOST ROUTE ")
        }
        composable(Destination.ArticlePage.route) { navBackStackEntry ->
            val articleId = navBackStackEntry.arguments?.get("articleId")
            if(articleId == null) {
                Toast.makeText(LocalContext.current, "Element is required", Toast.LENGTH_SHORT).show()
            }
            else {
                ArticlePage(articleId.toString().toInt())
            }

        }
    }


}



