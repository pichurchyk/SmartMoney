package com.example.smartmoney.common.base

import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.afollestad.materialdialogs.MaterialDialog
import com.example.smartmoney.R
import com.example.smartmoney.common.util.dialogBuilder
import com.example.smartmoney.common.util.hideKeyboard
import com.example.smartmoney.common.util.snackbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {
    abstract val viewModel: ViewModel

    fun snackBar(view: View, text: String) {
        requireContext().snackbar(view, text)
    }

    fun dialog(@StringRes title: Int, @StringRes text: Int) {
        dialogBuilder(title)
            .message(text)
            .show()
    }

    fun dialogBuilder(title: Int): MaterialDialog {
        return requireContext().dialogBuilder(viewLifecycleOwner, title)
    }

    fun navigate(actionId: Int) {
        Navigation.findNavController(requireView()).navigate(actionId)
    }

    fun clearFocus() {
        activity?.hideKeyboard(requireView())
        requireView().clearFocus()
    }

    fun menuVisibility(isVisible: Boolean) {
        if (isVisible) {
            requireActivity().findViewById<AppCompatImageButton>(R.id.btnHideMenu).visibility =
                View.VISIBLE
            requireActivity().findViewById<NavigationView>(R.id.menu).visibility =
                View.VISIBLE
        } else {
            requireActivity().findViewById<AppCompatImageButton>(R.id.btnHideMenu).visibility =
                View.GONE
            requireActivity().findViewById<NavigationView>(R.id.menu).visibility =
                View.GONE
        }
    }

    fun slideMenu(slideUp: Boolean) {
        val direction = if (slideUp) -100F else 100F

        requireActivity().findViewById<BottomNavigationView>(R.id.menu).animate()
            .translationY(direction).duration = 200
    }
}