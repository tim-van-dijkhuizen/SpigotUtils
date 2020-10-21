package nl.timvandijkhuizen.spigotutils.helpers;

import java.util.ArrayList;
import java.util.List;

public class ExceptionHelper {

    public static String[] getStackTrace(Throwable cause) {
        List<String> lines = new ArrayList<String>();

        lines.add("[" + cause.getClass().getSimpleName() + "] Cause [" + cause.getMessage() + "]");
        for (StackTraceElement ste : cause.getStackTrace()) {
            lines.add(ste.toString());
        }

        return lines.toArray(new String[lines.size()]);
    }

}
