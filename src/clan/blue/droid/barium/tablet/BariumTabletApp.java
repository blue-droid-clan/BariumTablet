package clan.blue.droid.barium.tablet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import clan.blue.droid.barium.tablet.data.AvailableSensors;
import clan.blue.droid.barium.tablet.sensor.fragment.BasicSensorDataFragment;
import clan.blue.droid.common.android.sensor.SensorType;

import com.codeswimmer.android.fragments.FragmentHelper;

@SuppressWarnings("serial") 
public class BariumTabletApp {
    @SuppressWarnings("unused")
    private static final String TAG = BariumTabletApp.class.getSimpleName();
    private static final Integer[] UNKNOWN_REALTIME_RES_IDS = new Integer[] {};
    
    private static final Map<SensorType, Integer> sensorDisplaysMap = new ConcurrentHashMap<SensorType, Integer>();
    private static final Map<SensorType, Integer> sensorHumanReadableNamesMap = new ConcurrentHashMap<SensorType, Integer>();
    private static final Map<SensorType, Integer[]> sensorRealtimeLabelsMap = new ConcurrentHashMap<SensorType, Integer[]>();
    
    private static final Integer[] xyzResIds = { R.string.x_axis, R.string.y_axis, R.string.z_axis, };
    
    private static final Integer[] accelerometerResIds = xyzResIds;
    private static final Integer[] gravityResIds = xyzResIds;
    private static final Integer[] gyroscopeResIds = xyzResIds;
    private static final Integer[] lightResIds = { R.string.ambient_light, null, null };
    private static final Integer[] linearAcceleartionResIds = xyzResIds;
    private static final Integer[] magneticFieldResIds = xyzResIds;
    private static final Integer[] orientationResIds = xyzResIds;
    private static final Integer[] proximityResIds = xyzResIds;
    private static final Integer[] pressureResIds = xyzResIds;
    private static final Integer[] rotationVectorResIds = xyzResIds;
    private static final Integer[] temperatureResIds = xyzResIds; // Not sure about this one
    
    private static SensorManager sensorManager;
    private static FragmentHelper fragmentHelper;
    
    public static final void initialize(Activity activity) {
        initializeMaps();
        initilizeSensorManager(activity.getApplicationContext());
        initializeFragmentHelper(activity.getFragmentManager());
    }
    
    private static final void initializeMaps() {
        initializeSensorDisplaysMap();
        initializeSensorHumanReadableNamesMap();
        initializeSensorRealtimeLabelsMap();
    }
    
    private static final void initilizeSensorManager(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }
    
    private static final void initializeFragmentHelper(FragmentManager fm) {
        fragmentHelper = new FragmentHelper(fm);
    }
    
    private static final void initializeSensorDisplaysMap() {
        sensorDisplaysMap.put(SensorType.Unknown, R.id.unknown_sensor_container);
        sensorDisplaysMap.put(SensorType.Accelerometer, R.id.accelerometer_container);
        sensorDisplaysMap.put(SensorType.Gravity, R.id.gravity_container);
        sensorDisplaysMap.put(SensorType.Gyroscope, R.id.gyroscope_container);
        sensorDisplaysMap.put(SensorType.Light, R.id.light_container);
        sensorDisplaysMap.put(SensorType.LinearAcceleration, R.id.linear_acceleration_container);
        sensorDisplaysMap.put(SensorType.MagneticField, R.id.magnetic_field_container);
        sensorDisplaysMap.put(SensorType.Orientation, R.id.orientation_container);
        sensorDisplaysMap.put(SensorType.Pressure, R.id.pressure_container);
        sensorDisplaysMap.put(SensorType.Proximity, R.id.proximity_container);
        sensorDisplaysMap.put(SensorType.RotationVector, R.id.rotation_vector_container);
        sensorDisplaysMap.put(SensorType.Temperature, R.id.temperature_container);
    }
    
    private static final void initializeSensorHumanReadableNamesMap() {
        sensorHumanReadableNamesMap.put(SensorType.Accelerometer, R.string.sensor_name_accelerometer);
        sensorHumanReadableNamesMap.put(SensorType.Gravity, R.string.sensor_name_gravity);
        sensorHumanReadableNamesMap.put(SensorType.Gyroscope, R.string.sensor_name_gyroscope);
        sensorHumanReadableNamesMap.put(SensorType.Light, R.string.sensor_name_light);
        sensorHumanReadableNamesMap.put(SensorType.LinearAcceleration, R.string.sensor_name_linearAcceleration);
        sensorHumanReadableNamesMap.put(SensorType.MagneticField, R.string.sensor_name_magneticField);
        sensorHumanReadableNamesMap.put(SensorType.Orientation, R.string.sensor_name_orientation);
        sensorHumanReadableNamesMap.put(SensorType.Pressure, R.string.sensor_name_pressure);
        sensorHumanReadableNamesMap.put(SensorType.Proximity, R.string.sensor_name_proximity);
        sensorHumanReadableNamesMap.put(SensorType.RotationVector, R.string.sensor_name_rotationVector);
        sensorHumanReadableNamesMap.put(SensorType.Temperature, R.string.sensor_name_temperature);
    }
    
    private static final void initializeSensorRealtimeLabelsMap() {
        sensorRealtimeLabelsMap.put(SensorType.Accelerometer, accelerometerResIds);
        sensorRealtimeLabelsMap.put(SensorType.Gravity, gravityResIds);
        sensorRealtimeLabelsMap.put(SensorType.Gyroscope, gyroscopeResIds);
        sensorRealtimeLabelsMap.put(SensorType.Light, lightResIds);
        sensorRealtimeLabelsMap.put(SensorType.LinearAcceleration, linearAcceleartionResIds);
        sensorRealtimeLabelsMap.put(SensorType.MagneticField, magneticFieldResIds);
        sensorRealtimeLabelsMap.put(SensorType.Orientation, orientationResIds);
        sensorRealtimeLabelsMap.put(SensorType.Pressure, pressureResIds);
        sensorRealtimeLabelsMap.put(SensorType.Proximity, proximityResIds);
        sensorRealtimeLabelsMap.put(SensorType.RotationVector, rotationVectorResIds);
        sensorRealtimeLabelsMap.put(SensorType.Temperature, temperatureResIds);
    }
    
    public static Integer sensorDisplayContainerResIdForSensorType(SensorType sensorType) {
        Integer result = sensorDisplaysMap.get(sensorType);
        if (result == null)
            result = R.id.unknown_sensor_container;
        return result;
    }
    
    public static Integer sensorHumanReadableNameResIdForSensorType(SensorType sensorType) {
        Integer result = sensorHumanReadableNamesMap.get(sensorType);
        if (result == null)
            result = R.string.sensor_name_unknown;
        return result;
    }
    
    public static Integer[] sensorRealtimeLabelsResIdsForSensorType(SensorType sensorType) {
        Integer[] result = sensorRealtimeLabelsMap.get(sensorType);
        if (result == null)
            result = UNKNOWN_REALTIME_RES_IDS;
        return result;
    }
    
    public static final SensorManager getSensorManager() throws RuntimeException {
        throwErrorIfSensorManagerNotInitialized();
        return sensorManager;
    }
    
    public static final void displayFragment(int containerViewId, Fragment fragment, String tag) {
        throwErrorIfFragmentManagerNotInitialized();
        fragmentHelper.showFragment(containerViewId, fragment, tag);
    }
    
    private static Collection<Integer> availableSensorTypeCodes;
    
    public static final List<Integer> getMissingSensorDisplays() {
        List<Integer> resIds = new ArrayList<Integer>();
        
        List<SensorType> missingSensors = retrieveMissingSensors();
        for (SensorType sensorType : missingSensors) {
            Integer resId = sensorDisplaysMap.get(sensorType);
            if (resId != null)
                resIds.add(resId);
        }
        
        return resIds;
    }
    
    private static final List<SensorType> retrieveMissingSensors() {
        retrieveAvailableSensors();
        List<SensorType> missingSensors = new ArrayList<SensorType>();
        
        for (SensorType sensorType : SensorType.values())
            if (isaMissingSensor(sensorType))
                missingSensors.add(sensorType);
        
        recycleAvailableSensorTypeCodes();
        return missingSensors;
    }
    
    private static final void retrieveAvailableSensors() {
        availableSensorTypeCodes = getAvailableSensorTypeCodes();
    }
    
    private static final boolean isaMissingSensor(SensorType sensorType) {
        return !availableSensorTypeCodes.contains(sensorType.getTypeCode());
    }
    
    private static final Collection<Integer> getAvailableSensorTypeCodes() {
        Collection<Integer> typeCodes = new ArrayList<Integer>();
        Collection<Sensor> availableSensors = AvailableSensors.getAvailableSensorsAsCollection();
        for (Sensor availableSensor : availableSensors)
            typeCodes.add(availableSensor.getType());
        return typeCodes;
    }
    
    private static final void recycleAvailableSensorTypeCodes() {
        availableSensorTypeCodes.clear();
        availableSensorTypeCodes = null;
    }
    
    private static final void throwErrorIfSensorManagerNotInitialized() throws RuntimeException {
        if (sensorManager == null)
            throw new RuntimeException("sensorManager must be initialized by calling initializeSensorManager(Context);");
    }
    
    private static final void throwErrorIfFragmentManagerNotInitialized() throws RuntimeException {
        if (fragmentHelper == null)
            throw new RuntimeException("fragmentHelper must be initialized by calling initializeFragmentHelper(FragmentManager);");
    }
    
    private static class SensorFragmentMap extends ConcurrentHashMap<SensorType, BasicSensorDataFragment> {}
    
    public static final class SensorFragmentManager {
        private static final Map<SensorType, BasicSensorDataFragment> availabileSensorFragments = new SensorFragmentMap();
        
        public static final void addSensorFragment(SensorType sensorType, BasicSensorDataFragment fragment) {
            if (sensorType == null || fragment == null)
                return;
            availabileSensorFragments.put(sensorType, fragment);
        }
        
        public static final BasicSensorDataFragment removeSensorFragment(SensorType sensorType) {
            if (sensorType == null)
                return null;
            return availabileSensorFragments.remove(sensorType);
        }
        
        public static final BasicSensorDataFragment getSensorFragment(SensorType sensorType) {
            if (sensorType == null)
                return null;
            return availabileSensorFragments.get(sensorType);
        }
        
        public static final Collection<BasicSensorDataFragment> getSensorFragments() {
            return availabileSensorFragments.values();
        }
    }
}
