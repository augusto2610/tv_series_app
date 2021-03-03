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

class HomeFragment : Fragment() {

    private val mHomeViewModel: HomeViewModel by inject()

    private var mBinding: FragmentHomeBinding? = null
    private val binding get() = mBinding!!

    private lateinit var mAdapter: TvSeriesListAdapter
    private lateinit var mSubscribedAdapter: SubscriptionsListAdapter

    private val mImageUrlHelper: ImageFactoryHelper by inject()

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
        mAdapter = TvSeriesListAdapter(
            requireContext(),
            mImageUrlHelper
        )
        mAdapter.setOnClickListener(object: TvSeriesListAdapter.OnTvSerieClickListener {
            override fun onTvSerieClick(serieId: Long) {
                mHomeViewModel.shouldLoadAgain = false

                val action =
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                action.serieId = serieId
                findNavController().navigate(action)
            }
        })

        val layoutManager = LinearLayoutManager(requireContext())

        layoutManager.orientation = VERTICAL
        binding.tvSeriesRecyclerView.layoutManager = layoutManager
        binding.tvSeriesRecyclerView.adapter = mAdapter

        initSubscribedList()
    }

    private fun initSubscribedList() {
        mSubscribedAdapter = SubscriptionsListAdapter(requireContext(), mImageUrlHelper)

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = HORIZONTAL

        binding.subscriptionsRecyclerView.layoutManager = layoutManager
        binding.subscriptionsRecyclerView.adapter = mSubscribedAdapter
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
                    Timber.d("genres: ${it.data.genres}")
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
        mHomeViewModel.getTvSeriesList().observe(viewLifecycleOwner, Observer {
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

    private fun getSubscriptions() {
        mHomeViewModel.getSubscriptions().observe(viewLifecycleOwner, Observer {
            when(it) {
                is Success -> {
                    Timber.d("apinto - Subscriptions: ${it.data.size}")
                    mSubscribedAdapter.updateList(it.data)

                    if (it.data.isNotEmpty()) {
                        binding.subscriptionGroup.visibility = VISIBLE
                        if (binding.loadingProgressBar.visibility == VISIBLE) {
                            binding.loadingProgressBar.visibility = GONE
                        }
                    }
                }

                is Error -> {
                    Timber.d("apinto - error: ${it.exception.localizedMessage}")
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