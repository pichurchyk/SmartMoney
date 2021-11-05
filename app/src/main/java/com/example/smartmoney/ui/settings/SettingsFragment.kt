package com.example.smartmoney.ui.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.smartmoney.R
import com.example.smartmoney.common.base.BaseFragment
import com.example.smartmoney.common.util.bottomErrorMessage
import com.example.smartmoney.common.util.removeBottomErrorMessage
import com.example.smartmoney.databinding.ChangeProfileInfoDialogBinding
import com.example.smartmoney.databinding.FragmentSettingsBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
   override val viewModel by viewModels<SettingsViewModel>()
   private val binding by viewBinding(FragmentSettingsBinding::bind)

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      addEventListeners()

      binding.userEmail.text = viewModel.email!!

      loadAds()
   }

   private fun addEventListeners() {
//      addMenuListener()

      binding.root.setOnClickListener {
         clearFocus()
      }

      binding.userPassword.findViewById<TextView>(R.id.edit).setOnClickListener {
         changePassword()
      }

      binding.logOutBtn.setOnClickListener {
         Navigation.findNavController(requireView()).navigate(R.id.action_settingsFragment_to_signInFragment)
         viewModel.logOut()
         viewModel.removeRememberUser()
      }
   }

   @SuppressLint("ResourceType")
   private fun changePassword() {
      val rootView = binding.root as ViewGroup
      val view = LayoutInflater.from(requireContext()).inflate(R.layout.change_profile_info_dialog, rootView, false)
      val viewToAttach = binding.userPassword.findViewById<LinearLayout>(R.id.sectionToChange)

      val viewsToRestore = mutableListOf<View>()
      for (i in 0 until viewToAttach.childCount) {
         viewsToRestore.add(viewToAttach.getChildAt(i))
      }

      when (viewToAttach.childCount) {
         2 -> {
            viewToAttach.removeAllViews()
            viewToAttach.addView(view)
         }
      }

      val binding = ChangeProfileInfoDialogBinding.bind(view)
      binding.firstPassword.addTextChangedListener(object : TextWatcher{
         override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

         override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

         override fun afterTextChanged(s: Editable?) {
            viewModel.newPassword = s.toString()
            viewModel.isNewPasswordValid(s.toString())
         }
      })

      binding.cancelBtn.setOnClickListener {
         viewToAttach.removeAllViews()
         viewsToRestore.forEach {
            viewToAttach.addView(it)
         }
      }

      binding.submitBtn.setOnClickListener {
         if (viewModel.arePasswordsTheSame(binding.secondPassword.text.toString()) && !binding.firstPassword.text.isNullOrEmpty()) {
            Log.d("Success", "Password changed!")
            viewModel.updatePassword(viewModel.newPassword)
            snackBar(requireView(), "Password changed successfully. You need to re-login with new password")
            SettingsFragmentDirections.actionSettingsFragmentToSignInFragment()
         }
         else {
            addObserversToView(binding)
         }
      }
   }

   private fun addObserversToView(viewBinding: ChangeProfileInfoDialogBinding) {
      lifecycleScope.launch {
         viewModel.isNewPasswordValid.collect {
            when (it) {
               true -> viewBinding.firstPasswordWrapper.removeBottomErrorMessage(viewBinding.firstPasswordWrapper)
               false -> viewBinding.firstPasswordWrapper.bottomErrorMessage(viewBinding.firstPasswordWrapper, "Invalid password")
            }
         }
      }
   }

   private fun loadAds() {
      MobileAds.initialize(binding.root.context)
      val mAdView = binding.adView
      val adRequest = AdRequest.Builder().build()
      mAdView.loadAd(adRequest)
   }
}