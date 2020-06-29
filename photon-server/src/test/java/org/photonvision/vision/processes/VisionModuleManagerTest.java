package org.photonvision.vision.processes;

import edu.wpi.cscore.VideoMode;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.*;
import org.photonvision.common.configuration.CameraConfiguration;
import org.photonvision.common.datatransfer.DataConsumer;
import org.photonvision.common.util.TestUtils;
import org.photonvision.vision.frame.FrameProvider;
import org.photonvision.vision.frame.provider.FileFrameProvider;
import org.photonvision.vision.pipeline.result.CVPipelineResult;
import org.photonvision.vision.pipeline.CVPipelineSettings;

public class VisionModuleManagerTest {

    @BeforeEach
    public void init() {
        TestUtils.loadLibraries();
    }

    private static class TestSource implements VisionSource {

        private final FrameProvider provider;

        public TestSource(FrameProvider provider) {

            this.provider = provider;
        }

        @Override
        public FrameProvider getFrameProvider() {
            return provider;
        }

        @Override
        public VisionSourceSettables getSettables() {
            return new TestSettables(new CameraConfiguration("", "", "", ""));
        }
    }

    private static class TestSettables extends VisionSourceSettables {

        protected TestSettables(CameraConfiguration configuration) {
            super(configuration);
        }

        @Override
        public int getExposure() {
            return 0;
        }

        @Override
        public void setExposure(int exposure) {}

        @Override
        public int getBrightness() {
            return 0;
        }

        @Override
        public void setBrightness(int brightness) {}

        @Override
        public int getGain() {
            return 0;
        }

        @Override
        public void setGain(int gain) {}

        @Override
        public VideoMode getCurrentVideoMode() {
            return null;
        }

        @Override
        public void setCurrentVideoMode(VideoMode videoMode) {}

        @Override
        public HashMap<Integer, VideoMode> getAllVideoModes() {
            return null;
        }
    }

    private static class TestDataConsumer implements DataConsumer {
        private Data data;

        @Override
        public void accept(Data data) {
            this.data = data;
        }

        public Data getData() {
            return data;
        }
    }

    @Test
    public void setupManager() {
        var sources = new HashMap<VisionSource, List<CVPipelineSettings>>();
        sources.put(
                new TestSource(
                        new FileFrameProvider(
                                TestUtils.getWPIImagePath(TestUtils.WPI2019Image.kCargoStraightDark72in_HighRes),
                                TestUtils.WPI2019Image.FOV)), List.of());

        VisionModuleManager.getInstance().addSources(sources);
        var module0DataConsumer = new TestDataConsumer();

        VisionModuleManager.getInstance().visionModules.get(0).addDataConsumer(module0DataConsumer);

        VisionModuleManager.getInstance().startModules();

        sleep(500);

        Assertions.assertNotNull(module0DataConsumer.data);
        Assertions.assertNotNull(module0DataConsumer.data.result);
        printTestResults(module0DataConsumer.data.result);
    }

    private static void printTestResults(CVPipelineResult pipelineResult) {
        double fps = 1000 / pipelineResult.getLatencyMillis();
        System.out.print(
                "Pipeline ran in " + pipelineResult.getLatencyMillis() + "ms (" + fps + " fps), ");
        System.out.println("Found " + pipelineResult.targets.size() + " valid targets");
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // ignored
        }
    }
}
