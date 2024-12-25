package org.kmpcompose.kmp.dependencies

import androidx.lifecycle.ViewModel

class MyViewModel(
    private val myRepository: MyRepository
) : ViewModel(){
    fun getName() = myRepository.printName()
}