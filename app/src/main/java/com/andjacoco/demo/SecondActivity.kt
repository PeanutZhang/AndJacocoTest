package com.andjacoco.demo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random

class SecondActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        secondTest()

        findViewById<TextView>(R.id.tv_second).setOnClickListener({
            Log.i("tag", "log")
        })
        SecondHello().sayHello()
//        ThirdHello().sayHello()
        FourthHello().sayHello()

        findViewById<Button>(R.id.btn_fk).setOnClickListener {
            val fk = Random.nextInt(100)
            Log.i("tag", "fk random: $fk")

            if(fk < 10){
                Log.i("tag", "fk < 10")

            }else if(fk < 50){
                Log.i("tag", "fk < 50")

            }else if(fk < 80){
                Log.i("tag", "fk < 80")
            }else {
                Log.i("tag", "log  ff>>> "+fk)
            }
        }
    }

    fun secondTest() {
        Log.i("tag", "asdasd")
    }
}