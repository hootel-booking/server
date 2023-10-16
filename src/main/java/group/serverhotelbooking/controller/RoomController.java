package group.serverhotelbooking.controller;

import group.serverhotelbooking.constant.Constant;
import group.serverhotelbooking.payload.BaseResponse;
import group.serverhotelbooking.payload.response.RoomResponse;
import group.serverhotelbooking.service.imp.RoomServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/rooms")
public class RoomController {
    @Autowired
    private RoomServiceImp roomServiceImp;

    @GetMapping("/{pageNumber}")
    private ResponseEntity<?> getProductsPagination(@PathVariable int pageNumber) {
        Page<RoomResponse> rooms = roomServiceImp.getAllRoom(pageNumber, Constant.PAGE_SIZE);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Get Products Pagination");
        baseResponse.setData(rooms);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
