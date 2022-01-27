package com.tpo.fitme.service.peaks;

import com.tpo.fitme.domain.activity.Activity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.var;
import lombok.val;

@Data
@Builder
public class Peak {

    private final String name;
    private final String altitude;
    private final String activity;
    private final String date;

    public static Peak of(Activity activity) {
        return Peak.builder()
                .name(parseName(activity))
                .altitude(parseAltitude(activity))
                .activity(activity.getUrl())
                .date(activity.getStartDate().toString())
                .build();
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
