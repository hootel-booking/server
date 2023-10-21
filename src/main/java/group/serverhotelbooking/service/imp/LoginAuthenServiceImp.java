package group.serverhotelbooking.service.imp;

import group.serverhotelbooking.payload.request.LoginRequest;

public interface LoginAuthenServiceImp {
    boolean authenticate(LoginRequest loginRequest);
}
