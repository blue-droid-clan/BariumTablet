package com.codeswimmer.android.dispatch;

import android.content.Context;
import android.content.Intent;

public interface IntentReceiver {
    public Context getContext();
    public void onMessageReceived(Intent intent);
}
