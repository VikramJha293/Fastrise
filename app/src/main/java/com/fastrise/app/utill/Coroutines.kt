package com.fastrise.app.utill

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object Coroutines{
    //Unit in kotlin is similar to void in java
    fun main(work:suspend (()-> Unit)) =
        CoroutineScope(Dispatchers.Main).launch {
            work()
        }
    fun mainIo(work:suspend (()-> Unit)) =
        CoroutineScope(Dispatchers.IO).launch {
            work()
        }
    fun<T: Any> ioThenMain(work: suspend (() -> T?), callback: ((T?)->Unit)) =
        CoroutineScope(Dispatchers.Main).launch {
            val data = CoroutineScope(Dispatchers.IO).async  rt@{
                return@rt work()
            }.await()
            callback(data)
        }
}