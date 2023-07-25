package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btn_start : Button
    private lateinit var btn_reset : Button
    private lateinit var tv_minute : TextView
    private lateinit var tv_second : TextView
    private lateinit var tv_millisecond : TextView

    private var isRunning = false
    private var timer : Timer? = null
    private var time = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start = findViewById(R.id.btn_start)
        btn_reset = findViewById(R.id.btn_reset)
        tv_minute = findViewById(R.id.tv_minute)
        tv_second = findViewById(R.id.tv_second)
        tv_millisecond = findViewById(R.id.tv_millisecond)

        btn_start.setOnClickListener(this)
        btn_reset.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_start -> {
                if(isRunning){
                    pause()
                }else{
                    start()
                }
            }
            R.id.btn_reset -> {
                reset()
            }
        }
    }
    private fun start(){
        btn_start.text = getString(R.string.btn_pause)
        btn_start.setBackgroundColor(getColor(R.color.btn_pause))
        isRunning = true


        timer = timer(period = 10){
            // timer 함수는 항상 백그라운드 스레드에서 실행됨
            // period는 millisecond Unit임
            // 1000ms = 1s
            // 0.01s 마다 time에 1 증가
            time++

            val milli_second = time%100
            val second = (time % 6000) / 100
            val minute = time  / 6000



            runOnUiThread {
                // timer 함수가 백그라운드 스레드에서 실행되더라도 해당 함수는 메인 스레드에서 실행되게 하는 것
                if(isRunning){
                    tv_millisecond.text = if(milli_second < 10 ) ".0${milli_second}" else ".${milli_second}"
                    tv_second.text = if(second<10) ":0${second}" else ":${second}"
                    tv_minute.text = if(minute<10) "0${minute}" else "${minute}"
                }


            }
        }

    }

    private fun pause(){
        btn_start.text = getString(R.string.btn_start)
        btn_start.setBackgroundColor(getColor(R.color.btn_start))

        isRunning = false
        timer?.cancel()
    }

    private fun reset(){
        timer?.cancel()
        btn_start.text = getString(R.string.btn_start)
        btn_start.setBackgroundColor(getColor(R.color.btn_start))
        isRunning = false

        time = 0

        tv_minute.text = "00"
        tv_second.text = ":00"
        tv_millisecond.text = ",00"
    }

}