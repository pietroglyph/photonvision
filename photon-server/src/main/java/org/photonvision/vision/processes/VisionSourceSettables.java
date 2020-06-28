package org.photonvision.vision.processes;

import edu.wpi.cscore.VideoMode;
import java.util.HashMap;
import org.photonvision.common.configuration.USBCameraConfiguration;
import org.photonvision.vision.frame.FrameStaticProperties;

public abstract class VisionSourceSettables {
    private final USBCameraConfiguration configuration;

    protected VisionSourceSettables(USBCameraConfiguration configuration) {
        this.configuration = configuration;
    }

    FrameStaticProperties frameStaticProperties;
    protected HashMap<Integer, VideoMode> videoModes;

    public USBCameraConfiguration getConfiguration() {
        return configuration;
    }

    public abstract int getExposure();

    public abstract void setExposure(int exposure);

    public abstract int getBrightness();

    public abstract void setBrightness(int brightness);

    public abstract int getGain();

    public abstract void setGain(int gain);

    public abstract VideoMode getCurrentVideoMode();

    public abstract void setCurrentVideoMode(VideoMode videoMode);

    public abstract HashMap<Integer, VideoMode> getAllVideoModes();

    public double getFOV() {
        return configuration.FOV;
    }

    public void setFOV(double fov) {
        configuration.FOV = fov;
    }

    public FrameStaticProperties getFrameStaticProperties() {
        return frameStaticProperties;
    }
}
