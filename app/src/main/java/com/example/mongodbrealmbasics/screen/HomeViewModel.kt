package com.example.mongodbrealmbasics.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mongodbrealmbasics.Model.Person
import com.example.mongodbrealmbasics.data.MongoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: MongoRepository
) : ViewModel() {

    var name= mutableStateOf("")
    var objectId= mutableStateOf("")
    val filtered= mutableStateOf(false)
    var data= mutableStateOf(emptyList<Person>())

    init {
        viewModelScope.launch {
            repo.getData().collect(){
            data.value=it
        }
        }
    }
    fun updateName(name:String){
        this.name.value=name
    }
    fun updateObjectId(id: String) {
        this.objectId.value=id
    }
    fun insertPerson(){
        viewModelScope.launch(Dispatchers.IO) {
            if (name.value.isNotEmpty()) {
                repo.insertPerson(person = Person().apply {
                    name=this@HomeViewModel.name.value
                })
            }
        }
    }
    fun deletePerson() {
        viewModelScope.launch {
            if (objectId.value.isNotEmpty()) {
                repo.deletePerson(id=ObjectId(hexString = objectId.value))
            }
        }
    }
    fun updatePerson() {
        viewModelScope.launch(Dispatchers.IO) {
            if (objectId.value.isNotEmpty()) {
                repo.updatePerson(person = Person().apply {
                    _id = ObjectId(hexString = this@HomeViewModel.objectId.value)
                    name = this@HomeViewModel.name.value
                })
            }
        }
    }
    fun filterData() {
        viewModelScope.launch {
            if (filtered.value) {
                repo.getData().collect(){
                    filtered.value=false
                    name.value=""
                    data.value=it
                }
            }else{
                repo.filterData(name=name.value).collect{
                    filtered.value=true
                    data.value=it
                }
            }
        }
    }
}