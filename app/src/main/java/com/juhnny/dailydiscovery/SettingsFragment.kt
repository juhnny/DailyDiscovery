package com.juhnny.dailydiscovery

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.preference.*
import com.google.firebase.auth.FirebaseAuth

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
        findPreference<Preference>("version")?.summaryProvider = Preference.SummaryProvider {prefernce:Preference ->
            val versionName = BuildConfig.VERSION_NAME
            val versionCode = BuildConfig.VERSION_CODE
            "Version: $versionName($versionCode)"
        }

        //항목이 바뀌었을 때
        prefs.registerOnSharedPreferenceChangeListener(listener)

        //로그인 돼있는 상태면 버튼 숨기기
        findPreference<Preference>("signout")?.isVisible = G.user != null //로그인돼있으면 true, 안돼있으면 false로 입력됨

        //테스트용으로 무조건 보이도록
        findPreference<Preference>("signout")?.isVisible = true

        //항목이 클릭되었을 때
        findPreference<Preference>("signout")?.onPreferenceClickListener = object : Preference.OnPreferenceClickListener{
            override fun onPreferenceClick(preference: Preference): Boolean {
                Toast.makeText(requireContext(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()

                //로그아웃 처리////////
                if(G.user == null) Toast.makeText(requireContext(), "SettingsFrag G.user is null while logged in", Toast.LENGTH_SHORT).show()
                when(G.user?.snsType){
                    "" -> {
                        FirebaseAuth.getInstance().signOut()
                    }
                    "kakao" -> {

                    }
                    "google" -> {

                    }
                    "naver" -> {

                    }
                }
                //////////////

                Toast.makeText(requireContext(), "G.user: ${G.user}", Toast.LENGTH_SHORT).show()
                G.user = null
                val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
                prefs.edit().putBoolean("isLoggedIn", false).commit()
                Toast.makeText(requireContext(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()

                val intent = Intent(requireContext(), MainActivity::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

                return true //구글 문서 예제에 true
            }
        }
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
//            MyUtil.showSorryAlert(requireContext())
        }
    }

}