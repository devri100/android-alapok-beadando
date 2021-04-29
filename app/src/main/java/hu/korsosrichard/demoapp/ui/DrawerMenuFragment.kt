package hu.korsosrichard.demoapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import hu.korsosrichard.demoapp.MainActivity
import hu.korsosrichard.demoapp.R
import hu.korsosrichard.demoapp.databinding.FragmentDrawerMenuBinding
import hu.korsosrichard.demoapp.ui.author.AuthorListFragment
import hu.korsosrichard.demoapp.ui.book.BookListFragment
import hu.korsosrichard.demoapp.utils.ShowFragmentBehaviour

class DrawerMenuFragment : Fragment(), ShowFragmentBehaviour {

    lateinit var toggle: ActionBarDrawerToggle

    private lateinit var binding: FragmentDrawerMenuBinding

    private lateinit var backPressedCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_drawer_menu_container, BookListFragment())
                .commit()
        }

        backPressedCallback = object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDrawerMenuBinding.inflate(inflater)

        (requireActivity() as MainActivity).let {
            it.setSupportActionBar(binding.mainToolbar)
            it.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        toggle = MyActionBarDrawerToggle()
        toggle.syncState()
        binding.drawerLayout.addDrawerListener(toggle)
        binding.navView.setNavigationItemSelectedListener {
            /*if(bottom_navigation.menu.findItem(it.itemId) != null){
                bottom_navigation.selectedItemId = it.itemId
                bottom_navigation.visibility = View.VISIBLE
            } else {
                bottom_navigation.visibility = View.GONE
            }*/
            selectMenuItem(it)
        }

        return binding.root
    }

    inner class MyActionBarDrawerToggle : ActionBarDrawerToggle(
        requireActivity(),
        binding.drawerLayout,
        binding.mainToolbar,
        R.string.empty,
        R.string.empty
    ) {
        override fun onDrawerOpened(drawerView: View) {
            super.onDrawerOpened(drawerView)
            backPressedCallback.isEnabled = true
        }

        override fun onDrawerClosed(drawerView: View) {
            super.onDrawerClosed(drawerView)
            backPressedCallback.isEnabled = false
        }
    }

    private fun selectMenuItem(menuItem: MenuItem): Boolean {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        when (menuItem.itemId) {
            R.id.menu_books -> showFragmentNow(BookListFragment())
            R.id.menu_authors -> showFragmentNow(AuthorListFragment())
        }

        return true
    }

    override fun showFragmentNow(fragment: Fragment, tag: String?) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_drawer_menu_container, fragment, tag)
            .commitNow()
    }

    override fun showFragment(fragment: Fragment, name: String?, tag: String?) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_drawer_menu_container, fragment, tag)
            .addToBackStack(name)
            .commit()
    }

    companion object {
        val TAG = "DrawerMenuFragment"
    }
}