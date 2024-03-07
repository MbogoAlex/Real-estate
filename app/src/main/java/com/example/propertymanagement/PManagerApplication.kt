package com.example.propertymanagement

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.propertymanagement.SFServices.PManagerSFRepository
import com.example.propertymanagement.container.AppContainer
import com.example.propertymanagement.container.DefaultContainer

private const val SF_NAME = "PManagerSF"
private val Context.datastore: DataStore<Preferences> by preferencesDataStore(
    name = SF_NAME
)

class PManagerApplication: Application() {
    lateinit var container: AppContainer
    lateinit var pManagerSFRepository: PManagerSFRepository
    override fun onCreate() {
        super.onCreate()
        container = DefaultContainer(this)
        pManagerSFRepository = PManagerSFRepository(datastore)
    }
}