package org.kmpcompose.kmp.dependencies

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

fun createDataStore(path : () -> String) : DataStore<Preferences>{
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = {    path().toPath()    }
    )
}

internal const val DATASTORE_NAME = "pref.preferences_pb"