package com.readablecolors.mdziuba.persistentnav

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        FirstFragment.OnFragmentInteractionListener,
        SecondFragment.OnFragmentInteractionListener,
        ThirdFragment.OnFragmentInteractionListener {


    companion object {
        val MyAppPreferences = "MyAppPreferences"
        val fragmentId = "fragmentId"
    }

    var sharedPref: SharedPreferences? = null

    private var fragmentId = R.id.navigation_first

    private var firstFragment: FirstFragment? = null
    private var secondFragment: SecondFragment? = null
    private var thirdFragment: ThirdFragment? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_first -> {
                replaceFragment(firstFragment!!)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_second -> {
                replaceFragment(secondFragment!!)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_third -> {
                replaceFragment(thirdFragment!!)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.app_bar))
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        this.sharedPref = this.getSharedPreferences(MainActivity.MyAppPreferences, Context.MODE_PRIVATE)

        this.fragmentId = sharedPref!!.getInt(MainActivity.fragmentId, R.id.navigation_first)
        this.generateFragments()

        navigation.selectedItemId = fragmentId
    }

    override fun onDestroy() {
        super.onDestroy()
        firstFragment = null
        secondFragment = null
        thirdFragment = null
    }




    /**
     * Replaces shown fragment with parameter specified fragment
     * @param fragment the fragment to replace currently showing fragment
     */
    fun replaceFragment(fragment: AppFragment) {
        if(supportFragmentManager != null) {
            with(sharedPref!!.edit()) { putInt(MainActivity.fragmentId, fragment.fragmentId).apply() }
            supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
                    .replace(R.id.contentMain, fragment as Fragment)
                    .commitAllowingStateLoss()
        }
    }

    /**
     * Generates all fragments used in this app
     */
    private fun generateFragments(){

        firstFragment = FirstFragment.newInstance(sharedPref!!)
        secondFragment = SecondFragment.newInstance(sharedPref!!)
        thirdFragment = ThirdFragment.newInstance(sharedPref!!)

    }


    /**
     * Required by OnFragmentInteractionListener
     */
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
