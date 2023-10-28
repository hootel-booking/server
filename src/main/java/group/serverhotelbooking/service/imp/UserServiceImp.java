package group.serverhotelbooking.service.imp;

import group.serverhotelbooking.payload.request.UserRequest;
import group.serverhotelbooking.payload.response.UserResponse;
import org.springframework.core.io.Resource;

import java.net.MalformedURLException;
import java.util.List;

public interface UserServiceImp {
    List<UserResponse> getAllUser();

    boolean editUserById(int id, UserRequest userRequest);

    boolean deleteUserById(int id);

    UserResponse getUserById(int id, String hostName) throws MalformedURLException;

    boolean editProfile(int id, UserRequest userRequest, String pathFolderStore) throws Exception;

    Resource downloadUserFile(String fileName, String pathFolderStore) throws MalformedURLException;
}
