package com.kosign.multimodulehilt.viewmodel

import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kosign.multimodulehilt.data.ProdRepository
import com.kosign.multimodulehilt.data.model.City
import com.kosign.multimodulehilt.data.model.Reservation
import com.kosign.multimodulehilt.view.widget.ProgressDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val prodRepository: ProdRepository) : BaseViewModel() {

    private val _label = MutableLiveData<String>("Default vaule")
    val label : LiveData<String> = _label

    private val _dialog = MutableLiveData<Boolean>()
    val dialog = _dialog

    fun setTextLabel(lb : String){
        _label.value = lb
    }

    private val _reservations = MutableLiveData<List<Reservation>>()
    val reservations: LiveData<List<Reservation>> = _reservations

    private val _cities = MutableLiveData<List<City>>()
    val cities: LiveData<List<City>> = _cities

    init {
        Log.d(">>>", "Injection discovered")
    }

    fun queryCityList(query: String) {
        _dialog.value = true
        runOnIO {
            prodRepository.queryCities(query)
                .flowOn(Dispatchers.IO)
                .catch {
                    handleResult(false)
                    if (it.message?.toInt() == 500){
                        Log.d(">>>", "queryCityList: Internal server error")
                    }
                }
                .collect { r ->
                    onOperationSucceed(r)
                }
        }
    }

    fun getReservationList() {
        _dialog.value = true
        runOnIO {
            prodRepository.getReservation()
                .flowOn(Dispatchers.IO)
                .catch { handleResult(false) }
                .collect { r ->
                    backOnMain {
                        hideLoading()
                        _reservations.value = r
                    }
                }
        }
    }

    private suspend fun onOperationSucceed(obj: List<City>) {
        backOnMain {
            hideLoading()
            _dialog.value = false
            _cities.value = obj
            _label.value = "Text from Observer"
            Log.d(">>>", "response >> ${cities.value}")
        }
    }

}