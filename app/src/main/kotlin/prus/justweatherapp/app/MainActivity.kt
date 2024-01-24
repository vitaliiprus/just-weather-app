package prus.justweatherapp.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import prus.justweatherapp.app.navigation.RootNavigationGraph
import prus.justweatherapp.theme.AppTheme


class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            
            AppTheme {
                RootNavigationGraph(
                    navController = navController
                )
            }
        }

    }

}