package com.vlados.rxjavaapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var dispose: Disposable? = null
    var timerRunning = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.textView)
        val textView2 = findViewById<TextView>(R.id.textView2)
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            if (timerRunning) {
                dispose?.dispose()
                timerRunning = false
            } else {
                dispose = timerOn()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d("RX", "Таймер сработал")
                        textView.text = "$it"
                        if (it % 5 == 0L) {
                            textView2.text = "$it"
                        }
                    })
                timerRunning = true
            }
        }

        dispose?.dispose()
        dispose = timerOn()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("RX", "Таймер сработал")
                textView.text = "$it"
                if (it % 5 == 0L) {
                    textView2.text = "$it"
                }
            })
    }

    fun timerOn(): Flowable<Long> {
        return Flowable.interval(1L, 1L, TimeUnit.SECONDS)
    }
}