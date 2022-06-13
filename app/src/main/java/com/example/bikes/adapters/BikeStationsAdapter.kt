package com.example.bikes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bikes.databinding.ItemBikeStationBinding
import com.example.bikes.model.domain.BikeStationModel

class BikeStationsAdapter(
    private var clickCallback: ((BikeStationModel) -> Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: MutableList<BikeStationModel> = arrayListOf()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BikeStationViewHolder(
            ItemBikeStationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as BikeStationViewHolder
        holder.bind(items[position])
    }

    fun setItemsList(values: List<BikeStationModel>) {
        val diffResult = DiffUtil.calculateDiff(ItemsDiffUtilCallback(items, values))
        items.clear()
        items.addAll(values)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class BikeStationViewHolder(private val binding: ItemBikeStationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemModel: BikeStationModel) {
            binding.layoutBikeStation.tvTitle.text = itemModel.name
            binding.layoutBikeStation.tvAvailableBikesValue.text = itemModel.availableBikes.toString()
            binding.layoutBikeStation.tvAvailablePlacesValue.text = itemModel.availablePlaces.toString()
            binding.root.setOnClickListener { clickCallback(itemModel) }
        }
    }

    class ItemsDiffUtilCallback(
        private val oldList: List<BikeStationModel>,
        private val newList: List<BikeStationModel>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldList[oldItemPosition].id == newList[newItemPosition].id
                    && oldList[oldItemPosition].name == newList[newItemPosition].name
                    && oldList[oldItemPosition].availableBikes == newList[newItemPosition].availableBikes
                    && oldList[oldItemPosition].availablePlaces == newList[newItemPosition].availablePlaces
                    && oldList[oldItemPosition].distance == newList[newItemPosition].distance)

        }

    }

}
