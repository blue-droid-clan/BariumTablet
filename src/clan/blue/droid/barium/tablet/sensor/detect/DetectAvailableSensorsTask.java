package clan.blue.droid.barium.tablet.sensor.detect;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.util.Log;
import clan.blue.droid.barium.tablet.BariumTabletApp;
import clan.blue.droid.common.android.sensor.SensorType;

import com.codeswimmer.android.dispatch.IntentBroadcaster;
import com.codeswimmer.android.dispatch.MessageDispatcher;
import com.codeswimmer.common.contract.Contract;

public class DetectAvailableSensorsTask extends AsyncTask<Void, Void, Void> implements IntentBroadcaster {
    private static final String TAG = DetectAvailableSensorsTask.class.getSimpleName();
    private final SensorManager sensorManager;
    private Context context;
    private OnSensorAvailableListener sensorAvailableListener;
    private Sensor defaultSensor;
    
    public DetectAvailableSensorsTask(Context context) {
        this.context = context;
        sensorManager = BariumTabletApp.getSensorManager();
    }
    
    @Override
    protected Void doInBackground(Void... params) {
        detectAvailableSensors();
        return null;
    }
    
    private void detectAvailableSensors() {
        determineSensorsThatArePresent();
    }
    
    private void determineSensorsThatArePresent() {
        Log.i(TAG, String.format("detectDevices() - %s", ""));
        for (SensorType sensorType : SensorType.values())
            if (sensorAdded(sensorType))
                notifySensorAvailable(sensorType);
    }
    
    private boolean sensorAdded(SensorType sensorType) {
        defaultSensor = getDefaultSensorForType(sensorType);
        return (defaultSensor != null);
    }
    
    private void notifySensorAvailable(SensorType sensorType) {
        Contract.preconditionNotNull(defaultSensor); // Method assumes defaultSensor has already been null checked
        
        if (sensorAvailableListener != null) 
            sensorAvailableListener.onSensorAvailable(sensorType, defaultSensor);
    }
    
    private Sensor getDefaultSensorForType(SensorType sensorType) {
        int type = sensorType.getTypeCode();
        Sensor defaultSensor = sensorManager.getDefaultSensor(type);
        return defaultSensor;
    }
    
    @Override
    protected void onPostExecute(Void result) {
        Log.i(TAG, String.format("onPostExecute()", ""));
        broadcastAvailableSensorsDetected();
    }
    
    private void broadcastAvailableSensorsDetected() {
        AvailableSensorsDetectedIntent intent = new AvailableSensorsDetectedIntent();
        MessageDispatcher.broadcastIntent(this, intent);
    }
    
    public void setOnSensorAvailableListener(OnSensorAvailableListener sensorAvailableListener) {
        this.sensorAvailableListener = sensorAvailableListener;
    }

    @Override
    public Context getContext() {
        return context;
    }
}
