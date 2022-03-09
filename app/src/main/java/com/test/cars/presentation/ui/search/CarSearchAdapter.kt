package com.test.cars.presentation.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.cars.databinding.ViewholderItemManufacturerRowBinding
import com.test.cars.domain.model.Manufacturer

class CarSearchAdapter(
    private val list: List<Manufacturer>,
    private val context: Context,
) : RecyclerView.Adapter<CarSearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewholderItemManufacturerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], context)
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(
        private val binding: ViewholderItemManufacturerRowBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(car: Manufacturer, context: Context) {
            Glide.with(context)
                .load(URL)
                .into(binding.viewHolderImage)

            binding.viewHolderItemName.text = car.name
        }
    }

    companion object {
        private const val URL =
            "https://cdni.autocarindia.com/Utils/ImageResizer.ashx?n=https://cdni.autocarindia.com/Galleries/20180621010041_Volvo_S60_Momentum-fr4ont.jpg&w=730&h=484&q=75&c=1"

    }
}