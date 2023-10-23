package group.serverhotelbooking.controller;

import group.serverhotelbooking.payload.BaseResponse;
import group.serverhotelbooking.payload.request.ReservationRequest;
import group.serverhotelbooking.service.imp.ReservationServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/reservation")
public class ReservationController {
    @Autowired
    private ReservationServiceImp reservationServiceImp;

    @PostMapping("")
    private ResponseEntity<?> insertReservation(@RequestBody ReservationRequest reservationRequest) {
        boolean isSuccess = reservationServiceImp.insertReservation(reservationRequest);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(isSuccess);
        baseResponse.setMessage("Insert Reservation");
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
