package com.szg.utils;

public interface ILock {
    boolean tryLock(long timeoutSec);

    void unlock();
}
