package group.serverhotelbooking.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class Common {
    public String handleConvertRole(String roleResponse) {
        String[] roles = roleResponse.split("_");
        return roles[1];
    }

    public Date convertStringToDate(String date) {
        String dateParts[] = date.split("-");
        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        LocalDate localDate = LocalDate.of(year, month, day);
        Date dateFormat = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return dateFormat;
    }

    public Date getCurrentDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Date dateFormat = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return dateFormat;
    }
}