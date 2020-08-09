package com.batu.callstatistics.utility

import android.os.AsyncTask

/**
 * This class is for handling all the async progresses in this app
 */
class AsyncUtil(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        handler()
        return null
    }
}