package com.juhnny.dailydiscovery

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.preference.*
import com.juhnny.dailydiscovery.databinding.FragmentSettingsBinding

class SettingsFragment : PreferenceFragmentCompat() {

    val settingsActivity by lazy { requireActivity() as SettingsActivity }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
//        findPreference<EditTextPreference>("bbb")?.layoutResource = R.layout.recycler_item_follow
//        findPreference<EditTextPreference>("bbb")?.widgetLayoutResource = R.layout.recycler_item_follow
//        findPreference<EditTextPreference>("bbb")?.dialogLayoutResource = R.layout.recycler_item_follow

    }

    //각 항목들의 설정값이 항목명 아래 summary에 바로 보여지게 하고 싶다면 리스너로 값을 바꿔줘야 한다.
    //onResume, onPause 양족에서 쓰기 위해 멤버변수로 만들어주고
    val prefs by lazy { PreferenceManager.getDefaultSharedPreferences(requireContext()) }

    override fun onResume() {
        super.onResume()

        //summary 값을 바꾸고 싶을 떄
        findPreference<SwitchPreferenceCompat>("aaa")?.summaryProvider = Preference.SummaryProvider { preference:SwitchPreferenceCompat ->
            "이 항목은..."
        }

        //항목이 바뀌었을 때
        prefs.registerOnSharedPreferenceChangeListener(listener)

        //항목이 클릭되었을 때
        findPreference<EditTextPreference>("bbb")?.setOnPreferenceClickListener(object : Preference.OnPreferenceClickListener{
            override fun onPreferenceClick(preference: Preference): Boolean {
//                Toast.makeText(requireContext(), "bbb", Toast.LENGTH_SHORT).show()
                return true //구글 문서 예제에 true
            }
        })
    }

    override fun onPause() {
        super.onPause()
    }

    val listener = object : SharedPreferences.OnSharedPreferenceChangeListener{
        override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
            //두번째 파라미터는 변경된 항목의 key값
            val buffer = StringBuffer()
            when(p1){
                "about_app" -> {

                }
            }
            MyUtil.showSorryAlert(requireContext())
        }
    }

}