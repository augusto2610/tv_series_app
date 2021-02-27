package com.apinto.tvseriesapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.apinto.tvseriesapp.R
import com.apinto.tvseriesapp.core.Resource
import org.koin.android.ext.android.inject
import timber.log.Timber

class SplashFragment : Fragment() {

    private val mSplashViewModel: SplashViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onResume() {
        super.onResume()

        mSplashViewModel.getTvSeriesList().observe(this, Observer {
            when(it) {
                is Resource.Loading -> {
                    Timber.d("Loading")
                }
                is Resource.Error -> {
                    Timber.d("Error getting tv series")
                }

                is Resource.Success -> {
                    Timber.d("${it.data.totalResults}")
                }
            }
        })
    }
}