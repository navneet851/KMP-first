package org.kmpcompose.kmp


fun intReverse(number : Int) : Int{
    var num = number
    var rev = 0
    while (num > 0){
        val mod = num % 10
        rev = rev * 10 + mod
        num /= 10
    }
    return rev
}
