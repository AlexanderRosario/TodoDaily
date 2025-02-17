package com.alexander.tododaily.todotask.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
//import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.alexander.tododaily.R
import com.alexander.tododaily.todotask.navigation.Routes


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    navHostController: NavHostController
) {
    val isUserAuthenticated by loginViewModel.isUserAuthenticated.observeAsState(false)

    LaunchedEffect(isUserAuthenticated) {
        if (isUserAuthenticated) {
            navHostController.navigate(Routes.Home.route) {
                popUpTo(Routes.Login.route) { inclusive = true }
            }
        }
    }

    Scaffold(Modifier.background(Color(249, 245, 244)
        )
    ) { PaddingValues ->



//        when(loginViewModel.uiState){
////            is SignedOut ->  // Display signed out UI components
////            is InProgress ->
////            is Error ->      // Display error toast
//
//            // Using the SignIn state as a trigger to navigate
////            is SignIn ->     navController.navigate(...)
//        }
//
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(PaddingValues)
                .background(Color(249, 245, 244))
//                .background(Color(243, 239, 238))
//                .verticalScroll(rememberScrollState())
        ) {
            HeadSigin()
            Spacer(Modifier.padding(30.dp))

            BodySigin(loginViewModel, navHostController)
            Spacer(Modifier.padding(15.dp))
            FooterLogin(navHostController)


        }

    }
}



@Composable
fun BodySigin(loginViewModel: LoginViewModel, navHostController: NavHostController) {
    val email: String by loginViewModel.email.observeAsState(initial = "")
    val password: String by loginViewModel.password.observeAsState(initial = "")
    val isloginEnable: Boolean by loginViewModel.isLoginEnable.observeAsState(initial = false)

    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {

        Email(email = email) {
            loginViewModel.onLoginChange(email = it, password = password)
        }

        Spacer(Modifier.padding(10.dp))

        Password(password) {
            loginViewModel.onLoginChange(email = email, password = it)
        }

        ForgotPassword(Modifier.align(Alignment.End))

        Spacer(Modifier.padding(6.dp))

        ButtonConfirmation(
            modifier = Modifier.fillMaxWidth(),
            text = "Sign In",
            onActionButton = {loginViewModel.onLoginClick(navHostController)
            },
            isEnable = isloginEnable
        )

        Spacer(Modifier.padding(16.dp))

        Divider()

        Spacer(Modifier.padding(16.dp))

        SocialMediaSigin()
    }

}


@Composable
fun Divider() {
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            Modifier
                .weight(1f)
                .padding(start = 35.dp, end = 10.dp)
        )
        Text(text = "Or sign in with")
        HorizontalDivider(
            Modifier
                .weight(1f)
                .padding(start = 10.dp, end = 35.dp)
        )
    }
}

//@Composable
//fun ForgotPassword(modifier: Modifier) {
//    TextButton(onClick = {}, modifier = modifier) {
//        Text(text = "Forgot Password?", textDecoration = TextDecoration.Underline)
//    }
//}


@Composable
fun HeadSigin() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Sign In",
            fontSize = 22.sp,
//            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(12.dp)
        )
        Text(text = "Hi! Welcome back, you've been missed", fontSize = 12.sp)
    }

}




@Composable
fun Email(email: String, onChanceValue: (String) -> Unit) {


    Text(text = "Email")
    TextField(
//            isError = true,
        value = email,
        onValueChange = { onChanceValue(it) },
        shape = RoundedCornerShape(10.dp),
        placeholder = { Text(text = "example@gmail.com") },

        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(243, 239, 238),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding()
            .border(BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(10.dp))
    )
}

@Composable
fun Password(password: String, onChangeValue: (String) -> Unit) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }


    Text(text = "Passworsd")
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding()

            .border(BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(10.dp)),
//            isError = true,
        value = password,
        onValueChange = { onChangeValue(it) },
        shape = RoundedCornerShape(10.dp),
        placeholder = { Text(text = "**************") },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(243, 239, 238),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
//                disabledIndicatorColor = Color.Transparent


        ),
        trailingIcon = {
            val imagen = if (passwordVisibility) {
                Icons.Filled.VisibilityOff

            } else {
                Icons.Filled.Visibility
            }
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = imagen, contentDescription = "show password")
            }
        },
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )

}

@Composable
fun ForgotPassword(modifier: Modifier) {
    TextButton(onClick = {}, modifier = modifier) {
        Text(text = "Forgot Password?", textDecoration = TextDecoration.Underline)
    }
}

@Composable
fun SocialMediaSigin() {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center

    ) {
        Image(
            painter = painterResource(id = R.drawable.icons8google_192),
            contentDescription = "",
            modifier = Modifier
                .border(BorderStroke(1.dp, Color.LightGray), shape = CircleShape)
                .padding(16.dp)
                .size(32.dp)

//                .weight(1f)

        )
        Spacer(
            Modifier.padding(
                6.dp
            )
        )

        Image(
            painter = painterResource(id = R.drawable.facebook_logo_icon),
            contentDescription = "",
            modifier = Modifier
                .border(BorderStroke(1.dp, Color.LightGray), shape = CircleShape)
                .padding(16.dp)
                .size(32.dp)
        )

    }
}



@Composable
fun ButtonConfirmation(
    text: String,
    onActionButton: () -> Unit,
    modifier: Modifier,
    isEnable: Boolean
) {

    Button(
        onClick = { onActionButton() },
        modifier = modifier   ,
        shape = RoundedCornerShape(10.dp),
        enabled = isEnable
    ) {
        Text(text = text)
    }
}




@Composable
fun FooterLogin(navHostController:NavHostController) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {

        Text(text = "Don't have an account? ")

        Text(text = "Sign up",
            fontWeight = FontWeight.SemiBold,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable { navHostController.navigate(Routes.Signup.route )})

    }

}

