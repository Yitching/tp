package seedu.duke.utility;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.Level;


public class MintLogger {
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void run() {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.INFO);

        try {
            FileHandler file = new FileHandler("MintLogger.log", true);
            file.setLevel(Level.INFO);
            logger.addHandler(file);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to log to file", e);
        }

        logger.info("Start logging");
    }
}
