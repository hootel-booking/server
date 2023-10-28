package group.serverhotelbooking.util;

import org.springframework.stereotype.Component;

@Component
public class ConvertRole {
    public String handleConvertRole(String roleResponse) {
        String[] roles = roleResponse.split("_");
        return roles[1];
    }
}
