package hu.korsosrichard.demoapp

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import hu.korsosrichard.demoapp.ui.DrawerMenuFragment
import hu.korsosrichard.demoapp.ui.SimpleToolbarFragment
import hu.korsosrichard.demoapp.utils.ShowFragmentBehaviour
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector, ShowFragmentBehaviour {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val fragment = DrawerMenuFragment()
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.main_activity_container,
                    fragment,
                    DrawerMenuFragment.TAG
                )
                .setPrimaryNavigationFragment(fragment)
                .commit()
        }
    }

    /*val drawerMenuFragment: DrawerMenuFragment get() {
        return supportFragmentManager.findFragmentByTag(DrawerMenuFragment.TAG) as DrawerMenuFragment
    }*/

    override fun onBackPressed() {

        if(onBackPressedDispatcher.hasEnabledCallbacks() || supportFragmentManager.backStackEntryCount > 0){
            super.onBackPressed()
        } else {
            AlertDialog.Builder(this)
                .setMessage("Do you want to leave the application?")
                .setPositiveButton("Yes"){_, _ -> super.onBackPressed()}
                .setNegativeButton("No", null)
                .create().show()
        }
    }

    override fun showFragmentNow(fragment: Fragment, tag: String?){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_container, fragment, tag)
            .commitNow()
    }

    override fun showFragment(fragment: Fragment, name: String?, tag: String?){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_container, fragment, tag)
            .addToBackStack(name)
            .commit()
    }

    fun showFragmentWithToolbar(fragment: Fragment, name: String?, tag: String?){
        val simpleToolbarFragment = SimpleToolbarFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_container, simpleToolbarFragment, tag)
            .addToBackStack(name)
            .commit()
        supportFragmentManager.executePendingTransactions()
        simpleToolbarFragment.showFragmentNow(fragment, null)
    }

    override fun androidInjector(): AndroidInjector<Any> = fragmentInjector
}
