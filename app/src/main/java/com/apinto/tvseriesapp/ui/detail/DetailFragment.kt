package com.apinto.tvseriesapp.ui.detail

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.palette.graphics.Palette
import com.apinto.tvseriesapp.R
import com.apinto.tvseriesapp.core.ImageFactoryHelper
import com.apinto.tvseriesapp.core.Resource.Error
import com.apinto.tvseriesapp.core.Resource.Success
import com.apinto.tvseriesapp.databinding.FragmentDetailBinding
import com.apinto.tvseriesapp.model.TvSerieDetails
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.coordinator_header.view.*
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.lang.Exception

class DetailFragment : Fragment() {

    private val mViewModel: DetailViewModel by inject()
    private val mImageHelper: ImageFactoryHelper by inject()

    private var mBinding: FragmentDetailBinding? = null
    private val binding get() = mBinding!!

    private var backgroundColor: Int = 0
    private var textColor: Int = 0
    private var isSuscripted = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        arguments?.let {
            val safeargs =
                DetailFragmentArgs.fromBundle(it)
            getDetails(safeargs.serieId)
        } ?: showErrorMessage()


    }

    private fun initViews() {
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.subscriptionButton.setOnClickListener {
            isSuscripted = !isSuscripted
            checkSubscriptionButtonState()
        }
    }

    private fun checkSubscriptionButtonState() {
        if (isSuscripted) {
            binding.subscriptionButton.background = resources.getDrawable(R.drawable.subscribe_button_selected, null)
            binding.subscriptionButton.setTextColor(backgroundColor)
            binding.subscriptionButton.text = getString(R.string.subscripted_text)
        } else {
            binding.subscriptionButton.background = resources.getDrawable(R.drawable.subscribe_button_unselected, null)
            binding.subscriptionButton.setTextColor(textColor)
            binding.subscriptionButton.text = getString(R.string.no_subscript_text)
        }
    }

    private fun getDetails(serieId: Long) {
        mViewModel.getDetails(serieId).observe(viewLifecycleOwner, Observer {
            when(it) {
                is Success -> {
                    fillDetails(it.data)
                }

                is Error -> {
                    showErrorMessage()
                    Timber.d("Error: ${it.exception.localizedMessage}")
                }
            }
        })
    }

    private fun fillDetails(details: TvSerieDetails) {
        binding.serieTitleTextView.text = details.name

        binding.serieLaunchYearTextView.text = details.firstAirDate.split("-")[0]

        binding.descriptionTextView.text = details.overview

        val url = mImageHelper.getDefaultSizePosterPath(details.posterPath)

        //Added resize values in order to avoid onBitmapLoaded method never called
        Picasso.get().load(url).resize(182, 273).into(object: Target {
            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                binding.appBar.posterImage.setImageBitmap(bitmap)

                handleBackgroundColor(bitmap)

            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }
        })

    }

    private fun handleBackgroundColor(bitmap: Bitmap?) {
        Palette.from(bitmap!!).generate {palette ->

            palette?.let {
                backgroundColor = it.mutedSwatch?.rgb
                    ?: it.vibrantSwatch?.rgb ?: requireContext().getColor(R.color.white)

                binding.screenBackground.setImageBitmap(bitmap)
                binding.colorMask.setBackgroundColor(backgroundColor)

                textColor = it.lightMutedSwatch?.rgb
                    ?: it.lightVibrantSwatch?.rgb ?: requireContext().getColor(R.color.black)

                binding.serieTitleTextView.setTextColor(textColor)
                binding.serieLaunchYearTextView.setTextColor(textColor)
                binding.descriptionTextView.setTextColor(textColor)

                checkSubscriptionButtonState()
                stopLoading()
            }
        }
    }

    private fun stopLoading() {
        binding.loadingProgressBar.visibility = GONE
        binding.appBar.visibility = VISIBLE
        binding.scrollView.visibility = VISIBLE

    }

    private fun showErrorMessage() {
        binding.errorMessageTextView.text = getString(R.string.generic_error_message)

        binding.errorMessageTextView.visibility = VISIBLE
        binding.loadingProgressBar.visibility = GONE
    }
}