package group.serverhotelbooking.controller;

import group.serverhotelbooking.payload.BaseResponse;
import group.serverhotelbooking.payload.response.StatusResponse;
import group.serverhotelbooking.service.imp.StatusServiceImp;
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
@RequestMapping(value = "/status")
public class StatusController {
    @Autowired
    private StatusServiceImp statusServiceImp;

    @GetMapping("")
    private ResponseEntity<?> getListStatus() {
        List<StatusResponse> listStatus = statusServiceImp.getAllStatus();

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Get List Status");
        baseResponse.setData(listStatus);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
