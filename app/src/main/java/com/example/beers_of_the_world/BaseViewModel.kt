package com.example.beers_of_the_world

import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beers_of_the_world.repositories.BaseRepository


abstract class BaseViewModel(private val repository: BaseRepository) : ViewModel() {

    private val TAG = BaseViewModel::class.java.name

    private val _notUploadedMediaListResponse = MutableLiveData<Event<Resource<List<MediaStore.Audio.Media>>>>()
    val notUploadedMediaListResponse get() = _notUploadedMediaListResponse

    private val _uploadedMediaListResponse = MutableLiveData<Event<Resource<List<MediaStore.Audio.Media>>>>()
    val uploadedMediaListResponse get() = _uploadedMediaListResponse

    private val _uploadedMediaResponse = MutableLiveData<Event<Resource<MediaStore.Audio.Media>>>()
    val uploadedMediaResponse get() = _uploadedMediaResponse

    private val _confirmMediaResponse = MutableLiveData<Event<Resource<Any>>>()
    val confirmMediaResponse get() = _confirmMediaResponse

    private val _irdPhotoUploadResponse = MutableLiveData<Event<Resource<BeerResponse>>>()
    val irdPhotoUploadResponse: LiveData<Event<Resource<BeerResponse>>> get() = _irdPhotoUploadResponse


}