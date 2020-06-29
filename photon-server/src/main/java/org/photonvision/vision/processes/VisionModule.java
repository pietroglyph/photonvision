package org.photonvision.vision.processes;

import java.util.LinkedList;

import edu.wpi.first.networktables.NetworkTableInstance;
import org.photonvision.common.dataflow.events.OutgoingUIEvent;
import org.photonvision.common.dataflow.networktables.NTDataConsumer;
import org.photonvision.common.datatransfer.DataConsumer;
import org.photonvision.server.UIUpdateType;
import org.photonvision.vision.frame.Frame;
import org.photonvision.vision.frame.FrameConsumer;
import org.photonvision.vision.frame.consumer.MJPGFrameConsumer;
import org.photonvision.vision.pipeline.result.CVPipelineResult;

/**
 * This is the God Class
 *
 * <p>VisionModule has a pipeline manager, vision runner, and data providers. The data providers
 * provide info on settings changes. VisionModuleManager holds a list of all current vision modules.
 */
public class VisionModule {

    private final PipelineManager pipelineManager;
    private final VisionSource visionSource;
    private final VisionRunner visionRunner;
    private final LinkedList<DataConsumer> dataConsumers = new LinkedList<>();
    private final LinkedList<FrameConsumer> frameConsumers = new LinkedList<>();

    public VisionModule(PipelineManager pipelineManager, VisionSource visionSource) {
        this.pipelineManager = pipelineManager;
        this.visionSource = visionSource;
        this.visionRunner =
            new VisionRunner(
                this.visionSource.getFrameProvider(),
                this.pipelineManager::getCurrentPipeline,
                this::consumeResult);

        frameConsumers.add(new MJPGFrameConsumer(visionSource.getSettables().getConfiguration()));
        dataConsumers.add(new NTDataConsumer(NetworkTableInstance.getDefault().getTable("photonvision"),
            visionSource.getSettables().getConfiguration().nickname));
    }

    public void start() {
        visionRunner.startProcess();
    }

    public void addDataConsumer(DataConsumer dataConsumer) {
        dataConsumers.add(dataConsumer);
    }

    void addFrameConsumer(FrameConsumer consumer) {
        frameConsumers.add(consumer);
    }

    void consumeResult(CVPipelineResult result) {
        // TODO: put result in to Data (not this way!)
        var data = new Data();
        data.result = result;
        consumeData(data);

        var frame = result.outputFrame;
        consumeFrame(frame);
    }

    void consumeData(Data data) {
        for (var dataConsumer : dataConsumers) {
            dataConsumer.accept(data);
        }
    }

    void consumeFrame(Frame frame) {
        for (var frameConsumer : frameConsumers) {
            frameConsumer.accept(frame);
        }
    }
}
