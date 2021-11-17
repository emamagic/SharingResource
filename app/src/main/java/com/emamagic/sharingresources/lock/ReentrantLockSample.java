package com.emamagic.sharingresources.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockSample {

    Lock lock = new ReentrantLock();
    int counter = 0;

    public void perform() {
        lock.lock();
        // Try-finally for avoiding deadlock
        try {
            // Critical section here
            counter++;
        } finally {
            lock.unlock();
        }
    }

    public void performTryLock() throws InterruptedException {
        boolean isLockAcquired = lock.tryLock(1, TimeUnit.SECONDS);

        if(isLockAcquired) {
            try {
                //Critical section here
            } finally {
                lock.unlock();
            }
        }
    }

}
