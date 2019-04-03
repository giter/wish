package com.wish.common.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created on 2017/8/7.
 */

public class LockUtil {
    public static Lock goodsLock = new ReentrantLock();
}
