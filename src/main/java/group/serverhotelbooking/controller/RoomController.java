package group.serverhotelbooking.controller;

import group.serverhotelbooking.constant.Constant;
import group.serverhotelbooking.payload.BaseResponse;
import group.serverhotelbooking.payload.request.RoomRequest;
import group.serverhotelbooking.payload.response.RoomResponse;
import group.serverhotelbooking.service.imp.RoomServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/rooms")
public class RoomController {
    @Autowired
    private RoomServiceImp roomServiceImp;

    @GetMapping("/page={pageNumber}")
    private ResponseEntity<?> getRoomsPagination(@PathVariable int pageNumber) {
        Page<RoomResponse> rooms = roomServiceImp.getAllRoom(pageNumber, Constant.PAGE_SIZE);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Get Rooms Pagination");
        baseResponse.setData(rooms);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/id={id}")
    private ResponseEntity<?> getRoomById(@PathVariable int id) {
        RoomResponse room = roomServiceImp.getRoomById(id);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Get Room By Id");
        baseResponse.setData(room);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/add")
    private ResponseEntity<?> addRoom(
        @RequestParam String name, @RequestParam double price, @RequestParam int discount,
        @RequestParam String description, @RequestParam int idSize, @RequestParam int idType,
        @RequestParam MultipartFile file
    ) throws Exception {
        RoomRequest roomRequest = new RoomRequest();
        roomRequest.setName(name);
        roomRequest.setPrice(price);
        roomRequest.setDiscount(discount);
        roomRequest.setDescription(description);
        roomRequest.setId_size(idSize);
        roomRequest.setId_type(idType);
        roomRequest.setFile(file);

        boolean addRoomIsSuccess = roomServiceImp.addRoom(roomRequest, Constant.PATH_ROOMS);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Create New Room");
        baseResponse.setData(addRoomIsSuccess);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    private ResponseEntity<?> deleteRoom(@RequestParam int id) {
        boolean deleteIsSuccess = roomServiceImp.deleteRoom(id);

        BaseResponse baseResponse = new BaseResponse(200, "delete room successfully", deleteIsSuccess);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping("/id={id}")
    public ResponseEntity<?> updateRoom(
        @PathVariable int id, @RequestParam String name, @RequestParam double price, @RequestParam int discount,
        @RequestParam String description, @RequestParam int idSize, @RequestParam int idType,
        @RequestParam MultipartFile file
    ) throws IOException  {
        RoomRequest roomRequest = new RoomRequest();
        roomRequest.setName(name);
        roomRequest.setPrice(price);
        roomRequest.setDiscount(discount);
        roomRequest.setDescription(description);
        roomRequest.setId_size(idSize);
        roomRequest.setId_type(idType);
        roomRequest.setFile(file);

        boolean updateIsSuccess = roomServiceImp.updateRoom(id, roomRequest, Constant.PATH_ROOMS);

        BaseResponse baseResponse = new BaseResponse(200, "updated room successfully", updateIsSuccess);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("")
    private ResponseEntity<?> getAllRooms() {
        List<RoomResponse> rooms = roomServiceImp.getRooms();

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Get List Rooms");
        baseResponse.setData(rooms);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/name={roomName}")
    private ResponseEntity<?> findRoomByName(@PathVariable String roomName){
        boolean isSuccess = roomServiceImp.findRoomByName(roomName);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(isSuccess);
        baseResponse.setMessage("Find Room By Name");
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
