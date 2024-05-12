package com.example.datastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CounterViewModel : ViewModel() {
    private val dataStore = createDataStore("counter_preferences")
    private val counterKey = preferencesKey<Int>("counter_key")
    private val _counter = MutableStateFlow(0)

    val counter: Flow<Int> = _counter

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            dataStore.data.map { preferences ->
                preferences[counterKey] ?: 0
            }.collect {
                _counter.value = it
            }
        }
    }

    fun incrementCounter() {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                val currentCounterValue = preferences[counterKey] ?: 0
                preferences[counterKey] = currentCounterValue + 1
            }
        }
    }
}
