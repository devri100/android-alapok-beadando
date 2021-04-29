package hu.korsosrichard.demoapp.utils

import androidx.fragment.app.Fragment

interface ShowFragmentBehaviour {

    fun showFragmentNow(fragment: Fragment, tag: String? = null)
    fun showFragment(fragment: Fragment, name: String? = null, tag: String? = null)
//    fun showFragmentNowNoBackStack(fragment: Fragment, name: String? = null, tag: String? = null)
//    fun showFragmentNoBackStack(fragment: Fragment, name: String? = null, tag: String? = null)

}