package com.example.smartmoney.common.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.afollestad.materialdialogs.MaterialDialog
import com.example.smartmoney.R
import com.example.smartmoney.common.util.dialogBuilder
import com.example.smartmoney.common.util.hideKeyboard
import com.example.smartmoney.common.util.snackbar
import com.google.android.material.bottomnavigation.BottomNavigationView

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
            requireActivity().findViewById<BottomNavigationView>(R.id.menu).visibility =
                View.VISIBLE
        } else {
            requireActivity().findViewById<BottomNavigationView>(R.id.menu).visibility =
                View.GONE
        }
    }

    fun addMenuListener() {
        val root = requireActivity().findViewById<ConstraintLayout>(R.id.mainActivityWrapper)

        var isMenuVisible = false
        root.viewTreeObserver.addOnGlobalLayoutListener {
            val height = root.rootView.height - root.height
            if (height > 200) {
                isMenuVisible = true
                menuVisibility(false)
            } else {
                if (isMenuVisible) {
                    requireView().clearFocus()
                    isMenuVisible = false
                }

                menuVisibility(true)
            }
        }
    }
}