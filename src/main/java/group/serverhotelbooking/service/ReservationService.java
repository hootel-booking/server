package group.serverhotelbooking.service;

import group.serverhotelbooking.entity.ReservationEntity;
import group.serverhotelbooking.entity.RoomEntity;
import group.serverhotelbooking.entity.StatusEntity;
import group.serverhotelbooking.entity.UserEntity;
import group.serverhotelbooking.payload.request.ReservationRequest;
import group.serverhotelbooking.repository.ReservationRepository;
import group.serverhotelbooking.repository.RoomRepository;
import group.serverhotelbooking.repository.StatusRepository;
import group.serverhotelbooking.repository.UserRepository;
import group.serverhotelbooking.service.imp.ReservationServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ReservationService implements ReservationServiceImp {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public boolean insertReservation(ReservationRequest reservationRequest) {
        ReservationEntity reservation = new ReservationEntity();

        Optional<RoomEntity> room = roomRepository.findById(reservationRequest.getIdRoom());
        Optional<UserEntity> user = userRepository.findById(reservationRequest.getIdUser());
        Optional<StatusEntity> status = statusRepository.findById(reservationRequest.getIdStatus());

        if (room.isPresent() && user.isPresent() && status.isPresent()) {
            reservation.setDateCheckIn(reservationRequest.getDateCheckIn());
            reservation.setDateCheckout(reservationRequest.getDateCheckout());
            reservation.setAdultNumber(reservationRequest.getAdultNumber());
            reservation.setChildNumber(reservationRequest.getChildNumber());
            reservation.setPrice(reservationRequest.getPrice());
            reservation.setDiscount(reservationRequest.getDiscount());
            reservation.setNote(reservationRequest.getNote());
            reservation.setRoom(room.get());
            reservation.setUser(user.get());
            reservation.setStatus(status.get());

            try {
                reservationRepository.save(reservation);
                return true;
            } catch (Exception ex) {
                System.out.println("Error: " + ex);
                return false;
            }
        }

        return false;
    }

    @Override
    public boolean checkAvailability(int idRoom, Date dateCheckin, Date dateCheckout) {

        return false;
    }
}
