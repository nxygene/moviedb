package com.sberkozd.moviedb.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.sberkozd.moviedb.R
import com.sberkozd.moviedb.databinding.ActivityMainBinding
import com.skydoves.bindables.BindingActivity
import dagger.hilt.android.AndroidEntryPoint


//class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main)
@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // The layout for this activity is a Data Binding layout so it needs to be inflated using
        // DataBindingUtil.
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
    }
}