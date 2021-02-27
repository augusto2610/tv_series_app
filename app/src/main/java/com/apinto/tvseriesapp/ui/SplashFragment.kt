package com.apinto.tvseriesapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apinto.tvseriesapp.R
import com.apinto.tvseriesapp.core.Resource
import com.apinto.tvseriesapp.databinding.FragmentSplashBinding
import org.koin.android.ext.android.inject
import timber.log.Timber

class SplashFragment : Fragment() {

    private val mSplashViewModel: SplashViewModel by inject()

    private var mBinding: FragmentSplashBinding? = null
    private val binding get() = mBinding!!

    private lateinit var mAdapter: TvSeriesListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        mBinding = FragmentSplashBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        mAdapter = TvSeriesListAdapter(requireContext())
        val layoutManager = LinearLayoutManager(requireContext())


        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.tvSeriesRecyclerView.layoutManager = layoutManager
        binding.tvSeriesRecyclerView.adapter = mAdapter

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
                    mAdapter.updateList(it.data.results)
                }
            }
        })
    }
}