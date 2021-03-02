package com.apinto.tvseriesapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.apinto.tvseriesapp.DetailFragment
import com.apinto.tvseriesapp.R
import com.apinto.tvseriesapp.core.ImageFactoryHelper
import com.apinto.tvseriesapp.core.Resource.*
import com.apinto.tvseriesapp.databinding.FragmentHomeBinding
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.inject
import timber.log.Timber

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

        getConfiguration()
    }

    private fun initViews() {
        mAdapter = TvSeriesListAdapter(requireContext(), imageUrlHelper)
        mAdapter.setOnClickListener(object: TvSeriesListAdapter.OnTvSerieClickListener {
            override fun onTvSerieClick(serieId: Long) {

                mHomeViewModel.shouldLoadAgain = false

                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                action.serieId = serieId
                findNavController().navigate(action)
            }
        })

        val layoutManager = LinearLayoutManager(requireContext())


        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.tvSeriesRecyclerView.layoutManager = layoutManager
        binding.tvSeriesRecyclerView.adapter = mAdapter

    }
    private fun getConfiguration() {
        mHomeViewModel.getConfiguration().observe(this, Observer {
            when(it) {
                is Loading -> {
                    showLoading()
                }
                is Success -> {
                    imageUrlHelper.setImageConfig(it.data)
                    getGenreList()
                }
                is Error -> {
                    Timber.e("Error ${it.exception.localizedMessage}")
                    showErrorMessage()
                }
            }
        })
    }

    private fun getGenreList() {
        mHomeViewModel.getGenreList().observe(this, Observer {
            when(it) {

                is Success -> {
                    Timber.d("genres: ${it.data.genres}")
                    mAdapter.setGenreList(it.data.genres)
                    getTvSeriesList()
                }

                is Error -> {
                    Timber.e("Error ${it.exception.localizedMessage}")
                    showErrorMessage()
                }
            }
        })
    }

    private fun getTvSeriesList() {
        mHomeViewModel.getTvSeriesList().observe(this, Observer {
            when(it) {

                is Error -> {
                    Timber.d("Error ${it.exception.localizedMessage}")
                    showErrorMessage()
                }

                is Success -> {
                    showLoading(false)

                    mAdapter.updateList(it.data.results)
                }
            }
        })
    }

    private fun showLoading(show: Boolean = true) {
        binding.errorMessageTextView.visibility = View.GONE

        if (show) {
            binding.loadingProgressBar.visibility = View.VISIBLE
            binding.contentGroup.visibility = View.GONE
        } else {
            binding.loadingProgressBar.visibility = View.GONE
            binding.contentGroup.visibility = View.VISIBLE
        }
    }

    private fun showErrorMessage(message: String = requireContext().getString(R.string.generic_error_message)) {
        binding.contentGroup.visibility = View.GONE
        binding.loadingProgressBar.visibility = View.GONE

        binding.errorMessageTextView.visibility = View.VISIBLE
        binding.errorMessageTextView.text = message
    }

}