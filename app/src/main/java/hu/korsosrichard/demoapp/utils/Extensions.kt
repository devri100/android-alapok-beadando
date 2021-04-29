package hu.korsosrichard.demoapp.utils

import androidx.fragment.app.Fragment

fun Fragment.back() {
    if (parentFragment != null && requireParentFragment().childFragmentManager.backStackEntryCount == 0) {
        requireParentFragment().parentFragmentManager.popBackStack()
    } else {
        parentFragmentManager.popBackStack()
    }
}