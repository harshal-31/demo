package com.example.demo.base

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.base.data.NearbyRestaurant
import com.example.demo.ui.LocAdapter
import com.example.demo.ui.MainAdapter
import com.example.demo.ui.MainViewModel
import com.google.android.material.button.MaterialButton

/**
 * Created by Harshal Chaudhari on 18/12/19.
 */
class BindingUtil {

    companion object {

        @JvmStatic
        @BindingAdapter(value = ["gitHubModel", "gitAdapter"])
        fun RecyclerView.setGithubList(viewModel: MainViewModel?, gitAdapter: MainAdapter) {
            this.layoutManager = LinearLayoutManager(this.context)
            viewModel?.let {
                addItemDecoration(DividerItemDecoration(context, RecyclerView.HORIZONTAL))
                gitAdapter.setHasStableIds(true)
                gitAdapter.viewModel = it
                this.adapter = gitAdapter
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["locModel", "locAdapter"])
        fun RecyclerView.setGithubList(viewModel: MainViewModel?, locaAdapter: LocAdapter) {
            this.layoutManager = LinearLayoutManager(this.context)
            viewModel?.let {
                if (!locaAdapter.hasObservers())
                    locaAdapter.setHasStableIds(true)

                locaAdapter.viewModel = it
                this.adapter = locaAdapter
            }
        }


        @JvmStatic
        @BindingAdapter("photoUrl")
        fun ImageView.setImageOnView(path: String) {
            this.loadImage(path)
        }

        @JvmStatic
        @BindingAdapter("datssss")
        fun MaterialButton.channn(rest: NearbyRestaurant) {
            val kk = "#${rest.restaurant?.userRating?.ratingColor}"
            this.backgroundTintList = ColorStateList.valueOf(Color.parseColor(kk))
        }



    }

}