package model;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Time Function class.
 * @author Rene Gomez Student ID: 001467443
 */
public class TimeFunctions {
    /** Date Time Formatter. Creates a format time to MM-dd-yyy HH:mm*/
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyy HH:mm");
    /** Local Zone ID. Sets variable to system default zone ID.*/
    static ZoneId zoneIDLoc = ZoneId.systemDefault();
    /** EST Time Zone ID. Sets variable to EST zone ID.*/
    static ZoneId zoneIDEST = ZoneId.of("America/New_York");
    /** UTC Time Zone ID. Sets variable to UTC zone ID.*/
    static ZoneId zoneIDUTC = ZoneId.of("UTC");

    /**
     * Local to UTC. Changes local LDT to UTC ZDT.
     * @param timeLoc timeLoc to set
     * @return Returns UTC ZDT.
     */
    public static ZonedDateTime getLoctoUTC(LocalDateTime timeLoc){
        ZonedDateTime zoneStr = timeLoc.atZone(zoneIDLoc);
        ZonedDateTime zoneStrUTC = zoneStr.withZoneSameInstant(ZoneOffset.UTC);
        return zoneStrUTC;
    }

    /**
     * Local to EST. Changes local LDT to EST ZDT.
     * @param timeLoc timeLoc to set
     * @return Returns EST ZDT
     */
    public static ZonedDateTime getLoctoEST(LocalDateTime timeLoc){
        ZonedDateTime zoneStr = timeLoc.atZone(zoneIDLoc);
        ZonedDateTime zoneStrEST= zoneStr.withZoneSameInstant(zoneIDEST.getRules().getOffset(Instant.now()));
        return zoneStrEST;
    }

    /**
     * EST to UTC. Changes local EST LDT to UTC ZDT.
     * @param timeLoc timeLoc to set
     * @return Returns UTC ZDT
     */
    public static ZonedDateTime getESTtoUTC(LocalDateTime timeLoc){
        ZonedDateTime zoneStr = timeLoc.atZone(zoneIDEST);
        ZonedDateTime zoneStrUTC= zoneStr.withZoneSameInstant(ZoneOffset.UTC);
        return zoneStrUTC;
    }

    /**
     * EST to Local. Changes EST LDT to Local ZDT.
     * @param timeLoc timeLoc to set
     * @return Returns Local ZDT
     */
    public static ZonedDateTime getESTtoLoc(LocalDateTime timeLoc){
        ZonedDateTime zoneStr = timeLoc.atZone(zoneIDEST);
        ZonedDateTime zoneStrLoc= zoneStr.withZoneSameInstant(zoneIDLoc.getRules().getOffset(Instant.now()));
        return zoneStrLoc;
    }

    /**
     * UTC to EST. Changes local UTC LDT to EST ZDT.
     * @param timeLoc timeLoc to set
     * @return Returns EST ZDT
     */
    public static ZonedDateTime getUTCtoEst(LocalDateTime timeLoc){
        ZonedDateTime zoneStr = timeLoc.atZone(zoneIDUTC);
        ZonedDateTime zoneStrEst= zoneStr.withZoneSameInstant(zoneIDEST.getRules().getOffset(Instant.now()));
        return zoneStrEst;
    }

    /**
     * UTC to Local. Changes UTC LDT to Local ZDT.
     * @param timeLoc timeLoc to set
     * @return Returns Local ZDT
     */
    public static ZonedDateTime getUTCtoLoc(LocalDateTime timeLoc){
        ZonedDateTime zoneStr = timeLoc.atZone(zoneIDUTC);
        ZonedDateTime zoneStrLoc= zoneStr.withZoneSameInstant(zoneIDLoc.getRules().getOffset(Instant.now()));
        return zoneStrLoc;
    }

    /**
     * Local Date Time Comnine. Combines a date and time together.
     * @param localDate localDate to set
     * @param localTime localTime to set
     * @return
     */
    public static LocalDateTime combDT(LocalDate localDate, LocalTime localTime){
        return LocalDateTime.of(localDate,localTime);
    }

    /**
     * Formats LDT. Formats Local Date Time input into MM-dd-yyy HH:mm format.
     * @param time time to set to
     * @return
     */
    public static String formDTF(LocalDateTime time){
        return time.format(dtf);
    }


}
