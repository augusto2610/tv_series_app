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
    }

    private fun getDetails(serieId: Long) {
        mViewModel.getDetails(serieId).observe(viewLifecycleOwner, Observer {
            when(it) {
                is Success -> {
                    fillDetails(it.data)
                    stopLoading()
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

        val url = mImageHelper.getBiggerSizePosterPath(details.posterPath)

        //Added resize values in order to avoid onBitmapLoaded method never called
        Picasso.get().load(url).resize(182, 273).into(object: Target {
            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                binding.appBar.posterImage.setImageBitmap(bitmap)

                Palette.from(bitmap!!).generate {palette ->

                    palette?.let {

                        it.mutedSwatch?.let { mutedSwatch ->
                            binding.subscriptionButton.setBackgroundColor(mutedSwatch.rgb)
                        } ?: it.vibrantSwatch?.let {vibrantSwatch ->
                            binding.subscriptionButton.setBackgroundColor(vibrantSwatch.rgb)
                        }

                        it.lightMutedSwatch?.let { lightMutedSwatch ->
                            binding.subscriptionButton.setTextColor(lightMutedSwatch.rgb)
                        } ?: it.lightVibrantSwatch?.let { lightVibrantSwatch ->
                            binding.subscriptionButton.setTextColor(lightVibrantSwatch.rgb)
                        }

                    }


                }
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }
        })

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