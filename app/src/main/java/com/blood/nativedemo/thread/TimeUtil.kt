package com.blood.nativedemo.thread

import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author: cgz
 * @Email: caiguangzu.cgz@alibaba-inc.com
 * @CreateDate: 2021/10/13 15:53
 * @Description:
 */
object TimeUtil {

    fun curTime(): String {
        return SimpleDateFormat("HH:mm:ss").format(Date())
    }

}