package com.alexander.tododaily.todotask.navigation


sealed class Routes(val route:String)

{
    object Login: Routes("login")
    object Home: Routes("home")
    object Signup: Routes("signup")
    object Pantalla4: Routes("Pantalla4/{age}"){

        fun createRoute(age:Int) = "Pantalla4/$age"
    }
    object  Pantalla5: Routes("Pantalla5?name={myName}"){
        fun createRoute(myName:String) = "Pantalla5?name=$myName"
    }
}