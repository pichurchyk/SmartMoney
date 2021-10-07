package com.example.smartmoney.ui.auth.signIn

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.smartmoney.R
import com.example.smartmoney.common.base.BaseFragment
import com.example.smartmoney.common.util.bottomErrorMessage
import com.example.smartmoney.common.util.removeBottomErrorMessage
import com.example.smartmoney.databinding.FragmentSignInBinding
import com.example.smartmoney.ui.auth.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {
    override val viewModel by viewModels<AuthViewModel>()
    private val binding by viewBinding(FragmentSignInBinding::bind)

    private var emailValidator: Job? = null
    private var passwordValidator: Job? = null

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOpenSignUp.setOnClickListener {
            navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.root.setOnClickListener {
            clearFocus()
        }

        binding.submitBtn.setOnClickListener {
            if (emailValidator == null) {
                viewModel.isEmailValid(binding.email.text.toString())
                viewModel.isPasswordValid(binding.password.text.toString())
                setTextWatchers()
                addObservers()
            }

            if (viewModel.isReadyToSubmit()) {
                setupFirebaseAuth()
            }
        }
    }

    private fun setTextWatchers() {
        binding.email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.isEmailValid(s.toString())
            }
        })

        binding.password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.isPasswordValid(s.toString())
            }
        })
    }


    private fun addObservers() {
        emailValidator = lifecycleScope.launchWhenCreated {
            viewModel.isEmailValid
                .collect {
                    when (it) {
                        false -> binding.emailWrapper.bottomErrorMessage(
                            binding.emailWrapper,
                            "Invalid email"
                        )
                        true -> {
                            binding.emailWrapper.removeBottomErrorMessage(binding.emailWrapper)
                        }
                    }
                }
        }

        passwordValidator = lifecycleScope.launchWhenCreated {
            viewModel.isPasswordValid
                .collect {
                    when (it) {
                        false -> binding.passwordWrapper.bottomErrorMessage(
                            binding.passwordWrapper,
                            "Invalid password"
                        )
                        true -> {
                            binding.passwordWrapper.removeBottomErrorMessage(binding.passwordWrapper)
                        }
                    }
                }
        }
    }

    private fun setupFirebaseAuth() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                viewModel.setRememberUser(binding.remember.isChecked)
                navigate(R.id.action_signInFragment_to_historyFragment)

            } else {
                snackBar(requireView(), "Login Failed! Check your data and try again")
                binding.password.setText("")
            }
        }
    }
}