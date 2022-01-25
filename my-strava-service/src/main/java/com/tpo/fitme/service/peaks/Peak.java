package com.tpo.fitme.service.peaks;

import com.tpo.fitme.domain.activity.Activity;
import lombok.Data;
import lombok.experimental.var;
import lombok.val;

@Data
public class Peak {

    private final String name;
    private final String altitude;

    public static Peak of(Activity activity) {
        return new Peak(parseName(activity), parseAltitude(activity));
    }

    private static String parseName(Activity activity) {
        var to = activity.getName().indexOf("(");
        if (to < 0) to = activity.getName().length();
        return activity.getName().substring(8, to);
    }

    private static String parseAltitude(Activity activity) {
        val from = activity.getName().indexOf("(");
        val to = activity.getName().indexOf(")");
        if (to < 0 || from < 0) return "N/A";
        return activity.getName().substring(from + 1, to);
    }
}
