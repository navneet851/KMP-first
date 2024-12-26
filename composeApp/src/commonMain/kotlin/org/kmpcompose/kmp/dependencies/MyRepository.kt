package org.kmpcompose.kmp.dependencies

interface MyRepository {
    fun printName() : String
}

class MyRepositoryImpl(
    private val db : DbConnect
) : MyRepository{
    override fun printName(): String {
        return "Navneet Yadav"
    }
}