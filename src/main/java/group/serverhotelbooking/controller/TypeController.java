package group.serverhotelbooking.controller;

import group.serverhotelbooking.payload.BaseResponse;
import group.serverhotelbooking.payload.response.TypeResponse;
import group.serverhotelbooking.service.imp.TypeServiceImp;
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
@RequestMapping(value = "/types")
public class TypeController {
    @Autowired
    private TypeServiceImp typeServiceImp;

    @GetMapping("")
    private ResponseEntity<?> getTypes() {
        List<TypeResponse> types = typeServiceImp.getTypes();

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Get Types");
        baseResponse.setData(types);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
