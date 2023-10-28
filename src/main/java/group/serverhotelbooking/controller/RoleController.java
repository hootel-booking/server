package group.serverhotelbooking.controller;

import group.serverhotelbooking.payload.BaseResponse;
import group.serverhotelbooking.payload.response.RoleResponse;
import group.serverhotelbooking.service.imp.RoleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/roles")
public class RoleController {
    @Autowired
    private RoleServiceImp roleServiceImp;

    @GetMapping("")
    private ResponseEntity<?> getRoles() {
        List<RoleResponse> roles = roleServiceImp.getRoles();

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Get Roles");
        baseResponse.setData(roles);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
