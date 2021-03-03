package com.apinto.tvseriesapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.apinto.tvseriesapp.R
import com.apinto.tvseriesapp.core.ImageFactoryHelper
import com.apinto.tvseriesapp.core.Resource.*
import com.apinto.tvseriesapp.databinding.FragmentHomeBinding
import org.koin.android.ext.android.inject
import timber.log.Timber

const val FIRST_PAGE_INDEX = 1
class HomeFragment : Fragment() {

    private val mHomeViewModel: HomeViewModel by inject()
    private val mImageUrlHelper: ImageFactoryHelper by inject()

    private var mBinding: FragmentHomeBinding? = null
    private val binding get() = mBinding!!

    private lateinit var mAdapter: TvSeriesListAdapter
    private lateinit var mSubscribedAdapter: SubscriptionsListAdapter

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
        setupTvSerieList()
        setupSubscribedList()
    }

    private fun setupTvSerieList() {
        mAdapter = TvSeriesListAdapter(requireContext(), mImageUrlHelper)
        mAdapter.setOnClickListener(object: TvSeriesListAdapter.OnTvSerieClickListener {
            override fun onTvSerieClick(serieId: Long) {
                navigateToDetails(serieId)
            }
        })

        val layoutManager = LinearLayoutManager(requireContext())

        layoutManager.orientation = VERTICAL
        binding.tvSeriesRecyclerView.layoutManager = layoutManager
        binding.tvSeriesRecyclerView.adapter = mAdapter
    }

    private fun setupSubscribedList() {
        mSubscribedAdapter = SubscriptionsListAdapter(requireContext(), mImageUrlHelper)
        mSubscribedAdapter.setOnClickListener(object: SubscriptionsListAdapter.OnSubscribedClickListener {
            override fun onClicked(serieId: Long) {
                navigateToDetails(serieId)
            }
        })

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = HORIZONTAL

        binding.subscriptionsRecyclerView.layoutManager = layoutManager
        binding.subscriptionsRecyclerView.adapter = mSubscribedAdapter
    }

    private fun navigateToDetails(serieId: Long) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToDetailFragment()
        action.serieId = serieId
        findNavController().navigate(action)
    }

    private fun getConfiguration() {
        mHomeViewModel.getConfiguration().observe(viewLifecycleOwner, Observer {
            when(it) {
                is Loading -> {
                    showLoading()
                }
                is Success -> {
                    mImageUrlHelper.setImageConfig(it.data)
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
        mHomeViewModel.getGenreList().observe(viewLifecycleOwner, Observer {
            when(it) {

                is Success -> {
                    mAdapter.setGenreList(it.data.genres)
                    getTvSeriesList()
                    getSubscriptions()
                }

                is Error -> {
                    Timber.e("Error ${it.exception.localizedMessage}")
                    showErrorMessage()
                }
            }
        })
    }

    private fun getTvSeriesList() {
        mHomeViewModel.getTvSeriesList(FIRST_PAGE_INDEX).observe(viewLifecycleOwner, Observer {
            when(it) {

                is Error -> {
                    Timber.e("Error ${it.exception.localizedMessage}")
                    showErrorMessage()
                }

                is Success -> {
                    showLoading(false)
                    mAdapter.updateList(it.data.results)
                }
            }
        })
    }

    private fun getSubscriptions() {
        mHomeViewModel.getSubscriptions().observe(viewLifecycleOwner, Observer {
            when(it) {
                is Success -> {
                    mSubscribedAdapter.updateList(it.data)

                    if (it.data.isNotEmpty()) {
                        binding.subscriptionGroup.visibility = VISIBLE
                        if (binding.loadingProgressBar.visibility == VISIBLE) {
                            binding.loadingProgressBar.visibility = GONE
                        }
                    }
                }

                is Error -> {
                    Timber.e("Error: ${it.exception.localizedMessage}")
                }
            }
        })
    }

    private fun showLoading(show: Boolean = true) {
        binding.errorMessageTextView.visibility = GONE

        if (show) {
            binding.loadingProgressBar.visibility = VISIBLE
            binding.contentGroup.visibility = GONE
        } else {
            binding.loadingProgressBar.visibility = GONE
            binding.contentGroup.visibility = VISIBLE
        }
    }

    private fun showErrorMessage(message: String = requireContext().getString(R.string.generic_error_message)) {
        binding.contentGroup.visibility = GONE
        binding.loadingProgressBar.visibility = GONE

        binding.errorMessageTextView.visibility = VISIBLE
        binding.errorMessageTextView.text = message
    }

}