package com.example.snapup_android.Homepage.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "我是一个抢票软件"
    }
    val text: LiveData<String> = _text
}