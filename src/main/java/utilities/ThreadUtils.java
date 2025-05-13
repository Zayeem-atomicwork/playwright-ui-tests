package utilities;

import org.apache.logging.log4j.Logger;

/**
 * The class {@link ThreadUtils} holds static methods related to threading.
 */
public final class ThreadUtils {

    private static final Logger LOGGER = Log.getLogger(ThreadUtils.class);

    private ThreadUtils() {
    }

    /**
     * Method that cause the currently executing thread to sleep for the specified
     * number of seconds.
     *
     * @param secs: time to sleep in seconds
     */
    public static void sleep(final int secs) {
        try {
            Thread.sleep(secs * 1000);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}