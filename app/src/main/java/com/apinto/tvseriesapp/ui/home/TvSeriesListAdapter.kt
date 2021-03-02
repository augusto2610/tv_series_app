package com.apinto.tvseriesapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.DiffResult.NO_POSITION
import androidx.recyclerview.widget.RecyclerView
import com.apinto.tvseriesapp.core.BaseViewHolder
import com.apinto.tvseriesapp.core.ImageFactoryHelper
import com.apinto.tvseriesapp.databinding.TvSerieItemBinding
import com.apinto.tvseriesapp.model.Genre
import com.apinto.tvseriesapp.model.TvSerie
import com.squareup.picasso.Picasso


class TvSeriesListAdapter(private val mContext: Context, private val imageHelper: ImageFactoryHelper): RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnTvSerieClickListener {
        fun onTvSerieClick(serieId: Long)
    }

    private var mClickListener: OnTvSerieClickListener? = null

    private val mSeriesList = mutableListOf<TvSerie>()
    private var mGenreList: List<Genre>? = null

    fun updateList(list: List<TvSerie>) {
        mSeriesList.addAll(list)
        notifyDataSetChanged()
    }

    fun setGenreList(list: List<Genre>) {
        mGenreList = list
    }

    fun setOnClickListener(listener: OnTvSerieClickListener) {
        mClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = TvSerieItemBinding.inflate(LayoutInflater.from(mContext), parent, false)

        val holder = ViewHolder(itemBinding)

        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != NO_POSITION } ?: return@setOnClickListener
            mClickListener?.onTvSerieClick(mSeriesList[position].id)
        }

        return holder
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

            val url = "${imageHelper.getDefaultSizeBackdropPath(item.backdropPath)}"

            mGenreList?.find {
                it.id == item.genreIds.firstOrNull()
            }?.let {
                serieGenreTextView.text = it.name.toUpperCase()
            }

            Picasso.get().load(url).into(binding.seriePosterImageView)
        }
    }
}