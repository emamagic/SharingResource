package com.emamagic.sharingresources.lock;

import android.os.Build;
import androidx.annotation.RequiresApi;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

@RequiresApi(api = Build.VERSION_CODES.N)
// java 8
public class StampedLockSample {

    Map<String,String> map = new HashMap<>();
    private StampedLock lock = new StampedLock();

    public void put(String key, String value){
        long stamp = lock.writeLock();
        try {
            map.put(key, value);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public String get(String key) throws InterruptedException {
        long stamp = lock.readLock();
        try {
            return map.get(key);
        } finally {
            lock.unlockRead(stamp);
        }
    }

    public String readWithOptimisticLock(String key) {
        long stamp = lock.tryOptimisticRead();
        String value = map.get(key);

        if(!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                return map.get(key);
            } finally {
                lock.unlock(stamp);
            }
        }
        return value;
    }

}
