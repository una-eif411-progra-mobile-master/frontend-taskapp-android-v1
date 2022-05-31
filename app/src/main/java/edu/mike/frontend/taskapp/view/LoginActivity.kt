package edu.mike.frontend.taskapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.mike.frontend.taskapp.databinding.ActivityLoginBinding
import edu.mike.frontend.taskapp.repository.LoginRepository
import edu.mike.frontend.taskapp.service.LoginService
import edu.mike.frontend.taskapp.viewmodel.LoginViewModel
import edu.mike.frontend.taskapp.viewmodel.TaskViewModelFactory

class LoginActivity : AppCompatActivity() {

    // Definition of the binding variable
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // With View Binding
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading

        // Retrofit Service
        val loginService = LoginService.getInstance()
        val loginRepository = LoginRepository(loginService)
    }
}