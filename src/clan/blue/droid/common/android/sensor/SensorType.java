package clan.blue.droid.common.android.sensor;

import android.hardware.Sensor;

public enum SensorType {
    Unknown(Integer.MIN_VALUE),
    Accelerometer(Sensor.TYPE_ACCELEROMETER),
    Gravity(Sensor.TYPE_GRAVITY),
    Gyroscope(Sensor.TYPE_GYROSCOPE),
    Light(Sensor.TYPE_LIGHT),
    LinearAcceleration(Sensor.TYPE_LINEAR_ACCELERATION),
    MagneticField(Sensor.TYPE_MAGNETIC_FIELD),
    Orientation(Sensor.TYPE_ORIENTATION),
    Pressure(Sensor.TYPE_PRESSURE),
    Proximity(Sensor.TYPE_PROXIMITY),
    RotationVector(Sensor.TYPE_ROTATION_VECTOR),
    Temperature(Sensor.TYPE_TEMPERATURE),
    ;
    
    private int typeCode;
    
    private SensorType(int typeCode) {
        this.typeCode = typeCode;
    }
    
    public static SensorType fromTypeCode(int typeCode) {
        for (SensorType sensorType : SensorType.values())
            if (sensorType.typeCode == typeCode)
                return sensorType;
        return SensorType.Unknown;
    }

    /* TODO: Not sure whether I want this or not.
     * Can't decide if it's useful anywhere else other than when we determine all of the available sensors.
    public static int[] getAllSensorTypeCodes() {
        SensorType[] allSensorTypes = SensorType.values();
        int numSensorTypes = allSensorTypes.length;
        int allCodes[] = new int[numSensorTypes];
        int i = 0;
        for (SensorType sensorType : allSensorTypes)
            allCodes[i] = sensorType.typeCode;
        return allCodes;
    }
     */
    
    public int getTypeCode() { return typeCode;}
    
    public boolean isTemperature() { return this == Temperature;}
    public boolean isNotTemperature() { return this != Temperature; }
    
    public boolean isRotationVector() { return this == RotationVector; }
    public boolean isNotRotationVector() { return this != RotationVector; }
    
    public boolean isProximity() { return this == Proximity; }
    public boolean isNotProximity() { return this != Proximity; }
    
    public boolean isPressure() { return this == Pressure; }
    public boolean isNotPressure() { return this != Pressure; }
    
    public boolean isOrientation() { return this == Orientation; }
    public boolean isNotOrientation() { return this != Orientation; }
    
    public boolean isMagneticField() { return this == MagneticField; }
    public boolean isNotMagneticField() { return this != MagneticField; }
    
    public boolean isLinearAcceleration() { return this == LinearAcceleration; }
    public boolean isNotLinearAcceleration() { return this != LinearAcceleration; }
    
    public boolean isLight() { return this == Light; }
    public boolean isNotLight() { return this != Light; }
    
    public boolean isGravity() { return this == Gravity; }
    public boolean isNotGravity() { return this != Gravity; }
    
    public boolean isGyroscope() { return this == Gyroscope; }
    public boolean isNotGyroscope() { return this != Gyroscope; }
    
    public boolean isAccelerometer() { return this == Accelerometer; }
    public boolean isNotAccelerometer() { return this != Accelerometer; }
    
    public boolean isUnknown() { return this == Unknown; }
    public boolean isNotUnknown() { return this != Unknown; }
    
}
