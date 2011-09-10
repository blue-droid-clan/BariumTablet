package com.codeswimmer.android.dispatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class MessageDispatcher {
    private static final String TAG = MessageDispatcher.class.getSimpleName();
    private static final Map<String, List<IntentReceiver>> receivers = new ConcurrentHashMap<String, List<IntentReceiver>>();
    private static final MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    
    public static void broadcastIntent(IntentBroadcaster broadcaster, Intent intent) {
        broadcast(broadcaster, intent);
    }
    
    public static void broadcastMessage(IntentBroadcaster broadcaster, String actionName) {
        broadcast(broadcaster, new Intent(actionName));
    }
    
    private static void broadcast(IntentBroadcaster broadcaster, Intent intent) {
        if (broadcaster == null || intent == null)
            return;
        
        Context context = broadcaster.getContext();
        if (contextIsNotValid(context))
            return;
        
        context.sendBroadcast(intent);
    }
    
    private static boolean contextIsNotValid(Context context) {
        boolean results = context == null;
        
        if (results)
            Log.e(TAG, "Context is null; unable to broadcast intent");
        
        return results;
    }

    public static void addReceiver(String actionName, IntentReceiver receiver) {
        registerWithSystemToReceivingBroadcastsFor(actionName, receiver);
        storeToReceiversMap(actionName, receiver);
    }
    
    private static void registerWithSystemToReceivingBroadcastsFor(String actionName, IntentReceiver receiver) {
        IntentFilter filter = new IntentFilter(actionName);
        receiver.getContext().registerReceiver(myBroadcastReceiver, filter);
    }
    
    private static void storeToReceiversMap(String actionName, IntentReceiver receiver) {
        List<IntentReceiver> actionReceivers = retrieveReceiversList(actionName);
        actionReceivers.add(receiver);
    }
    
    private static List<IntentReceiver> retrieveReceiversList(String actionName) {
        List<IntentReceiver> actionReceivers = receivers.get(actionName);
        if (actionReceivers == null) {
            actionReceivers = Collections.synchronizedList(new ArrayList<IntentReceiver>());
            receivers.put(actionName, actionReceivers);
        }
        return actionReceivers;
    }

    public static IntentReceiver removeReceiver(String actionName, IntentReceiver receiver) {
        // TODO: to be implemented
        return null;
    }
    
    private static void handleReceive(Context context, Intent intent) {
        String actionName = intent.getAction();
        Log.i(TAG, String.format("onReceive() - %s", actionName));
        
        List<IntentReceiver> actionReceivers = receivers.get(actionName);
        if (actionReceivers == null) {
            Log.i(TAG, "onReceive() - actionReceivers is null, returning");
            return;
        }
        
        for (IntentReceiver receiver : actionReceivers) {
            intent.putExtra("keith", "ermel");
            receiver.onMessageReceived(intent);
        }
    }
    
    public static final IntentFilter createIntentFilter(Intent intent) {
        return new IntentFilter(intent.getAction());
    }
    
    private static class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            handleReceive(context, intent);
        }
    }
    
//    private static class ReceiversMap extends ConcurrentHashMap<String, List<IntentReceiver>> {}
}
