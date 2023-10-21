package group.serverhotelbooking.service.imp;

import group.serverhotelbooking.payload.request.SignUpRequest;
public interface SignupServiceImp {
    boolean insertUser (SignUpRequest signUpRequest);
}