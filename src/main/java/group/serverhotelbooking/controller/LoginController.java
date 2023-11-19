package group.serverhotelbooking.controller;


import com.google.gson.Gson;
import group.serverhotelbooking.payload.BaseResponse;
import group.serverhotelbooking.payload.request.LoginRequest;
import group.serverhotelbooking.payload.request.SignUpRequest;
import group.serverhotelbooking.payload.response.AuthenticationResponse;
import group.serverhotelbooking.payload.response.UserResponse;
import group.serverhotelbooking.service.imp.LoginAuthenServiceImp;
import group.serverhotelbooking.service.imp.SignupServiceImp;
import group.serverhotelbooking.util.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private LoginAuthenServiceImp loginAuthenServiceImp;

    @Autowired
    SignupServiceImp signupServiceImp;

    private Gson gson = new Gson();

    @PostMapping("/signin")
    public ResponseEntity<?> siginin( @RequestBody  LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        boolean isAuthenticated = loginAuthenServiceImp.authenticate(loginRequest);

        if (isAuthenticated) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            List<SimpleGrantedAuthority> roles = (List<SimpleGrantedAuthority>) authentication.getAuthorities();

            String jsonRoles = gson.toJson(roles);
            /*
            Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path $
            sửa bằng cách tại roles.get(0) bỏ đi get(0)
             */
            String token = jwtHelper.generateToken(jsonRoles);

            AuthenticationResponse response = new AuthenticationResponse();
            UserResponse user = (UserResponse) authentication.getPrincipal();
            response.setUser(user);
            response.setToken(token);

            BaseResponse baseResponse = new BaseResponse();

            if (jsonRoles.contains("ROLE_ADMIN")) {
                baseResponse.setStatusCode(200);
                baseResponse.setMessage(String.valueOf(roles.get(0)));
                baseResponse.setData(response);
            } else if (jsonRoles.contains("ROLE_USER")) {
                baseResponse.setStatusCode(200);
                baseResponse.setMessage(String.valueOf(roles.get(0)));
                baseResponse.setData(response);
            } else {
                baseResponse.setStatusCode(403);
                baseResponse.setMessage("Access Denied");
            }
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);


        } else {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatusCode(401);
            baseResponse.setMessage("Invalid username or password");
            return new ResponseEntity<>(baseResponse, HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        boolean isSuccess = signupServiceImp.insertUser(signUpRequest);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage(isSuccess? "Successfully signed up":"Error");
        baseResponse.setData(isSuccess);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
