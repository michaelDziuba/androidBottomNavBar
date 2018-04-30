package com.readablecolors.mdziuba.medscalculator

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        PrescriptionCalcFragment.OnFragmentInteractionListener,
        ReverseCalcFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener {


    companion object {
//        val prescriptionCalcFragment = "prescriptionCalcFragment"
//        val reverseCalcFragment = "reverseCalcFragment"
//        val aboutFragment = "aboutFragment"
        val MyAppPreferences = "MyAppPreferences"
        val fragmentId = "fragmentId"
    }

    var sharedPref: SharedPreferences? = null


    private var fragmentId = R.id.navigation_prescription

    private var prescriptionCalcFragment: PrescriptionCalcFragment? = null
    private var reverseCalcFragment: ReverseCalcFragment? = null
    private var aboutFragment: AboutFragment? = null

//    private var fragments = hashMapOf<String, AppFragment?>(
//            MainActivity.prescriptionCalcFragment to prescriptionCalcFragment as AppFragment?,
//            MainActivity.reverseCalcFragment to reverseCalcFragment as AppFragment?,
//            MainActivity.aboutFragment to aboutFragment as AppFragment?
//    )

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_prescription -> {
                replaceFragment(prescriptionCalcFragment!!)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_reverse -> {
                replaceFragment(reverseCalcFragment!!)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_about -> {
                replaceFragment(aboutFragment!!)
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

        this.fragmentId = sharedPref!!.getInt(MainActivity.fragmentId, R.id.navigation_prescription)
        this.generateFragments()

        navigation.selectedItemId = fragmentId
    }

    override fun onDestroy() {
        super.onDestroy()
        prescriptionCalcFragment = null
        reverseCalcFragment = null
        aboutFragment = null
    }




    /**
     * Replaces shown fragment with paramenter specified fragment
     * @param fragment the fragment to replace currently showing fragment
     */
    fun replaceFragment(fragment: AppFragment) {
        if(supportFragmentManager != null) {
            fragmentId = fragment.fragmentId
            with(sharedPref!!.edit()) { putInt(MainActivity.fragmentId, fragmentId).apply() }
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

        prescriptionCalcFragment = PrescriptionCalcFragment.newInstance(sharedPref!!)
        reverseCalcFragment = ReverseCalcFragment.newInstance(sharedPref!!)
        aboutFragment = AboutFragment.newInstance(sharedPref!!)

    }


    /**
     * Required by OnFragmentInteractionListener
     */
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
