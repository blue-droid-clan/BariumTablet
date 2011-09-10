package clan.blue.droid.barium.tablet.sensor.detect;

import android.hardware.Sensor;
import clan.blue.droid.common.android.sensor.SensorType;

public interface OnSensorAvailableListener {
    public void onSensorAvailable(SensorType sensorType, Sensor sensor);
}
