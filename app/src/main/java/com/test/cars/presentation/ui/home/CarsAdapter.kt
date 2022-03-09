package com.test.cars.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.cars.R
import com.test.cars.databinding.ViewholderItemManufacturerRowBinding

class CarsAdapter(private val context: Context) : PagingDataAdapter<Pair<String, String>, CarsAdapter.ViewHolder>(DiffUtilCallBack()) {


    private var clickListener: ((String) -> Unit)? = null

    fun bindClickListener(lambdaFun: (String) -> Unit) {
        clickListener = lambdaFun
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewholderItemManufacturerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, context,)
        if (position % 2 != 0) {
            holder.renderImage(ODD_ROW_IMAGE_URL)
            holder.itemView.setBackgroundResource(R.drawable.list_background_color_default);
        } else {
            holder.renderImage(EVEN_ROW_IMAGE_URL)
            holder.itemView.setBackgroundResource(R.drawable.list_background_color_alternate);
        }

    }

    // Holds the views for adding it to image and text
    class ViewHolder(
        private val binding: ViewholderItemManufacturerRowBinding,
        private val clickListener: ((String) -> Unit)? = null
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(pairs: Pair<String, String>, context: Context) {
            binding.viewHolderItemName.text =  pairs.second
            binding.root.setOnClickListener {
                clickListener?.let {
                    it(pairs.first)
                }
            }
        }

        fun renderImage(url: String) {
            Glide.with(binding.viewHolderImage)
                .load(url)
                .circleCrop()
                .into(binding.viewHolderImage)
        }
    }

    class DiffUtilCallBack: DiffUtil.ItemCallback<Pair<String, String>>() {
        override fun areItemsTheSame(oldItem: Pair<String, String>, newItem: Pair<String, String>): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Pair<String, String>, newItem: Pair<String, String>): Boolean {
            return oldItem.second == newItem.second
                    && oldItem.second == newItem.second
        }

    }
    companion object {
        private const val ODD_ROW_IMAGE_URL = "https://cdni.autocarindia.com/Utils/ImageResizer.ashx?n=https://cdni.autocarindia.com/Galleries/20180621010041_Volvo_S60_Momentum-fr4ont.jpg&w=730&h=484&q=75&c=1"
        private const val EVEN_ROW_IMAGE_URL ="https://cdni.autocarindia.com/Utils/ImageResizer.ashx?n=http://cms.haymarketindia.net/model/uploads/modelimages/Volvo-S60-130220211541.jpg&w=730&h=484&q=75&c=1"
    }
}