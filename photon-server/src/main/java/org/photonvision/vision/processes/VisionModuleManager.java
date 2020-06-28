package org.photonvision.vision.processes;

import org.photonvision.vision.frame.consumer.MJPGFrameConsumer;
import org.photonvision.vision.pipeline.CVPipelineSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** VisionModuleManager has many VisionModules, and provides camera configuration data to them. */
public class VisionModuleManager {
    protected final List<VisionModule> visionModules = new ArrayList<>();

    public VisionModuleManager(HashMap<VisionSource, List<CVPipelineSettings>> visionSources) {
        for (var entry : visionSources.entrySet()) {
            var visionSource = entry.getKey();
            var pipelineManager = new PipelineManager(entry.getValue());
            var module = new VisionModule(pipelineManager, visionSource);
            visionModules.add(module);
            // todo: logging
        }
    }

    public void startModules() {
        for (var visionModule : visionModules) {
            visionModule.start();
            // todo: logging
        }
    }
}
