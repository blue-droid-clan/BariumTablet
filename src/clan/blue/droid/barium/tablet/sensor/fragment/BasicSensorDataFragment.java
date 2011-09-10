package clan.blue.droid.barium.tablet.sensor.fragment;

import android.app.Fragment;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import clan.blue.droid.barium.tablet.R;

public class BasicSensorDataFragment extends Fragment {
    @SuppressWarnings("unused")
    private static final String TAG = BasicSensorDataFragment.class.getSimpleName();
    
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
    
    public BasicSensorDataFragment(Sensor sensor) {
        this.sensor = sensor;
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
    }
    
    public void updateWidgetValues() {
        updateHumanReadableNameWidget();
        sensorNameView.setText(sensor.getName());
        sensorTypeView.setText(String.format("%d", sensor.getType()));
        sensorVendorView.setText(sensor.getVendor());
        sensorVersionView.setText(String.format("%d", sensor.getVersion()));
        sensorPowerUseView.setText(String.format("%,04.2f", sensor.getPower()));
        sensorResolutionView.setText(String.format("%,4.2f", sensor.getResolution()));
        sensorMaxRangeView.setText(String.format("%,4.2f", sensor.getMaximumRange()));
        sensorMinDelayView.setText(String.format("%,4.2f", Double.valueOf(sensor.getMinDelay()).doubleValue()));
    }

    public void setHumanReadableNameResId(int humanReadableNameResId) {
        this.humanReadableNameResId = humanReadableNameResId;
    }
    
    private void updateHumanReadableNameWidget() {
        if (sensorHumanReadableNameView != null)
            sensorHumanReadableNameView.setText(getHumanReadableName());
    }
    
    private String getHumanReadableName() {
        return getString(humanReadableNameResId);
    }
}
