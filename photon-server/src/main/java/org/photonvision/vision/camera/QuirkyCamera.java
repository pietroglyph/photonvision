package org.photonvision.vision.camera;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class QuirkyCamera {
    private static final List<QuirkyCamera> quirkyCameras =
            List.of(
                    new QuirkyCamera(0x1415, 0x2000, "PS3Eye", List.of(CameraQuirk.Gain)));

    public final int usbVid;
    public final int usbPid;
    public final String baseName;
    public final HashMap<CameraQuirk, Boolean> quirks;

    private QuirkyCamera(int usbVid, int usbPid, String baseName, List<CameraQuirk> quirks) {
        this.usbVid = usbVid;
        this.usbPid = usbPid;
        this.baseName = baseName;

        this.quirks = new HashMap<>();
        for (var q : quirks) {
            this.quirks.put(q, true);
        }
        for (var q: CameraQuirk.values()) {
            this.quirks.putIfAbsent(q, false);
        }
    }

    public static QuirkyCamera getQuirkyCamera(int usbVid, int usbPid, String baseName) {
        for (var qc : quirkyCameras) {
            if (qc.usbVid == usbVid && qc.usbPid == usbPid) {
                return qc;
            }
        }
        return new QuirkyCamera(usbVid, usbPid, baseName, List.of());
    }

    public boolean hasQuirk(CameraQuirk quirk) {
        return quirks.get(quirk);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuirkyCamera that = (QuirkyCamera) o;
        return usbVid == that.usbVid &&
                usbPid == that.usbPid &&
                Objects.equals(baseName, that.baseName) &&
                Objects.equals(quirks, that.quirks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usbVid, usbPid, baseName, quirks);
    }
}
