package com.example.demo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.demo.R
import com.example.demo.databinding.FragmentLocationDialogBinding

/**
 * Created by Harshal Chaudhari on 22/12/19.
 */


 class LocationFindingDialog: DialogFragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentLocationDialogBinding
    private val adapter: LocAdapter = LocAdapter()


    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog?.window?.setLayout(width, height)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.full_screen_dialog)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_location_dialog, container, false)
        viewModel = ViewModelProvider(activity!!).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.locAdapter = adapter
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        binding.include2.headerTitle = getString(R.string.find_restaurants)

        viewModel.locData.observe(viewLifecycleOwner, Observer {
            it?.let { loca ->
                adapter.clearList()
                adapter.setList(loca.locationSuggestions.toMutableList())
            }
        })
    }


}