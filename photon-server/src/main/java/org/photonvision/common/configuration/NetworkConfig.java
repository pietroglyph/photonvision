package org.photonvision.common.configuration;

import org.photonvision.common.networking.NetworkMode;

import java.util.HashMap;

public class NetworkConfig {
    public int teamNumber = 1;
    public NetworkMode connectionType = NetworkMode.DHCP;
    public String ip = "";
    public String gateway = "";
    public String netmask = "";
    public String hostname = "photonvision";

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> tmp = new HashMap<>();
        tmp.put("teamNumber", teamNumber);
        tmp.put("connectionType", connectionType.ordinal());
        tmp.put("ip", ip);
        tmp.put("gateway", gateway);
        tmp.put("netmask", netmask);
        return tmp;
    }
}
