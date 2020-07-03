package org.photonvision.vision.processes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.photonvision.vision.pipeline.CVPipelineSettings;

/** VisionModuleManager has many VisionModules, and provides camera configuration data to them. */
public class VisionModuleManager {

    private static class ThreadSafeSingleton {
        private static final VisionModuleManager INSTANCE = new VisionModuleManager();
    }

    public static VisionModuleManager getInstance() {
        return VisionModuleManager.ThreadSafeSingleton.INSTANCE;
    }

    protected final List<VisionModule> visionModules = new ArrayList<>();

    private VisionModuleManager() {}

    public List<VisionModule> getModules() {
        return visionModules;
    }

    public VisionModule getModule(int i) {
        return visionModules.get(i);
    }

    public void addSources(HashMap<VisionSource, List<CVPipelineSettings>> visionSources) {
        for (var entry : visionSources.entrySet()) {
            var visionSource = entry.getKey();
            var pipelineManager = new PipelineManager(entry.getValue());
            var module = new VisionModule(pipelineManager, visionSource, visionModules.size());
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
