package org.kmpcompose.kmp.dependencies

interface MyRepository {
    fun printName() : String
}

class MyRepositoryImpl : MyRepository{
    override fun printName(): String {
        return "Navneet Yadav"
    }
}