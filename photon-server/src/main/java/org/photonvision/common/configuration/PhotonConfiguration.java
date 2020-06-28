package org.photonvision.common.configuration;

import java.util.HashMap;

// TODO rename this class
public class PhotonConfiguration {
    public HardwareConfig getHardwareConfig() {
        return hardwareConfig;
    }

    public NetworkConfig getNetworkConfig() {
        return networkConfig;
    }

    public HashMap<String, USBCameraConfiguration> getCameraConfigurations() {
        return cameraConfigurations;
    }

    public void addCameraConfig(USBCameraConfiguration config) {
        addCameraConfig(config.uniqueName, config);
    }

    public void addCameraConfig(String name, USBCameraConfiguration config) {
        cameraConfigurations.put(name, config);
    }

    private HardwareConfig hardwareConfig;
    private NetworkConfig networkConfig;

    private HashMap<String, USBCameraConfiguration> cameraConfigurations;

    public PhotonConfiguration(HardwareConfig hardwareConfig, NetworkConfig networkConfig) {
        this(hardwareConfig, networkConfig, new HashMap<>());
    }

    public PhotonConfiguration(
            HardwareConfig hardwareConfig,
            NetworkConfig networkConfig,
            HashMap<String, USBCameraConfiguration> cameraConfigurations) {
        this.hardwareConfig = hardwareConfig;
        this.networkConfig = networkConfig;
        this.cameraConfigurations = cameraConfigurations;
    }
}
