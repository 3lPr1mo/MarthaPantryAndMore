package com.example.marthaspantrymore.ui.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marthaspantrymore.R
import com.example.marthaspantrymore.ui.presentation.viewModel.LoginViewModel
import com.example.marthaspantrymore.ui.presentation.viewModel.SignInState
import com.example.marthaspantrymore.ui.theme.Shapes
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: LoginViewModel, state: SignInState, onSignInClick: () -> Unit) {

    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Login(Modifier.align(Alignment.Center), viewModel, onSignInClick)
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel, onSignInClick: () -> Unit) {

    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .wrapContentHeight(Alignment.CenterVertically)
    ) {
        HeaderLogo(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(20.dp))
        EmailField(email) { viewModel.onLoginChange(it, password) }
        Spacer(modifier = Modifier.padding(5.dp))
        PasswordField(password) { viewModel.onLoginChange(email, it) }
        Spacer(modifier = Modifier.padding(8.dp))
        ForgotPassword(Modifier.align(Alignment.End))
        Spacer(modifier = Modifier.padding(20.dp))
        LoginButton(loginEnable) {
            coroutineScope.launch {
                viewModel.onLoginSelected()
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Divider(thickness = 1.dp, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.padding(8.dp))
        GoogleButton(modifier = Modifier.align(Alignment.CenterHorizontally), onSignInClick)
    }

    /*if(isLoading){
        Box(modifier = Modifier.fillMaxSize()){
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .wrapContentHeight(Alignment.CenterVertically)
        ) {
            HeaderLogo(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(20.dp))
            EmailField(email) { viewModel.onLoginChange(it, password) }
            Spacer(modifier = Modifier.padding(5.dp))
            PasswordField(password) { viewModel.onLoginChange(email, it) }
            Spacer(modifier = Modifier.padding(8.dp))
            ForgotPassword(Modifier.align(Alignment.End))
            Spacer(modifier = Modifier.padding(20.dp))
            LoginButton(loginEnable) {
                coroutineScope.launch {
                    viewModel.onLoginSelected()
                }
            }
        }

    }*/
}

@Composable
fun HeaderLogo(modifier: Modifier) {
    Image(
        modifier = modifier
            .fillMaxSize(0.6F)
            .aspectRatio(1f)
            .clip(shape = RoundedCornerShape(20.dp)),
        painter = painterResource(id = R.drawable.storeicon),
        contentDescription = "Store Icon",
    )
}

@Composable
fun EmailField(email: String, onTextFieldChanged: (String) -> Unit) {

    //var email by remember { mutableStateOf("") }

    OutlinedTextField(
        value = email,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        enabled = true,
        readOnly = false,
        //textStyle = TextStyle(color = Color(0xFF86B9FF)),
        label = { Text(text = "Email")},
        placeholder = { Text(text = "Email") },
        leadingIcon = { Icon(Icons.Rounded.Email, contentDescription = "Email") },
        isError = false,
        singleLine = true,
        maxLines = 1,
        minLines = 1,
        shape = RoundedCornerShape(25.dp),
    )
}

@Composable
fun PasswordField(password: String, onTextFieldChanged: (String) -> Unit) {
    //var password by remember { mutableStateOf("") }

    var passwordVisible = false
    OutlinedTextField(
        value = password,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        enabled = true,
        readOnly = false,
        //textStyle = TextStyle(color = Color(0xFF86B9FF)),
        label = { Text(text = "Contraseña")},
        placeholder = { Text(text = "Contraseña") },
        leadingIcon = { Icon(Icons.Rounded.Lock, contentDescription = "Password") },
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                val icon =
                    if (passwordVisible) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff
                Icon(icon, contentDescription = null)
            }
        },
        isError = false,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        singleLine = true,
        maxLines = 1,
        minLines = 1,
        shape = RoundedCornerShape(25.dp),
    )
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        text = "Olvidé la contraseña",
        modifier = modifier.clickable { },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        //color = Color(0xFF75A0F7)
    )
}

@Composable
fun LoginButton(loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(
        onClick = { onLoginSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            //containerColor = Color(0xFF75A0F7),
            //disabledContainerColor = Color(0x9C75B4F7),
            //contentColor = Color.White,
            //disabledContentColor = Color.White
        ),
        enabled = loginEnable
    ) {
        Text("Iniciar Sesión", fontSize = 20.sp)
    }
}

@Composable
fun GoogleButton(modifier: Modifier, onSignInClick: () -> Unit) {

    var clicked by remember { mutableStateOf(false) }

    Surface(
        onClick = { onSignInClick() },
        shape = Shapes.medium,
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 10.dp,
                    bottom = 10.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google_logo),
                contentDescription = "Google Button", tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Ingresa con Google")
        }
    }
}


@Composable
@Preview
fun GoogleButtonPreview() {
    //GoogleButton(Modifier.Companion.align(Alignment.CenterHorizontally))
}

@Composable
fun SingInView(
    state: SignInState,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
