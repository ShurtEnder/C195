package model;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimeFunctions {
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyy HH:mm");
    static ZoneId zoneIDLoc = ZoneId.systemDefault();
    static ZoneId zoneIDEST = ZoneId.of("America/New_York");
    static ZoneId zoneIDUTC = ZoneId.of("UTC");

    /* LocalDateTime timeLoc = LocalDateTime.now(); */

    public static ZonedDateTime getLoctoUTC(LocalDateTime timeLoc){
        ZonedDateTime zoneStr = timeLoc.atZone(zoneIDLoc);
        ZonedDateTime zoneStrUTC = zoneStr.withZoneSameInstant(ZoneOffset.UTC);
        return zoneStrUTC;
    }

    public static ZonedDateTime getLoctoEST(LocalDateTime timeLoc){
        ZonedDateTime zoneStr = timeLoc.atZone(zoneIDLoc);
        ZonedDateTime zoneStrEST= zoneStr.withZoneSameInstant(zoneIDEST.getRules().getOffset(Instant.now()));
        return zoneStrEST;
    }

    public static ZonedDateTime getESTtoUTC(LocalDateTime timeLoc){
        ZonedDateTime zoneStr = timeLoc.atZone(zoneIDEST);
        ZonedDateTime zoneStrUTC= zoneStr.withZoneSameInstant(ZoneOffset.UTC);
        return zoneStrUTC;
    }

    public static ZonedDateTime getESTtoLoc(LocalDateTime timeLoc){
        ZonedDateTime zoneStr = timeLoc.atZone(zoneIDEST);
        ZonedDateTime zoneStrLoc= zoneStr.withZoneSameInstant(zoneIDLoc.getRules().getOffset(Instant.now()));
        return zoneStrLoc;
    }

    public static ZonedDateTime getUTCtoEst(LocalDateTime timeLoc){
        ZonedDateTime zoneStr = timeLoc.atZone(zoneIDUTC);
        ZonedDateTime zoneStrEst= zoneStr.withZoneSameInstant(zoneIDEST.getRules().getOffset(Instant.now()));
        return zoneStrEst;
    }

    public static ZonedDateTime getUTCtoLoc(LocalDateTime timeLoc){
        ZonedDateTime zoneStr = timeLoc.atZone(zoneIDUTC);
        ZonedDateTime zoneStrLoc= zoneStr.withZoneSameInstant(zoneIDLoc.getRules().getOffset(Instant.now()));
        return zoneStrLoc;
    }

    public static LocalTime zdtToTime(LocalDateTime time){
        LocalTime locTime = time.toLocalTime();
        return locTime;
    }

    public static LocalDate zdtToDate(LocalDateTime time){
        LocalDate locDate = time.toLocalDate();
        return locDate;
    }

    public static LocalDateTime combDT(LocalDate localDate, LocalTime localTime){
        return LocalDateTime.of(localDate,localTime);
    }

    public static String formDTF(LocalDateTime time){
        return time.format(dtf);
    }

    public static LocalDateTime ldtParse(String form){
        return LocalDateTime.parse(form, dtf);
    }

    public static LocalDateTime zonetoLDT(ZonedDateTime zone){
        return zone.toLocalDateTime();
    }
}
