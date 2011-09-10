package clan.blue.droid.barium.tablet.sensor.detect;

import com.codeswimmer.android.dispatch.IntentBroadcaster;
import com.codeswimmer.android.dispatch.MessageDispatcher;
import com.codeswimmer.common.contract.Contract;

import clan.blue.droid.barium.tablet.BariumTabletApp;
import clan.blue.droid.common.android.sensor.SensorType;
import android.content.Context;
import android.hardware.Sensor;
import android.os.AsyncTask;
import android.util.Log;

public class DetectAvailableSensorsTask extends AsyncTask<Void, Void, Void> implements IntentBroadcaster {
    private static final String TAG = DetectAvailableSensorsTask.class.getSimpleName();
    private Sensor defaultSensor;
    private OnSensorAvailableListener sensorAvailableListener;
    private Context context;
    
    public DetectAvailableSensorsTask(Context context) {
        this.context = context;
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
        Sensor defaultSensor = BariumTabletApp.getSensorManager().getDefaultSensor(type);
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
