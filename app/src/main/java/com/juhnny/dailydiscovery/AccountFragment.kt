package com.juhnny.dailydiscovery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.juhnny.dailydiscovery.databinding.FragmentAccountBinding

class AccountFragment:Fragment() {

    val b by lazy { FragmentAccountBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val mainActivity = requireActivity() as MainActivity
//        mainActivity.setSupportActionBar(null)
//        mainActivity.setSupportActionBar(b.toolbar)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mainActivity.supportActionBar?.title = "Account"
        mainActivity.supportActionBar?.setCustomView(R.layout.actionbar_account)


//        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        mainActivity.b.toolbar.setNavigationOnClickListener {object : View.OnClickListener{
//            override fun onClick(p0: View?) {
//                Log.e("myTag", "aaaaa")
//
//            }
//        }
//        }
    }//onViewCreated()

    override fun onResume() {
        super.onResume()
        requireActivity().invalidateOptionsMenu()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                Toast.makeText(context, "home", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    

}