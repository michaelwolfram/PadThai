package de.mwdevs.padthai.main;

import android.os.SystemClock;

class DelayProvider {
    private static final long INITIAL_DELAY = 1000;
    private static final long PERIODIC_DELAY = 5000;
    private final long mStartTime;

    DelayProvider() {
        mStartTime = SystemClock.uptimeMillis();
    }

    long getNextDelay() {
        long currentTime = SystemClock.uptimeMillis();
        long timeDiff = currentTime - mStartTime;
        long result;
        if (timeDiff <= INITIAL_DELAY) {
            result = INITIAL_DELAY - timeDiff;
        } else {
            result = mStartTime + INITIAL_DELAY + PERIODIC_DELAY * ((timeDiff - INITIAL_DELAY) / PERIODIC_DELAY + 1) - currentTime;
        }
        return result;
    }
}
