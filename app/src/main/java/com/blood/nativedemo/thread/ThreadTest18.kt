package com.blood.nativedemo.thread

import android.os.AsyncTask
import android.os.SystemClock
import android.util.Log

/**
 * @Author: cgz
 * @Email: caiguangzu.cgz@alibaba-inc.com
 * @CreateDate: 2021/10/12 17:39
 * @Description:
 */
object ThreadTest18 {

    @JvmStatic
    fun main(args: Array<String>) {
//        for (i in 1..10) {
//            MyTask().execute(i)
//        }
        MyTask().execute(0)
        Thread.sleep(10000)
    }
}

class MyTask : AsyncTask<Int, Int, String>() {

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: Int?): String {
        Thread.sleep(2000)
        Log.i("MyTask", "doInBackground: $params")
        return "over"
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
    }

}