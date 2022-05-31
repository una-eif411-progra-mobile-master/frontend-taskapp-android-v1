package edu.mike.frontend.taskapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import edu.mike.frontend.taskapp.databinding.ActivityLoginBinding
import edu.mike.frontend.taskapp.model.LoginRequest
import edu.mike.frontend.taskapp.misc.SessionManager
import edu.mike.frontend.taskapp.viewmodel.LoginViewModel
import edu.mike.frontend.taskapp.viewmodel.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {

    // Definition of the binding variable
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // With View Binding
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading
        sessionManager = SessionManager(this)

        // LoginViewModelFactory
        loginViewModel =
            ViewModelProvider(this, LoginViewModelFactory())
                .get(LoginViewModel::class.java)

        loginViewModel.loginResponse.observe(this){
            val loginState = it
        }

        loginViewModel.tmp.observe(this) {
            val extra = it
            sessionManager.saveAuthToken(extra)
        }

        login.setOnClickListener() {
            // If token has been saved, add it to the request
            sessionManager.fetchAuthToken()?.let {
                var test = it
            }
            loginViewModel.login(LoginRequest(username.text.toString(), password.text.toString()))

            val intent = Intent(this, TaskActivity::class.java)
            startActivity(intent)
        }

    }
}