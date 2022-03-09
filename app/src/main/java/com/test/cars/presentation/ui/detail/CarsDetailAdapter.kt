package com.test.cars.presentation.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.cars.R
import com.test.cars.databinding.ItemCarDetailBinding
import com.test.cars.presentation.ui.home.CarsAdapter

class CarsDetailAdapter(
    private val context: Context,
    private val list: List<Pair<String, String>>
) : RecyclerView.Adapter<CarsDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCarDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], context)
        holder.renderImage(ODD_ROW_IMAGE_URL)

    }

    // Holds the views for adding it to image and text
    class ViewHolder(
        private val binding: ItemCarDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Pair<String, String>, context: Context) {
            binding.viewHolderItemName.text = model.second
        }

        fun renderImage(url: String) {
            Glide.with(binding.viewHolderImage)
                .load(url)
                .fitCenter()
                .into(binding.viewHolderImage)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    companion object {
        private const val ODD_ROW_IMAGE_URL =
            "https://cdni.autocarindia.com/Utils/ImageResizer.ashx?n=https://cdni.autocarindia.com/Galleries/20180621010041_Volvo_S60_Momentum-fr4ont.jpg&w=730&h=484&q=75&c=1"
        private const val EVEN_ROW_IMAGE_URL =
            "https://cdni.autocarindia.com/Utils/ImageResizer.ashx?n=http://cms.haymarketindia.net/model/uploads/modelimages/Volvo-S60-130220211541.jpg&w=730&h=484&q=75&c=1"
    }

}