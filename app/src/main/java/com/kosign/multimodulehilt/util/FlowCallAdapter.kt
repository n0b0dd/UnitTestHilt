package com.kosign.multimodulehilt.util

import android.util.Log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.json.JSONObject
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @Author ChenXingYu
 * @Date 2020/4/9-15:21
 */
internal class FlowCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, Flow<R?>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): Flow<R?> {
        return callbackFlow {
            val started = AtomicBoolean(false)
            if (started.compareAndSet(false, true)) {
                call.enqueue(object : Callback<R> {
                    override fun onResponse(call: Call<R>, response: Response<R>) {
                        if (response.isSuccessful) {
                            val body = response.body()
                            if (body == null || response.code() == 204) {
                                cancel(CancellationException("HTTP status code: ${response.code()}"))
                            } else {
                                trySend(body).isSuccess
                                channel.close()
                            }
                        } else {
                            cancel(CancellationException(response.code().toString() ?: "unknown error"))
                        }
                    }

                    override fun onFailure(call: Call<R>, throwable: Throwable) {
                        Log.d(">>>", "onFailure: ${throwable.message}")
                        cancel(CancellationException("500"))
                    }
                })
            }
            awaitClose { call.cancel() }
        }
    }

    private fun errorMsg(response: Response<R>): String? {
        val msg = response.errorBody()?.string()
        return if (msg.isNullOrEmpty()) {
            response.message()
        } else {
            msg
        }
    }

}
