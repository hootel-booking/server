package group.serverhotelbooking.service.imp;

import group.serverhotelbooking.payload.request.ReservationRequest;
import group.serverhotelbooking.payload.response.ReservationResponse;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface ReservationServiceImp {
    boolean insertReservation(ReservationRequest reservationRequest) throws ParseException;

    boolean checkAvailability(int idRoom, Date dateCheckin, Date dateCheckout);

    List<ReservationResponse> getListReservation();

    ReservationResponse findReservationById(int id);

    boolean updateReservationById(int id, ReservationRequest request);

    List<ReservationResponse> getListReservationByIdUser(int idUser);
}
