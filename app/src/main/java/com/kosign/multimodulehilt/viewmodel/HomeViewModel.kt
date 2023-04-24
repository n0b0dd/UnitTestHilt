package com.kosign.multimodulehilt.viewmodel

import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kosign.multimodulehilt.data.ProdRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val prodRepository: ProdRepository) :BaseViewModel() {

    val updateText :MutableLiveData<String> = MutableLiveData()

    private val _text = MutableLiveData<String>()
    val text = _text

    fun setDataFromIntent(data : String){
        _text.value = "Data :> $data"
        updateText.value = data
    }

    fun updateDataToMain(){
        if (updateText.value?.isNotEmpty() == true){

        }
    }

    val weatherVisibility: MutableLiveData<Int> = MutableLiveData()

    val cityLiveData: MediatorLiveData<String> = MediatorLiveData()
    val weatherIconUrlLiveData: MutableLiveData<String> = MutableLiveData()
    val temperatureLiveData: MediatorLiveData<String> = MediatorLiveData()
    val weatherDescLiveData: MutableLiveData<String> = MutableLiveData()
    val humidityLiveData: MutableLiveData<String> = MutableLiveData()
    val updatedTimeLiveData: MutableLiveData<String> = MutableLiveData()

    fun checkCurrentWeather(city: String) {
        cityLiveData.value = city
        weatherVisibility.value = View.INVISIBLE
        runOnMain {
            prodRepository.currentWeather(city)
                .flowOn(Dispatchers.IO)
                .catch {
                    handleResult(false)
                }
                .collect {
                    hideLoading()
                    it?.let { obj ->
                        temperatureLiveData.value = obj.temp_c
                        weatherDescLiveData.value = obj.condition?.description
                        humidityLiveData.value = "Humidity: ${obj.humidity}"
                        updatedTimeLiveData.value = "Last updated: ${obj.last_updated}"
                        weatherIconUrlLiveData.value = "https:${obj.condition?.icon}"
                    }

                    Log.d(">>", "checkCurrentWeather: $it")
                    weatherVisibility.value = View.VISIBLE
                    handleResult(true)
                }
        }
    }

}