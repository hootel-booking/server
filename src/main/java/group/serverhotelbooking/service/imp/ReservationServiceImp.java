package group.serverhotelbooking.service.imp;

import group.serverhotelbooking.payload.request.ReservationRequest;

import java.util.Date;

public interface ReservationServiceImp {
    boolean insertReservation(ReservationRequest reservationRequest);

    boolean checkAvailability(int idRoom, Date dateCheckin, Date dateCheckout);
}
