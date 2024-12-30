package org.kmpcompose.kmp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import org.kmpcompose.kmp.dependencies.DATASTORE_NAME
import org.kmpcompose.kmp.dependencies.createDataStore
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask


@OptIn(ExperimentalForeignApi::class)
fun createDataStore() : DataStore<Preferences>{
    return createDataStore {
        val directory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        requireNotNull(directory).path + "/$DATASTORE_NAME"
    }
}
