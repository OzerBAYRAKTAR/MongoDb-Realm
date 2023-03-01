package com.example.mongodbrealmbasics

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mongodbrealmbasics.screen.HomeScreen
import com.example.mongodbrealmbasics.screen.HomeViewModel
import com.example.mongodbrealmbasics.ui.theme.MongoDbRealmBasicsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MongoDbRealmBasicsTheme {
                val viewModel:HomeViewModel= hiltViewModel()
                val data by viewModel.data
                HomeScreen(
                    data = data,
                    filtered = viewModel.filtered.value,
                    name = viewModel.name.value,
                    objectId = viewModel.objectId.value,
                    onNameChanged = {viewModel.updateName(name =it)},
                    onObjectIdChanged = {viewModel.updateObjectId(id=it)},
                    onInsertClicked = { viewModel.insertPerson() },
                    onUpdateClicked = { viewModel.updatePerson() },
                    onDeleteClicked = { viewModel.deletePerson() },
                    onFilterClicked = { viewModel.filterData() }
                )
                }

            }
        }
    }