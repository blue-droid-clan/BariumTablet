package clan.blue.droid.barium.tablet;

import java.util.Collection;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import clan.blue.droid.barium.tablet.data.AvailableSensors;
import clan.blue.droid.barium.tablet.sensor.detect.AvailableSensorsDetectedIntent;
import clan.blue.droid.barium.tablet.sensor.fragment.BasicSensorDataFragment;
import clan.blue.droid.common.android.sensor.SensorType;

import com.codeswimmer.android.dispatch.IntentReceiver;
import com.codeswimmer.android.dispatch.MessageDispatcher;

public class BariumTabletActivity extends Activity {
//    @SuppressWarnings("unused")
    private static final String TAG = BariumTabletActivity.class.getSimpleName();
    private static final int INVALID_CONTAINER_VIEW_ID = -1;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        BariumTabletApp.initialize(this);
        
        setupAvailableSensorsDetectedReceiver();
        detectAvailableSensors();
    }
    
    private void setupAvailableSensorsDetectedReceiver() {
        String actionName = AvailableSensorsDetectedIntent.INTENT_ACTION;
        AvailableSensorsDetectedReceiver receiver = new AvailableSensorsDetectedReceiver();
        MessageDispatcher.addReceiver(actionName, receiver);
    }
    
    private void detectAvailableSensors() {
        AvailableSensors.detectAvailableSensors(getApplicationContext());
    }
    
    private void onAvailableSensorsDetected() {
        createSensorDisplays();
        removeEmptySensorDisplays();
        AvailableSensors.registerSensorListeners();
    }
    
    private void removeEmptySensorDisplays() {
        List<Integer> missingSensorDisplayResIds = BariumTabletApp.getMissingSensorDisplays();
        View mainView = findViewById(R.id.mainViewAvailableSensors);
        for (Integer resId : missingSensorDisplayResIds) {
            View view = mainView.findViewById(resId);
            if (view != null)
                view.setVisibility(View.GONE);
        }
    }
    
    private void createSensorDisplays() {
        int numSensorsAvailable = AvailableSensors.getAvailableSensorsCount();
        Log.i(TAG, String.format("createSensorDisplays() - numSensorsAvailable: %d", numSensorsAvailable));
        
        Collection<Sensor> sensors = AvailableSensors.getAvailableSensorsAsCollection();
        int count = 0;
        for (Sensor sensor : sensors) {
            Log.i(TAG, String.format("    %3d: %s", count, sensor.getName()));
            createAndAddSensorDisplay(sensor);
            count++;
        }
    }
    
    private void createAndAddSensorDisplay(Sensor sensor) {
        SensorType sensorType = SensorType.fromTypeCode(sensor.getType());
        
        BasicSensorDataFragment fragment = new BasicSensorDataFragment(sensor);
        updateSensorDataFragmentHumanReadableNameDisplay(sensorType, fragment);
        addSensorDataFragment(sensor, sensorType, fragment);
        BariumTabletApp.SensorFragmentManager.addSensorFragment(sensorType, fragment);
    }
    
    private void updateSensorDataFragmentHumanReadableNameDisplay(SensorType sensorType, BasicSensorDataFragment fragment) {
        int resId = determineSensorHumanReadableName(sensorType);
        fragment.setHumanReadableNameResId(resId);
    }
    
    private void addSensorDataFragment(Sensor sensor, SensorType sensorType, BasicSensorDataFragment fragment) {
        String tag = createFragmentTag(sensorType);
        int containerViewId = determineSensorContainerResId(sensorType);
        
        if (containerViewId == INVALID_CONTAINER_VIEW_ID)
            // TODO: throw ContainerNotAddedException.InvalidContainerViewId here
            return;
        
        BariumTabletApp.displayFragment(containerViewId, fragment, tag);
    }
    
    private int determineSensorContainerResId(SensorType sensorType) {
        int resId = BariumTabletApp.sensorDisplayContainerResIdForSensorType(sensorType);
        return resId;
    }
    
    private String createFragmentTag(SensorType sensorType) {
        String tag = sensorType.name();
        return tag;
    }
    
    private int determineSensorHumanReadableName(SensorType sensorType) {
        int resId = BariumTabletApp.sensorHumanReadableNameResIdForSensorType(sensorType);
        return resId;
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        AvailableSensors.unregisterSensorListeners();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        AvailableSensors.registerSensorListeners();
    }
    
    private class AvailableSensorsDetectedReceiver implements IntentReceiver {
        @Override public Context getContext() { return getApplicationContext(); }
        @Override public void onMessageReceived(Intent intent) { onAvailableSensorsDetected(); }
    }
}