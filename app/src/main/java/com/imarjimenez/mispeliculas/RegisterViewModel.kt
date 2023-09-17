package com.imarjimenez.mispeliculas
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String>
        get() = _selectedDate

    private val _selectedCity = MutableLiveData<String>()
    val selectedCity: LiveData<String>
        get() = _selectedCity

    fun setSelectedDate(date: String) {
        _selectedDate.value = date
    }

    fun setSelectedCity(city: String) {
        _selectedCity.value = city
    }
}