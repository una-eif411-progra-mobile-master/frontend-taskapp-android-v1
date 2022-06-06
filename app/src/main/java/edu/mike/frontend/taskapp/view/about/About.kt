package edu.mike.frontend.taskapp.view.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import edu.mike.frontend.taskapp.R
import edu.mike.frontend.taskapp.databinding.FragmentAboutBinding
import edu.mike.frontend.taskapp.viewmodel.LoginViewModel
import edu.mike.frontend.taskapp.viewmodel.LoginViewModelFactory

class About : Fragment() {

    private var _binding : FragmentAboutBinding ?= null
    private val binding get() = _binding!!
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAboutBinding.inflate(inflater, container, false)

        // LoginViewModelFactory
        loginViewModel =
            ViewModelProvider(this, LoginViewModelFactory())[LoginViewModel::class.java]

        binding.btnLogout.setOnClickListener(){
            loginViewModel.logout()
            activity?.finish()
        }

        return binding.root
    }

}