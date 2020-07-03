package org.photonvision.vision;

import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.photonvision.vision.camera.CameraQuirk;
import org.photonvision.vision.camera.QuirkyCamera;

public class QuirkyCameraTest {
    @Test
    public void ps3EyeTest() {
        HashMap<CameraQuirk, Boolean> ps3EyeQuirks = new HashMap<>();
        ps3EyeQuirks.put(CameraQuirk.Gain, true);
        for (var q : CameraQuirk.values()) {
            ps3EyeQuirks.putIfAbsent(q, false);
        }

        QuirkyCamera psEye = QuirkyCamera.getQuirkyCamera(0x1415, 0x2000, "psEye");
        Assertions.assertEquals(psEye.quirks, ps3EyeQuirks);
    }

    @Test
    public void quirklessCameraTest() {
        HashMap<CameraQuirk, Boolean> noQuirks = new HashMap<>();
        for (var q : CameraQuirk.values()) {
            noQuirks.put(q, false);
        }

        QuirkyCamera quirkless = QuirkyCamera.getQuirkyCamera(1234, 888, "empty");
        Assertions.assertEquals(quirkless.quirks, noQuirks);
    }
}
