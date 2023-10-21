package group.serverhotelbooking.filter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import group.serverhotelbooking.util.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtHelper jwtHelper;

    private Gson gson = new Gson();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerValue = request.getHeader("Authorization");
        if (headerValue != null && headerValue.startsWith("Bearer ")) {
            String token = headerValue.substring(7);
            String data = jwtHelper.parseToken(token);

            if (data != null && !data.isEmpty()) {
                Type listType = new TypeToken<ArrayList<SimpleGrantedAuthority>>() {}.getType();


                List<SimpleGrantedAuthority> roles = gson.fromJson(data,listType);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken("", "", roles);
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(usernamePasswordAuthenticationToken);}
        } else {
            System.out.println("giá trị token không hợp lệ");
        }

        filterChain.doFilter(request, response);
    }
}