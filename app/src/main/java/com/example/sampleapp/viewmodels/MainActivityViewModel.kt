package com.example.sampleapp.viewmodels

import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableList
import android.graphics.drawable.shapes.OvalShape
import android.util.Log
import com.example.sampleapp.BR
import com.example.sampleapp.R
import com.example.sampleapp.adapters.RecyclerViewData
import com.example.sampleapp.adapters.gson.ElementTypeJsonAdapter
import com.example.sampleapp.consts.OvalColor
import com.example.sampleapp.consts.UpdateListState
import com.example.sampleapp.extensions.fromJson
import com.example.sampleapp.extensions.value
import com.example.sampleapp.models.ElementModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Timed
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivityViewModel @Inject constructor() {

    private val random = Random()
    private val PERIOD_TIME = 1000L

    var startEnabled = ObservableBoolean(true)
    var updateListState = ObservableField<UpdateListState>(UpdateListState.LIST_CREATED)
    private var updateListAction: Disposable? = null

    val listData = object : RecyclerViewData<ElementModel>() {
        override val itemBindID: Int = BR.model
        override val items = ObservableArrayList<ElementModel>()
        override fun itemLayoutID(viewType: Int) = R.layout.view_element
    }

    private var items: ObservableList<ElementModel>? = null
        get() = listData.items

    private var listSize = 0
        get() = listData.items.size

    init {
        items?.clear()
    }

    //region star/stop
    fun start() {
        if (!startEnabled.value) return
        startEnabled.value = false
        updateListState.value = UpdateListState.UPDATING
        updateListAction =
                intervalObservable(PERIOD_TIME)
                        .subscribe {
                            updateList()
                        }
    }

    private fun intervalObservable(period: Long): Observable<Timed<Long>> {
        return Observable.interval(period, TimeUnit.MILLISECONDS)
                .timeInterval()
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun stop() {
        updateListState.value = UpdateListState.STOPPED
        updateListAction?.dispose()
        startEnabled.value = true
    }


    private fun updateList() {
        if (listSize < 5) {
            addElement()
        } else {
            updateElement()
        }
    }
    //endregion

    //region add new element
    private fun addElement() {
        items?.add(ElementModel(getRandomColor(), 0))
    }

    private fun getRandomColor(): OvalColor {
        return OvalColor.values()[random.nextInt(OvalColor.values().size)]
    }
    //endregion

    //region update action
    private fun updateElement() {
        val action = random.nextInt(100)
        val randomIndex = random.nextInt(listSize)
        val randomElement = items?.get(randomIndex)
        if (randomElement != null) {
            when {
                action < 50 -> addCounter(randomElement)
                action < 85 -> resetCounter(randomElement)
                action < 95 -> removeElement(randomElement)
                else -> addPreviousCounter(randomElement, randomIndex)
            }
        }
    }

    private fun addPreviousCounter(randomElement: ElementModel, randomIndex: Int) {
        val previousElement = when (randomIndex) {
            0 -> items?.get(listSize - 1)
            else -> items?.get(randomIndex - 1)
        }

        randomElement.counter = randomElement.counter + (previousElement?.counter ?: 0)
    }

    private fun removeElement(randomElement: ElementModel) {
        listData.items.remove(randomElement)
    }

    private fun resetCounter(randomElement: ElementModel) {
        randomElement.counter = 0
    }

    private fun addCounter(randomElement: ElementModel) {
        randomElement.counter = randomElement.counter.inc()
    }

    fun restoreList(listAsString: String) {
        val gson = ElementsListGsonBuilder()
        fillList(gson.fromJson<ArrayList<ElementModel>>(listAsString))
    }
    //endregion

    //region save and restore data
    private fun fillList(fromJson: ArrayList<ElementModel>?) {
        if (fromJson == null) return
        items?.addAll(fromJson)
    }

    fun getListAsGson(): String? {
        return ElementsListGsonBuilder().toJson(items?.toList())
    }

    fun restoreUpdateState(stateObj: Int) {
        val state = UpdateListState.values()[stateObj]
        updateListState.value = when (state) {
            UpdateListState.UPDATING -> UpdateListState.STOPPED
            else -> state
        }
    }

    fun getCurrentState(): UpdateListState {
        return updateListState.value ?: UpdateListState.LIST_CREATED
    }

    private fun ElementsListGsonBuilder() =
            GsonBuilder()
                    .registerTypeAdapter(OvalShape::class.java, ElementTypeJsonAdapter())
                    .create()

    //endregion
}