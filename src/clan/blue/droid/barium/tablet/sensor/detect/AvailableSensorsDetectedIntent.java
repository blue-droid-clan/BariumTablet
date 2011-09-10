package clan.blue.droid.barium.tablet.sensor.detect;

import android.content.IntentFilter;

import com.codeswimmer.android.dispatch.intent.BaseIntent;

public class AvailableSensorsDetectedIntent extends BaseIntent {
    public static final String INTENT_ACTION = "clan.blue.droid.barium.tablet.sensor.detect.AVAILABLE_SENSORS_DETECTED";
    
    public AvailableSensorsDetectedIntent() {
        super(INTENT_ACTION);
    }
    
    public static IntentFilter createIntentFilter() {
        return new IntentFilter(INTENT_ACTION);
    }
}
