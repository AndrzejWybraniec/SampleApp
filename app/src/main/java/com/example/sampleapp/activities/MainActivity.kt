package com.example.sampleapp.activities

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import com.example.sampleapp.R
import com.example.sampleapp.adapters.DataBoundRecyclerViewAdapter
import com.example.sampleapp.consts.UpdateListState
import com.example.sampleapp.databinding.ActivityMainBinding
import com.example.sampleapp.handlers.MainHandler
import com.example.sampleapp.models.ElementModel
import com.example.sampleapp.viewmodels.MainActivityViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainHandler {

    val CURRENT_LIST = "CURRENT_LIST"
    val CURRENT_STATE = "CURRENT_STATE"

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
    }

    private fun initView() {
        binding.viewModel = viewModel
        binding.handler = this

        initRecyclerView()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        restoreData(savedInstanceState)
    }

    private fun restoreData(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            val strObj = it.getString(CURRENT_LIST)
            strObj?.let {
                viewModel.restoreList(it)
            }
            viewModel.restoreUpdateState(it.getInt(CURRENT_STATE))
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.addItemDecoration(getDivider())
        binding.recyclerView.adapter = DataBoundRecyclerViewAdapter(viewModel.listData)
    }

    private fun getDivider(): RecyclerView.ItemDecoration? {
        //        Options:
//        1.space between elements
//        val ITEM_SPACE = 16.px
//        binding.recyclerView.addItemDecoration(SimpleItemDecoration(ITEM_SPACE))
//        2.new way added in version 25.1.0
//        binding.recyclerView.addItemDecoration( DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL))
//        3. new way with drawable. Preferable
        val divider = DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(this, R.drawable.divider_2dp)?.let {
            divider.setDrawable(it)
        }
        return divider
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        val list = viewModel.getListAsGson()
        outState?.putString(CURRENT_LIST, list)
        outState?.putInt(CURRENT_STATE,viewModel.getCurrentState().ordinal)
        super.onSaveInstanceState(outState)
    }

    override fun start() {
        viewModel.start()
    }

    override fun stop() {
        viewModel.stop()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stop()
    }

}
