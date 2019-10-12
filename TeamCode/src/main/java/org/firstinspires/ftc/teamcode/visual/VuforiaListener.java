package org.firstinspires.ftc.teamcode.visual;

import com.vuforia.TrackableResult;

import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaSkyStone;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VuforiaListener {

    private HashMap<Listener, Boolean> listeners = new HashMap<>();

    class Listener implements VuforiaTrackable.Listener{

        private VuforiaTrackable trackable;

        @Override
        public void onTracked(TrackableResult trackableResult, CameraName cameraName, Camera camera, VuforiaTrackable child) {
            listeners.put(this, true);
        }

        @Override
        public void onNotTracked() {
            listeners.put(this, false);
        }

        @Override
        public void addTrackable(VuforiaTrackable trackable) {
            this.trackable = (VuforiaTrackable) trackable;
        }

        public VuforiaTrackable getTrackable() {
            return trackable;
        }
    }

    public void addTrackable(VuforiaTrackable trackable)
    {
        Listener listener = new Listener();
        listeners.put(listener, false);
        listener.addTrackable(trackable);
    }

    public List<VuforiaTrackable> getVisibleTrackables() {
        List<VuforiaTrackable> trackables = new ArrayList<>();
        for (Listener listener : listeners.keySet()) {
            if(listeners.get(listener))
                trackables.add(listener.getTrackable());
        }

        return trackables;
    }
}
