package com.apinto.tvseriesapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apinto.tvseriesapp.core.BaseViewHolder
import com.apinto.tvseriesapp.databinding.TvSerieItemBinding
import com.apinto.tvseriesapp.model.TvSerie
import com.squareup.picasso.Picasso
import timber.log.Timber

class TvSeriesListAdapter(private val mContext: Context): RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var mSeriesList = mutableListOf<TvSerie>()

    fun updateList(list: List<TvSerie>) {
        mSeriesList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = TvSerieItemBinding.inflate(LayoutInflater.from(mContext), parent, false)

        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {

        when(holder) {
            is ViewHolder -> holder.bind(mSeriesList[position])
        }

    }

    override fun getItemCount(): Int = mSeriesList.size

    private inner class ViewHolder(val binding: TvSerieItemBinding): BaseViewHolder<TvSerie>(binding.root) {

        override fun bind(item: TvSerie) = with(binding) {
            serieTitleTextView.text = item.originalName

            val url = "https://image.tmdb.org/t/p/w500/${item.backdropPath}"

            Timber.d("apinto - $url")

            Picasso.get().load(url).into(binding.seriePosterImageView)
        }
    }
}