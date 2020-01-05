package com.example.demo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.base.data.LocationSuggestion
import com.example.demo.base.data.NearbyRestaurant
import com.example.demo.base.loadImage
import com.example.demo.databinding.LocViewHolderBinding
import com.example.demo.databinding.MainViewHolderBinding

/**
 * Created by Harshal Chaudhari on 18/12/19.
 */
class MainAdapter: RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val list = mutableListOf<NearbyRestaurant>()
    var viewModel: MainViewModel? = null
    set(value) {
        field = value
    }

    fun setList(lists: MutableList<NearbyRestaurant>) {
        list.addAll(lists)
        notifyDataSetChanged()
    }

    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }

    val removeFromPos = { pos: Int ->
        list.removeAt(pos)
        if (pos != 0)
            notifyItemRemoved(pos)
        else
            notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding: MainViewHolderBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.main_view_holder, parent, false)
        return MainViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun getItemId(position: Int): Long = position.toLong()


    inner class MainViewHolder(val binding: MainViewHolderBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bindData(restaurants: NearbyRestaurant) {
            binding.restaurants = restaurants
            binding.ivFoodImage.loadImage(restaurants.restaurant?.thumb ?: "")
        }

        override fun onClick(p0: View?) {
            viewModel?.itemClick?.value = Pair(list[adapterPosition], adapterPosition)
        }
    }
}



class LocAdapter: RecyclerView.Adapter<LocAdapter.LocViewHolder>() {

    private val list = mutableListOf<LocationSuggestion>()
    var viewModel: MainViewModel? = null
        set(value) {
            field = value
        }

    fun setList(lists: MutableList<LocationSuggestion>) {
        list.addAll(lists)
        notifyDataSetChanged()
    }
    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }

    val removeFromPos = { pos: Int ->
        list.removeAt(pos)
        if (pos != 0)
            notifyItemRemoved(pos)
        else
            notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocAdapter.LocViewHolder {
        val binding = DataBindingUtil.inflate<LocViewHolderBinding>(LayoutInflater.from(parent.context), R.layout.loc_view_holder, parent, false)
        return LocViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun getItemId(position: Int): Long = position.toLong()


    inner class LocViewHolder(val binding: LocViewHolderBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bindData(locData: LocationSuggestion) {
            binding.locData = locData
        }

        override fun onClick(p0: View?) {
            viewModel?.let {
                it.locItemClick.value = list[adapterPosition]
            }
        }
    }




}



