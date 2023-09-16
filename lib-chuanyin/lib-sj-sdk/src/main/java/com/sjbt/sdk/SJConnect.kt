package com.sjbt.sdk

import android.text.TextUtils
import com.base.sdk.`interface`.AbWmConnect
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe

class SJConnect : AbWmConnect() {

    private var connectEmitter: ObservableEmitter<Boolean>? = null

    override fun connect(address: String) {

        if(!TextUtils.isEmpty(address)){
            connectEmitter?.onNext(true)
        }
    }

    override fun disconnect(): Boolean {
        TODO("Not yet implemented")
        connectEmitter?.onNext(false)
    }

    override fun reset() {
        TODO("Not yet implemented")
        connectEmitter?.onNext(false)
    }

    override fun isConnect(): Observable<Boolean> {
        return Observable.create<Boolean>(object : ObservableOnSubscribe<Boolean> {
            @Throws(Throwable::class)
            override fun subscribe(emitter: ObservableEmitter<Boolean>) {
                connectEmitter = emitter
            }
        })
    }
}