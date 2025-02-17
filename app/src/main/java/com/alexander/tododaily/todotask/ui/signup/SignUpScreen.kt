package com.alexander.tododaily.todotask.ui.signup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.alexander.tododaily.R
import com.alexander.tododaily.todotask.navigation.Routes

@Composable
fun SignUpScreen(signUpViewModel: SignUpViewModel, navHostController: NavHostController) {


    Scaffold { PaddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(PaddingValues)
                .background(Color(249, 245, 244))
                .verticalScroll(rememberScrollState())
        ) {
            HeadLogin()
            Spacer(Modifier.padding(15.dp))

            BodyLogin(signUpViewModel,navHostController)
            Spacer(Modifier.padding(15.dp))




            FooterLogin(navHostController)


        }

    }

}


@Composable
fun FooterLogin(navHostController: NavHostController) {

//    Spacer(Modifier.padding(16.dp))


    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {

        Text(text = "Already")
        Spacer(Modifier.padding(2.dp))

        Text(text = "have an account?", fontSize = 13.sp)
        Spacer(Modifier.padding(2.dp))

        Text(text = "Log In",
            fontWeight = FontWeight.SemiBold,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {navHostController.navigate(Routes.Login.route) })

    }

}

@Composable
fun BodyLogin(signUpViewModel: SignUpViewModel, navHostController: NavHostController) {

    val email: String by signUpViewModel.email.observeAsState(initial = "")
    val password: String by signUpViewModel.password.observeAsState(initial = "")
    val isSignUpEnable: Boolean by signUpViewModel.isLoginEnable.observeAsState(initial = false)


    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {



//        Name(){}

        Spacer(Modifier.padding(10.dp))

        Email(email) {
            signUpViewModel.onSignUPChange(email = it, password = password)
        }

        Spacer(Modifier.padding(10.dp))

        Password(password) {
            signUpViewModel.onSignUPChange(email = email, password = it)
        }

//        TermsCondition(Modifier)

        Spacer(Modifier.padding(6.dp))

        ButtonConfirmation(
            modifier = Modifier.fillMaxWidth(),
            text = "Sign Up",
            onActionButton = { signUpViewModel.onRegisterClicked(navHostController) }, isEnable = isSignUpEnable)

        Spacer(Modifier.padding(16.dp))

        Divider()

        Spacer(Modifier.padding(16.dp))

        SocialMediaSigin()
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
        Text(text = "Or sign up with")
        HorizontalDivider(
            Modifier
                .weight(1f)
                .padding(start = 10.dp, end = 35.dp)
        )
    }
}

@Composable
fun TermsCondition(modifier: Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = false, onCheckedChange = {})
        Text(text = "Agree with")

        TextButton(
            onClick = {},
            modifier = modifier,
            contentPadding = PaddingValues(start = 6.dp)
        ) {
            Text(text = "Terms & Condition", textDecoration = TextDecoration.Underline)
        }
    }
}





@Composable
fun HeadLogin() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 75.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Create Account",
            fontSize = 26.sp,
//            fontWeight = FontWeight.,
            modifier = Modifier.padding(12.dp)
        )
        Text(text = "Fill your information below or register", fontSize = 12.sp)
        Text(text = "with your social account", fontSize = 12.sp)

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
//                disabledIndicatorColor = Color.Transparent

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


