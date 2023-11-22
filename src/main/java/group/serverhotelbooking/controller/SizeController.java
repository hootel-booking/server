package group.serverhotelbooking.controller;

import group.serverhotelbooking.payload.BaseResponse;
import group.serverhotelbooking.payload.response.SizeResponse;
import group.serverhotelbooking.service.imp.SizeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/sizes")
public class SizeController {
    @Autowired
    private SizeServiceImp sizeServiceImp;

    @GetMapping("")
    private ResponseEntity<?> getSizes() {
        List<SizeResponse> sizes = sizeServiceImp.getSizes();

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Get Sizes");
        baseResponse.setData(sizes);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
