package com.timothy.piece

import com.timothy.piece.data.SimpleData
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        val s = "good start and find skd"
        print("indexOf=${s.indexOf("start")}\n")
//        val data = SimpleData()
//        print("data filed=" + data.getFieldValue("arg"))
//        assertEquals(1, data.getFieldValue("name"))
    }
}