public class Logger {

    public Logger() {

    }

    public void log(LogType type, String msg) {
        System.out.println(type.toString() + msg);
    }

}
