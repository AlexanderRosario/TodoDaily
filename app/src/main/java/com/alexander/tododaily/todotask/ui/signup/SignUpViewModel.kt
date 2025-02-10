package com.alexander.tododaily.todotask.ui.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.alexander.tododaily.todotask.navigation.Routes
import com.alexander.tododaily.todotask.domain.CreateUserUseCase
import com.alexander.tododaily.todotask.ui.model.RegistrationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
//    private val loginUseCase: LoginUseCase,
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val _registrationState = MutableLiveData<RegistrationState>()
    val registrationState: LiveData<RegistrationState> get() = _registrationState

    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    private val _isLoginEnable = MutableLiveData<Boolean>()



    val email: LiveData<String> = _email
    val password: LiveData<String> = _password
    val isLoginEnable: LiveData<Boolean> = _isLoginEnable



    fun onSignUPChange(email: String, password: String) {
        _email.value = email
        _password.value = password
        _isLoginEnable.value = enableLogin(email, password)

    }
    fun enableLogin(email: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 6


    fun onRegisterClicked(navHostController:NavHostController) {

        viewModelScope.launch {
            _registrationState.value = RegistrationState.Loading
            val result = createUserUseCase(
                email = email.value.orEmpty(),
                password = password.value.orEmpty()
            )
            if (result.isSuccess) {
                navHostController.navigate(Routes.home.route)
                _registrationState.value = RegistrationState.Success
            } else {
                _registrationState.value =
                    RegistrationState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }


    }
}