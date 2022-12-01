import impl.AdvancedTimestamp;
import interfaces.IAdvancedTimestamp;
import interfaces.IUnixTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RightAdvancedTimestamp extends RightUnixTimestamp implements IAdvancedTimestamp {

    public ZonedDateTime get() {
        Instant i = Instant.ofEpochSecond(s);
        return ZonedDateTime.ofInstant(i, utc);
    }

    @Override
    public Weekday getWeekday(int n) {
        return Weekday.values()[get().getDayOfWeek().getValue()];
    }

    @Override
    public int getDay() {
        return get().getDayOfMonth();
    }

    @Override
    public int getMonth() {
        return get().getMonthValue();
    }

    @Override
    public int getYear() {
        return get().getYear();
    }

    @Override
    public int getHour() {
        return get().getHour();
    }

    @Override
    public int getMinutes() {
        return get().getMinute();
    }

    @Override
    public int getSeconds() {
        return get().getSecond();
    }

    public static IAdvancedTimestamp create(int secondsSince1970) {
        if (secondsSince1970 < 0){
            throw new IllegalArgumentException();
        }
        RightAdvancedTimestamp ts = new RightAdvancedTimestamp();
        ts.s = secondsSince1970;
        return ts;
    }

    public static IAdvancedTimestamp create(String s) {
        long unix = -1;
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDateTime r = LocalDateTime.parse(s, df);
            ZonedDateTime z = r.atZone(ZoneId.of("Etc/UTC"));
            unix = z.toEpochSecond();
        } catch (DateTimeParseException e) {

        }
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            LocalDateTime r = LocalDateTime.parse(s, df);
            ZonedDateTime z = r.atZone(ZoneId.of("Etc/UTC"));
            unix = z.toEpochSecond();
        } catch (DateTimeParseException e) {

        }
        if (unix < 0) {
            throw new IllegalArgumentException();
        }
        if (unix > Integer.MAX_VALUE) {
            throw new IllegalArgumentException();
        }
        return create((int) unix);
    }

}
