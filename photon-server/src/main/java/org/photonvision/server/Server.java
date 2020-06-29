package org.photonvision.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import org.photonvision.common.logging.LogGroup;
import org.photonvision.common.logging.Logger;

import java.util.Map;

public class Server {
    private static final Logger logger = new Logger(Server.class, LogGroup.Server);

    public static void main(int port) {
        Javalin app = Javalin.create(
            config -> {
                config.showJavalinBanner = false;
                config.addStaticFiles("web");
                config.enableCorsForAllOrigins();

                config.requestLogger((ctx, ms) ->
                    logger.debug("Got HTTP " + ctx.req.getMethod() + " request at " + ms.toString()));

                config.wsLogger(ws -> ws.onMessage(ctx ->
                    logger.debug("Got WebSockets message: " + ctx.message())));

                config.wsLogger(ws -> ws.onBinaryMessage(ctx ->
                    logger.debug("Got WebSockets binary message from host " + ctx.host() + " with contents "
                        + new ObjectMapper().readValue(ctx.data(), new TypeReference<Map<String, Object>>(){}))));
            });

        var socketHandler = SocketHandler.getInstance();

        /*Web Socket Events */
        app.ws(
            "/websocket",
            ws -> {
                ws.onConnect(socketHandler::onConnect);
                ws.onClose(socketHandler::onClose);
                ws.onBinaryMessage(socketHandler::onBinaryMessage);
            });
        /*API Events*/
        app.post("/api/settings/general", RequestHandler::onGeneralSettings);
        app.post("/api/settings/camera", RequestHandler::onCameraSettings);
        app.post("/api/vision/duplicate", RequestHandler::onDuplicatePipeline);
        app.post("/api/settings/startCalibration", RequestHandler::onCalibrationStart);
        app.post("/api/settings/snapshot", RequestHandler::onSnapshot);
        app.post("/api/settings/endCalibration", RequestHandler::onCalibrationEnding);
        app.post("/api/vision/pnpModel", RequestHandler::onPnpModel);
        app.post("/api/install", RequestHandler::onInstallOrUpdate);
        app.start(port);
    }
}
