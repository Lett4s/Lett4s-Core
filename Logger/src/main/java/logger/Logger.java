package logger;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class Logger {

    private static final SimpleDateFormat LOG_DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.US);
    private static final SimpleDateFormat LOG_FILE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss", Locale.US);
    private static final Path TARGET_DIRECTORY = Paths.get(System.getProperty("user.dir"));
    private static FileWriter WRITER;
    private static boolean LOGFILE;


    static {
        try {
            Logger.WRITER = new FileWriter(TARGET_DIRECTORY.resolve(LOG_FILE_FORMAT.format(new Date())).toFile());
        } catch (IOException ignored) {

        }
    }

    private static void log(LogType type, String msg, Object... objects) {
        System.out.println(type.toString() + msg);
        if (LOGFILE) {
            try {
                WRITER.write(type + msg + Arrays.toString(objects));
                WRITER.flush();
            } catch (IOException ignored) {

            }
        }
    }

    public static void createLogFile() {
        Logger.LOGFILE = true;
    }

    public static void log(String info) {
        log(LogType.LOG, info);
    }

    public static void info(String info, Object... o) {
        log(LogType.INFO, info);
    }

    public static void debug(String info, Object... o) {
        log(LogType.DEBUG, info);
    }

    public static void warn(String info, Object... o) {
        log(LogType.WARN, info);
    }

    public static void error(String info, Object... o) {
        log(LogType.ERROR, info);
    }

    public static void fatal(String info, Object... o) {
        log(LogType.FATAL, info, o);
    }

}
