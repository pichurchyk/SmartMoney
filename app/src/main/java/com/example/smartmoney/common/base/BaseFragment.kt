package com.example.smartmoney.common.base

import android.annotation.SuppressLint
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.afollestad.materialdialogs.MaterialDialog
import com.example.smartmoney.common.util.dialogBuilder
import com.example.smartmoney.common.util.hideKeyboard
import com.example.smartmoney.common.util.snackbar

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
}