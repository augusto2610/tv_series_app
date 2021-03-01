package com.apinto.tvseriesapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.apinto.tvseriesapp.core.ImageFactoryHelper
import com.apinto.tvseriesapp.core.Resource.*
import com.apinto.tvseriesapp.databinding.FragmentHomeBinding
import org.koin.android.ext.android.inject
import timber.log.Timber
import kotlin.Error

class HomeFragment : Fragment() {

    private val mHomeViewModel: HomeViewModel by inject()

    private var mBinding: FragmentHomeBinding? = null
    private val binding get() = mBinding!!

    private lateinit var mAdapter: TvSeriesListAdapter

    private val imageUrlHelper: ImageFactoryHelper by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        mBinding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        mAdapter = TvSeriesListAdapter(requireContext(), imageUrlHelper)
        val layoutManager = LinearLayoutManager(requireContext())


        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.tvSeriesRecyclerView.layoutManager = layoutManager
        binding.tvSeriesRecyclerView.adapter = mAdapter

    }

    override fun onResume() {
        super.onResume()

        getConfiguration()
    }

    private fun getConfiguration() {
        mHomeViewModel.getConfiguration().observe(this, Observer {
            when(it) {
                is Loading -> {}
                is Success -> {
                    imageUrlHelper.setImageConfig(it.data)
                    getGenreList()
                }
                is Error -> {
                    Timber.e("Error ${it.localizedMessage}")
                }
            }
        })
    }

    private fun getGenreList() {
        mHomeViewModel.getGenreList().observe(this, Observer {
            when(it) {
                is Loading -> {
                    Timber.d("Loading")
                }

                is Success -> {
                    Timber.d("genres: ${it.data.genres}")
                    mAdapter.setGenreList(it.data.genres)
                    getTvSeriesList()
                }

                is Error -> {
                    Timber.e("Error ${it.localizedMessage}")
                }
            }
        })
    }

    private fun getTvSeriesList() {
        mHomeViewModel.getTvSeriesList().observe(this, Observer {
            when(it) {
                is Loading -> {
                    Timber.d("Loading")
                }

                is Error -> {
                    Timber.d("Error getting tv series")
                }

                is Success -> {
                    mAdapter.updateList(it.data.results)
                }
            }
        })
    }
}