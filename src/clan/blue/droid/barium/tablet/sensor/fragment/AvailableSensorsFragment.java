package clan.blue.droid.barium.tablet.sensor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import clan.blue.droid.barium.tablet.R;

public class AvailableSensorsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.available_sensors, null);
        return mainView;
    }
}
