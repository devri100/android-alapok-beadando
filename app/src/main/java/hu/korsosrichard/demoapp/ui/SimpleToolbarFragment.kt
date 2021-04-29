package hu.korsosrichard.demoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.korsosrichard.demoapp.MainActivity
import hu.korsosrichard.demoapp.R
import hu.korsosrichard.demoapp.databinding.FragmentSimpleToolbarBinding
import hu.korsosrichard.demoapp.utils.ShowFragmentBehaviour

class SimpleToolbarFragment : Fragment(), ShowFragmentBehaviour {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSimpleToolbarBinding.inflate(inflater)
        (requireActivity() as MainActivity).let {
            it.setSupportActionBar(binding.mainToolbar)
            it.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            binding.mainToolbar.setNavigationOnClickListener {
                (requireActivity() as MainActivity).onBackPressed()
            }
        }
        return binding.root
    }

    override fun showFragmentNow(fragment: Fragment, tag: String?) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_simple_toolbar_container, fragment, tag)
            .commitNow()
    }

    override fun showFragment(fragment: Fragment, name: String?, tag: String?) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_simple_toolbar_container, fragment, tag)
            .addToBackStack(name)
            .commit()
    }
}