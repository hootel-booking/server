package group.serverhotelbooking.service.imp;

import group.serverhotelbooking.payload.request.UserRequest;
import group.serverhotelbooking.payload.response.UserResponse;

import java.util.List;

public interface UserServiceImp {
    List<UserResponse> getAllUser();

    boolean editUserById(int id, UserRequest userRequest);

    boolean deleteUserById(int id);
}
