package com.apinto.tvseriesapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.apinto.tvseriesapp.core.BaseViewHolder
import com.apinto.tvseriesapp.core.ImageFactoryHelper
import com.apinto.tvseriesapp.databinding.SubscribedListItemBinding
import com.apinto.tvseriesapp.model.TvSerieEntity
import com.squareup.picasso.Picasso

class SubscriptionsListAdapter(private val mContext: Context,
                               private val mImageHelper: ImageFactoryHelper): RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnSubscribedClickListener {
        fun onClicked(serieId: Long)
    }

    private var mSubscribedClickListener: OnSubscribedClickListener? = null
    private val mSeriesList = mutableListOf<TvSerieEntity>()

    fun updateList(list: List<TvSerieEntity>) {
        mSeriesList.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: OnSubscribedClickListener) {
        mSubscribedClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = SubscribedListItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        val holder = SubscribedViewHolder(itemBinding)

        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION } ?: return@setOnClickListener

            mSubscribedClickListener?.onClicked(mSeriesList[position].serieId)
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is SubscribedViewHolder -> holder.bind(mSeriesList[position])
        }
    }

    override fun getItemCount() = mSeriesList.size

    private inner class SubscribedViewHolder(val binding: SubscribedListItemBinding): BaseViewHolder<TvSerieEntity>(binding.root) {

        override fun bind(item: TvSerieEntity) {
            val url = mImageHelper.getDefaultSizePosterPath(item.posterPath)
            Picasso.get().load(url).into(binding.posterImage)
        }

    }

}