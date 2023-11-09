package group.serverhotelbooking.controller;

import group.serverhotelbooking.payload.BaseResponse;
import group.serverhotelbooking.payload.request.ReservationRequest;
import group.serverhotelbooking.payload.response.ReservationResponse;
import group.serverhotelbooking.service.imp.ReservationServiceImp;
import group.serverhotelbooking.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/reservation")
public class  ReservationController {
    @Autowired
    private Common common;

    @Autowired
    private ReservationServiceImp reservationServiceImp;

    @PostMapping("")
    private ResponseEntity<?> insertReservation(@RequestBody ReservationRequest reservationRequest) throws ParseException {
        boolean isSuccess = reservationServiceImp.insertReservation(reservationRequest);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(isSuccess);
        baseResponse.setMessage("Insert Reservation");
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/idRoom={idRoom}")
    private ResponseEntity<?> checkRoomAvailability(
            @PathVariable int idRoom,
            @RequestBody ReservationRequest reservationRequest
    ) throws ParseException {
        boolean isSuccess = reservationServiceImp.checkAvailability(idRoom,
                common.convertStringToDate(reservationRequest.getDateCheckIn()),
                common.convertStringToDate(reservationRequest.getDateCheckOut()));

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(isSuccess);
        baseResponse.setMessage("Check Room Availability");
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("")
    private ResponseEntity<?> getAllReservation() {
        List<ReservationResponse> reservationEntities = reservationServiceImp.getListReservation();

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(reservationEntities);
        baseResponse.setMessage("Get All Reservation");
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/id={id}")
    private ResponseEntity<?> findById(@PathVariable int id) {
        ReservationResponse response = reservationServiceImp.findReservationById(id);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(response);
        baseResponse.setMessage("Find Reservation By Id");
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping("/id={id}")
    private ResponseEntity<?> updateById(@PathVariable int id, @RequestBody ReservationRequest request) {
        boolean isSuccess = reservationServiceImp.updateReservationById(id, request);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(isSuccess);
        baseResponse.setMessage("Update Reservation By Id");
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/idUser={idUser}")
    private ResponseEntity<?> getReservationByIdUser(@PathVariable int idUser) {
        List<ReservationResponse> reservationEntities = reservationServiceImp.getListReservationByIdUser(idUser);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(reservationEntities);
        baseResponse.setMessage("Get Reservation By Id User");
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
