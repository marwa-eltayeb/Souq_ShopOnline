package com.marwaeltayeb.souq.utils;

public class FlagsManager {

    private static FlagsManager instance = null;

    private boolean isHistoryDeleted = false;
    private boolean isActivityRunning = false;

    public static FlagsManager getInstance() {
        if (instance == null)
            instance = new FlagsManager();

        return instance;
    }

    public boolean isHistoryDeleted() {
        return isHistoryDeleted;
    }

    public void setHistoryDeleted(boolean historyDeleted) {
        isHistoryDeleted = historyDeleted;
    }

    public boolean isActivityRunning() {
        return isActivityRunning;
    }

    public void setActivityRunning(boolean activityRunning) {
        isActivityRunning = activityRunning;
    }
}



