package com.example.marthaspantrymore.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marthaspantrymore.ui.data.GoogleAuthUiClient
import com.example.marthaspantrymore.ui.presentation.navigation.AppNavigation
import com.example.marthaspantrymore.ui.presentation.screens.LoginScreen
import com.example.marthaspantrymore.ui.presentation.viewModel.LoginViewModel
import com.example.marthaspantrymore.ui.theme.AppTheme
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            onTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "sign_in"){
                        composable("sign_in"){
                            val viewModel = viewModel<LoginViewModel>()
                            val state by viewModel.state.collectAsState()

                            LaunchedEffect(key1 = Unit){
                                if(googleAuthUiClient.getSignInUser() != null){
                                    navController.navigate("homeView")
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if(result.resultCode == RESULT_OK){
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )
                            
                            LaunchedEffect(key1 = state.isSignInSuccesful){
                                if(state.isSignInSuccesful) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Ingreso exitoso",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    navController.navigate("homeView")
                                    viewModel.resetState()
                                }
                            }
                            
                            LoginScreen(
                                LoginViewModel(),
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                }
                            )
                        }
                        composable("homeView"){
                            AppNavigation(applicationContext)
                        }
                    }
                }
            }
        }
    }
}