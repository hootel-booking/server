package group.serverhotelbooking.controller;

import group.serverhotelbooking.constant.Constant;
import group.serverhotelbooking.payload.BaseResponse;
import group.serverhotelbooking.payload.request.UserRequest;
import group.serverhotelbooking.payload.response.UserResponse;
import group.serverhotelbooking.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserServiceImp userServiceImp;

    @GetMapping("")
    private ResponseEntity<?> getAllUser() {
        List<UserResponse> users = userServiceImp.getAllUser();

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(users);
        baseResponse.setMessage("Get All Users");
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/id={id}")
    private ResponseEntity<?> getUserById(@PathVariable int id) throws MalformedURLException {
        UserResponse user = userServiceImp.getUserById(id, Constant.PATH_AVATARS);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(user);
        baseResponse.setMessage("Get User By Id");
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping("/id={id}")
    private ResponseEntity<?> editUserById(@PathVariable int id, @RequestBody UserRequest userRequest) {
        boolean isSuccess = userServiceImp.editUserById(id, userRequest);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(isSuccess);
        baseResponse.setMessage("Edit User By Id");
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @DeleteMapping("/id={id}")
    private ResponseEntity<?> deleteUserById(@PathVariable int id) {
        boolean isSuccess = userServiceImp.deleteUserById(id);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(isSuccess);
        baseResponse.setMessage("Delete User By Id");
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping("/profileId={id}")
    private ResponseEntity<?> editProfile(@PathVariable int id, @RequestParam String firstName,
        @RequestParam String lastName, @RequestParam String phone, @RequestParam MultipartFile file
    ) throws Exception {
        UserRequest user = new UserRequest();
        user.setFirstname((firstName));
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setFile(file);

        boolean isSuccess = userServiceImp.editProfile(id, user, Constant.PATH_AVATARS);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(isSuccess);
        baseResponse.setMessage("Edit Profile");
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
