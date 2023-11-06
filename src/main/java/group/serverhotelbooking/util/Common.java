package group.serverhotelbooking.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class Common {
    public String handleConvertRole(String roleResponse) {
        String[] roles = roleResponse.split("_");
        return roles[1];
    }

    public Date convertStringToDate(String date) {
        // "2023-10-24" -> 2023-10-24
        LocalDate localDate = LocalDate.of(2016, 8, 19);
        // Tue Oct 24 00:00:00 IST 2023
        Date dateFormat = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        //return ft.format(dateFormat);
        return dateFormat;
    }
}
