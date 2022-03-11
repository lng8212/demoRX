package com.example.demorx

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.simpleName
    private val footballPlayersObservable: Observable<String> =
        Observable.just("Messi", "Ronaldo", "Modric", "Salah", "Mbappe")

    private val footballPlayersObserver: Observer<String?> = getFootballPlayersObserver()
    private lateinit var disposable: Disposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUp()
    }


    private fun setUp(){
        footballPlayersObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(footballPlayersObserver)
    }

    @JvmName("getFootballPlayersObserver1")
    private fun getFootballPlayersObserver(): Observer<String?> {
        return object : Observer<String?> {
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribe")
                disposable = d
            }

            override fun onNext(s: String) {
                Log.d(TAG, "Name: $s")
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError: " + e.message)
            }

            override fun onComplete() {
                Log.d(TAG, "All items are emitted!")
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}