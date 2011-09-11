package clan.blue.droid.barium.tablet.data;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;
import clan.blue.droid.barium.tablet.BariumTabletApp;
import clan.blue.droid.barium.tablet.sensor.detect.DetectAvailableSensorsTask;
import clan.blue.droid.barium.tablet.sensor.detect.OnSensorAvailableListener;
import clan.blue.droid.barium.tablet.sensor.fragment.BasicSensorDataFragment;
import clan.blue.droid.common.android.sensor.SensorType;

public class AvailableSensors {
    private static final String TAG = AvailableSensors.class.getSimpleName();
    private static final Map<SensorType, Sensor> availableSensors = new ConcurrentHashMap<SensorType, Sensor>();
    private static final SensorAvailableListener sensorAvailableListener = new SensorAvailableListener();
    
    public static void detectAvailableSensors(Context context) {
        launchSensorDetectionTask(context);
    }
    
    private static void launchSensorDetectionTask(Context context) {
        DetectAvailableSensorsTask task = new DetectAvailableSensorsTask(context);
        task.setOnSensorAvailableListener(sensorAvailableListener);
        task.execute();
    }
    
    private static void addSensor(SensorType sensorType, Sensor sensor) {
        if (isValidSensor(sensorType, sensor)) {
            availableSensors.put(sensorType, sensor);
            Log.i(TAG, String.format("addSensor() - %s", sensorType.name()));
        }
    }
    
    private static boolean isValidSensor(SensorType sensorType, Sensor sensor) {
        return sensorType != null && sensorType.isNotUnknown() && sensor != null;
    }
    
    public static int getAvailableSensorsCount() { 
        return availableSensors.size(); 
    }
    
    public static Collection<Sensor> getAvailableSensorsAsCollection() {
        return availableSensors.values();
    }
    
    public static boolean sensorsAreAvailable() {
        return availableSensors.isEmpty() == false;
    }
    
    public static void registerSensorListeners() {
        if (sensorsAreAvailable())
            for (Sensor sensor : getAvailableSensorsAsCollection())
                registerSensorListener(sensor);
    }
    
    private static boolean registerSensorListener(Sensor sensor) {
        SensorType sensorType = SensorType.fromTypeCode(sensor.getType());
        BasicSensorDataFragment fragment = BariumTabletApp.SensorFragmentManager.getSensorFragment(sensorType);
        if (fragment == null)
            return false;
        boolean result = BariumTabletApp.getSensorManager().registerListener(fragment, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        return result;
    }

    public static void unregisterSensorListeners() {
        if (sensorsAreAvailable())
            for (Sensor sensor : getAvailableSensorsAsCollection())
                unregisterSensorListener(sensor);
    }
    
    private static void unregisterSensorListener(Sensor sensor) {
        SensorType sensorType = SensorType.fromTypeCode(sensor.getType());
        BasicSensorDataFragment fragment = BariumTabletApp.SensorFragmentManager.getSensorFragment(sensorType);
        if (fragment == null)
            return;
        
        BariumTabletApp.getSensorManager().unregisterListener(fragment);
    }
    
    private static class SensorAvailableListener implements OnSensorAvailableListener {
        @Override
        public void onSensorAvailable(SensorType sensorType, Sensor sensor) {
            addSensor(sensorType, sensor);
        }
    }
    
    // Singleton boilerplate //////////////////////////////////////////////////////////////////////////////////////////
    public static AvailableSensors getInstance() { return SingletonHolder.INSTANCE; }
    private AvailableSensors() {}
    private static class SingletonHolder {
        private static final AvailableSensors INSTANCE = new AvailableSensors();
    }
}
