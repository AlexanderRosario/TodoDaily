package com.alexander.tododaily.todotask.ui.login

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.alexander.tododaily.todotask.navigation.Routes
import com.alexander.tododaily.todotask.domain.LoginUseCase
import com.alexander.tododaily.todotask.ui.model.UiState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val auth: FirebaseAuth
) : ViewModel() {


    private val _isUserAuthenticated = MutableLiveData<Boolean>()
    val isUserAuthenticated: LiveData<Boolean> get() = _isUserAuthenticated

    init {
        checkUserSession()
    }


    private fun checkUserSession() {
        _isUserAuthenticated.value = auth.currentUser != null
    }


    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState


    private val _errorState = mutableStateOf<String?>(null)
    val errorState: MutableState<String?> = _errorState

    init {
        checkCurrentUser()
    }


    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    private val _isLoginEnable = MutableLiveData<Boolean>()


    val email: LiveData<String> = _email
    val password: LiveData<String> = _password
    val isLoginEnable: LiveData<Boolean> = _isLoginEnable

    fun onLoginChange(email: String, password: String) {
        _email.value = email
        _password.value = password
        _isLoginEnable.value = enableLogin(email, password)

    }


    fun enableLogin(email: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 6


    fun onLoginClick(navHostController: NavHostController) {
        viewModelScope.launch {
            _uiState.value = UiState.InProgress
            val result = loginUseCase(email.value.orEmpty(), password = password.value.orEmpty())
            _uiState.value = if (result.isSuccess) {
                navHostController.navigate(Routes.Home.route)
                {
                    popUpTo(Routes.Login.route) { inclusive = true }
                }
//                 homeViewModel.getTasks()
                UiState.Success

            } else {
                UiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }


    private fun checkCurrentUser() {
        viewModelScope.launch {
            val currentUser = auth.currentUser
            _isUserAuthenticated.value = currentUser != null
        }
    }

    fun signOut() {
        auth.signOut()
        _isUserAuthenticated.value = false
    }

//    fun signInCustomEmailAndPassword(
//        email: String,
//        password: String,
//        navHostController: NavHostController
//    ) {
//        authRepository.signInWithCustomEmailAndPassword(email = email, password = password)
//        { user, exception ->
//            if (user != null) {
//                navHostController.run { navigate(Routes.home.route) }
//
//            } else {
//                _errorState.value = exception?.message
////                _email.value = ""
//                _password.value = ""
//
//            }
//        }
//    }


}