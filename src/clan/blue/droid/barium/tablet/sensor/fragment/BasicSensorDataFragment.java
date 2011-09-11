package clan.blue.droid.barium.tablet.sensor.fragment;

import com.codeswimmer.common.util.StringUtils;

import android.app.Fragment;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import clan.blue.droid.barium.tablet.BariumTabletApp;
import clan.blue.droid.barium.tablet.R;
import clan.blue.droid.common.android.sensor.SensorType;

public class BasicSensorDataFragment extends Fragment implements SensorEventListener {
    @SuppressWarnings("unused")
    private static final String TAG = BasicSensorDataFragment.class.getSimpleName();
    
    private final int[] sensorRealtimeLabelResIds = {
        R.id.sensorRealtimeLabel1,
        R.id.sensorRealtimeLabel2,
        R.id.sensorRealtimeLabel3,
    };
    
    private final int[] sensorRealtimeValueResIds = {
        R.id.sensorRealtimeValue1,
        R.id.sensorRealtimeValue2,
        R.id.sensorRealtimeValue3,
    };
    
    private final TextView[] sensorRealtimeLabelDisplays;
    private final TextView[] sensorRealtimeValueDisplays;
    
    @SuppressWarnings("unused")
    private final SensorManager sensorManager;
    private Sensor sensor;
    private int humanReadableNameResId;
    
    private TextView sensorHumanReadableNameView;
    private TextView sensorNameView;
    private TextView sensorTypeView;
    private TextView sensorVendorView;
    private TextView sensorVersionView;
    private TextView sensorPowerUseView;
    private TextView sensorResolutionView;
    private TextView sensorMaxRangeView;
    private TextView sensorMinDelayView;
    
    @SuppressWarnings("unused")
    private ViewGroup basicSensorRealtimeDataContainer;
    private TextView basicSensorRealtimeDataTitle;
    
    public BasicSensorDataFragment(Sensor sensor) {
        this.sensor = sensor;
        sensorManager = BariumTabletApp.getSensorManager();
        sensorRealtimeLabelDisplays = new TextView[sensorRealtimeLabelResIds.length];
        sensorRealtimeValueDisplays = new TextView[sensorRealtimeValueResIds.length];
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.basic_sensor_data, null);
        storeWidgetHandles(mainView);
        updateWidgetValues();
        return mainView;
    }
    
    private void storeWidgetHandles(View mainView) {
        sensorHumanReadableNameView = (TextView) mainView.findViewById(R.id.sensorHumanReadableNameView);
        sensorNameView = (TextView) mainView.findViewById(R.id.sensorNameView);
        sensorTypeView = (TextView) mainView.findViewById(R.id.sensorTypeView);
        sensorVendorView = (TextView) mainView.findViewById(R.id.sensorVendorView);
        sensorVersionView = (TextView) mainView.findViewById(R.id.sensorVersionView);
        sensorPowerUseView = (TextView) mainView.findViewById(R.id.sensorPowerUseView);
        sensorResolutionView = (TextView) mainView.findViewById(R.id.sensorResolutionView);
        sensorMaxRangeView = (TextView) mainView.findViewById(R.id.sensorMaxRangeView);
        sensorMinDelayView = (TextView) mainView.findViewById(R.id.sensorMinDelayView);
        
        basicSensorRealtimeDataContainer = (ViewGroup) mainView.findViewById(R.id.basicSensorRealtimeDataContainer);
        basicSensorRealtimeDataTitle = (TextView) mainView.findViewById(R.id.basicSensorRealtimeDataTitle);
        
        int numFields = sensorRealtimeValueResIds.length;
        for (int i = 0; i < numFields; i++) {
            int resId = sensorRealtimeLabelResIds[i];
            sensorRealtimeLabelDisplays[i] = (TextView) mainView.findViewById(resId);
            
            resId = sensorRealtimeValueResIds[i];
            sensorRealtimeValueDisplays[i] = (TextView) mainView.findViewById(resId);
        }
    }
    
    public void updateWidgetValues() {
        updateHumanReadableNameWidget();
        updateRealtimeDataTitle();
        updateRealtimeLabels();
        sensorNameView.setText(sensor.getName());
        sensorTypeView.setText(String.format("%d", sensor.getType()));
        sensorVendorView.setText(sensor.getVendor());
        sensorVersionView.setText(String.format("%d", sensor.getVersion()));
        sensorPowerUseView.setText(String.format("%,04.2f", sensor.getPower()));
        sensorResolutionView.setText(String.format("%,4.2f", sensor.getResolution()));
        sensorMaxRangeView.setText(String.format("%,4.2f", sensor.getMaximumRange()));
        sensorMinDelayView.setText(String.format("%,4.2f", Double.valueOf(sensor.getMinDelay()).doubleValue()));
    }
    
    private void updateRealtimeDataTitle() {
        String text = getString(R.string.sensor_realtime_title_msg, sensor.getName());
        basicSensorRealtimeDataTitle.setText(text);
    }
    
    private void updateHumanReadableNameWidget() {
        if (sensorHumanReadableNameView != null)
            sensorHumanReadableNameView.setText(getHumanReadableName());
    }
    
    private void updateRealtimeLabels() {
        SensorType sensorType = SensorType.fromTypeCode(sensor.getType());
        Integer[] resIds = BariumTabletApp.sensorRealtimeLabelsResIdsForSensorType(sensorType);
        for (int i = 0; i < resIds.length; i++) {
            Integer resId = resIds[i];
            if (resId == null) {
                sensorRealtimeLabelDisplays[i].setText(StringUtils.EMPTY);
                continue;
            }
            String text = getString(resId);
            sensorRealtimeLabelDisplays[i].setText(text);
        }
    }
    
    @Override
    public void onSensorChanged(SensorEvent event) {
        int numValues = event.values.length;
        for (int i = 0; i < numValues; i++)
            updateRealtimeValue(i, event.values[i]);
    }
    
    private void updateRealtimeValue(int index, float value) {
        sensorRealtimeValueDisplays[index].setText(String.format("%+,12.3f", value));
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    
    private String getHumanReadableName() {
        return getString(humanReadableNameResId);
    }
    
    public void setHumanReadableNameResId(int humanReadableNameResId) {
        this.humanReadableNameResId = humanReadableNameResId;
    }
}
