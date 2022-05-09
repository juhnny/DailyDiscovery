package com.juhnny.dailydiscovery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.juhnny.dailydiscovery.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
//When a user taps a Preference with an associated Fragment,
// the interface method PreferenceFragmentCompat.OnPreferenceStartFragmentCallback.onPreferenceStartFragment() is called.
// This method is where you should handle displaying the new screen and should be implemented in the surrounding Activity.

//Note: if you do not implement onPreferenceStartFragment(),
// a fallback implementation is used instead. While this works in most cases,
// we strongly recommend implementing this method so you can fully configure transitions
// between Fragment objects and update the title displayed in your Activity toolbar, if applicable.

    val b by lazy { ActivitySettingsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        setSupportActionBar(b.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_settings_ac, SettingsFragment())
            .commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat,
        pref: Preference
    ): Boolean {
// Instantiate the new Fragment
//        val args = pref.extras
//        val fragment = supportFragmentManager.fragmentFactory.instantiate(
//            classLoader,
//            pref.fragment)
//        fragment.arguments = args
//        fragment.target
//        fragment.setTargetFragment(caller, 0)
//        // Replace the existing Fragment with the new Fragment
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.container_settings_ac, fragment)
//            .addToBackStack(null)
//            .commit()
        return true
    }
}