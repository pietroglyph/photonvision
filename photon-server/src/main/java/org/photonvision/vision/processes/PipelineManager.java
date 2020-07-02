package org.photonvision.vision.processes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.photonvision.vision.pipeline.*;

@SuppressWarnings({"rawtypes", "unused"})
public class PipelineManager {

    public static final int DRIVERMODE_INDEX = -1;
    public static final int CAL_3D_INDEX = -2;

    public final List<CVPipelineSettings> userPipelineSettings;
    private final Calibration3dPipeline calibration3dPipeline = new Calibration3dPipeline();
    private final DriverModePipeline driverModePipeline = new DriverModePipeline();

    /** Index of the currently active pipeline. */
    private int currentPipelineIndex = DRIVERMODE_INDEX;

    /** The currently active pipeline. */
    private CVPipeline currentPipeline = driverModePipeline;

    /**
    * Index of the last active user-created pipeline. <br>
    * <br>
    * Used only when switching from any of the built-in pipelines back to a user-created pipeline.
    */
    private int lastPipelineIndex;

    /**
    * Creates a PipelineManager with a DriverModePipeline, a Calibration3dPipeline, and all provided
    * pipelines.
    *
    * @param userPipelines Pipelines to add to the manager.
    */
    public PipelineManager(List<CVPipelineSettings> userPipelines) {
        this.userPipelineSettings = userPipelines;
        // TODO: this is a hack until the UI can add/remove pipelines
        addPipeline(PipelineType.Reflective);
        setPipelineInternal(0);
    }
    /**
    * Get the settings for a pipeline by index.
    *
    * @param index Index of pipeline whose settings need getting.
    * @return The gotten settings of the pipeline whose index was provided.
    */
    public CVPipelineSettings getPipelineSettings(int index) {
        if (index < 0) {
            switch (index) {
                case DRIVERMODE_INDEX:
                    return driverModePipeline.getSettings();
                case CAL_3D_INDEX:
                    return calibration3dPipeline.getSettings();
            }
        }

        for (var setting : userPipelineSettings) {
            if (setting.pipelineIndex == index) return setting;
        }
        return null;
    }

    public List<String> getPipelineNicknames() {
        List<String> ret = new ArrayList<>();
        for(var p: userPipelineSettings) {
            ret.add(p.pipelineNickname);
        }
        return ret;
    }


    public int getCurrentPipelineIndex() {
        return lastPipelineIndex;
    }

    /**
    * Get the currently active pipeline.
    *
    * @return The currently active pipeline.
    */
    public CVPipeline getCurrentPipeline() {
        if (currentPipelineIndex < 0) {
            switch (currentPipelineIndex) {
                case CAL_3D_INDEX: return calibration3dPipeline;
                case DRIVERMODE_INDEX: return driverModePipeline;
            }
        }

        var desiredPipelineSettings = userPipelineSettings.get(currentPipelineIndex);
        if (currentPipeline.getSettings().pipelineIndex != desiredPipelineSettings.pipelineIndex) {
            switch (desiredPipelineSettings.pipelineType) {
                case Reflective:
                    currentPipeline = new ReflectivePipeline((ReflectivePipelineSettings)desiredPipelineSettings);
                    break;
                case ColoredShape:
                    currentPipeline = new ColoredShapePipeline((ColoredShapePipelineSettings)desiredPipelineSettings);
                    break;
            }
        }

        return currentPipeline;
    }

    /**
    * Get the currently active pipelines settings
    *
    * @return The currently active pipelines settings
    */
    public CVPipelineSettings getCurrentPipelineSettings() {
        return getPipelineSettings(currentPipelineIndex);
    }

    /**
    * Internal method for setting the active pipeline. <br>
    * <br>
    * All externally accessible methods that intend to change the active pipeline MUST go through
    * here to ensure all proper steps are taken.
    *
    * @param index Index of pipeline to be active
    */
    private void setPipelineInternal(int index) {
        if (index < 0) {
            lastPipelineIndex = currentPipelineIndex;
        }

        currentPipelineIndex = index;
    }

    /**
    * Leaves the current built-in pipeline, if applicable, and sets the active pipeline to the most
    * recently active user-created pipeline.
    */
    public void exitAuxiliaryPipeline() {
        if (currentPipelineIndex < 0) {
            setPipelineInternal(lastPipelineIndex);
        }
    }

    public static final Comparator<CVPipelineSettings> PipelineSettingsIndexComparator =
        Comparator.comparingInt(o -> o.pipelineIndex);

    /**
    * Sorts the pipeline list by index, and reassigns their indexes to match the new order. <br>
    * <br>
    * I don't like this but I have no other ideas, and it works so ¯\_(ツ)_/¯
    */
    private void reassignIndexes() {
        userPipelineSettings.sort(PipelineSettingsIndexComparator);
        for (int i = 0; i < userPipelineSettings.size(); i++) {
            getPipelineSettings(i).pipelineIndex = i;
        }
    }

    public CVPipelineSettings addPipeline(PipelineType type) {
        switch (type) {
            case Reflective: {
                var added = new ReflectivePipelineSettings();
                userPipelineSettings.add(added);
                return added;
            }
            case ColoredShape: {
                var added = new ColoredShapePipelineSettings();
                userPipelineSettings.add(added);
                return added;
            }
        }
        return null;
    }

    private void addPipelineInternal(CVPipelineSettings settings) {
        settings.pipelineIndex = userPipelineSettings.size();
        userPipelineSettings.add(settings);
    }

    private void removePipelineInternal(int index) {
        userPipelineSettings.remove(index);
        reassignIndexes();
    }

    public void setIndex(int index) {
        this.setPipelineInternal(index);
    }
}
