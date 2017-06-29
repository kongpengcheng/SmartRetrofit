package com.lizubing.smartcacheforretrofit2.kotlin

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.lizubing.smartcacheforretrofit2.R

/**
 * Created by Harry.Kong.
 * Time 2017/5/30.
 * Email kongpengcheng@aliyun.com.
 * Description:
 */
class KotlinTest : Activity() {
    val name1 = "harry"
    val number = 1
    val s: String = "test"
    val activity: Context = this
    var x1 = 5
    val a: Double = 5.2
    val b: Int = a.toInt()
    val c: Float = 5.2F
    val d: Int = c.toInt()
    val h = "me"
    val j = "it is sh"
    val k = "h length is ${h.length}"
    val arr1 = arrayOf(1, 2, 3)//封装C操作
    val arr2: IntArray = intArrayOf(1, 2, 3)//原声类型数组
    val arr3 = arrayOfNulls<Int>(5)

    val l = 4
    val m = 5
    // 作为表达式
    val n = if (l > m) l else m

    val arr5 = arrayOf(1, 2, 3, 4, 5)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        for (arr in arr5) {
            println(arr)
        }


    }

    override fun onStart() {
        super.onStart()
    }

    //inline fun <reified T: Any> Gson.fromJson(json): T = this.fromJson(json, T::class.java)
fun taansform(color:String):Int{
        return when(color){
            "red"->0
            "green"->1
            else -> throw  IllegalAccessException("无此类型")
        }



    }

}
