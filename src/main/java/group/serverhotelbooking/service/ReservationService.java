package group.serverhotelbooking.service;

import group.serverhotelbooking.entity.ReservationEntity;
import group.serverhotelbooking.entity.RoomEntity;
import group.serverhotelbooking.entity.StatusEntity;
import group.serverhotelbooking.entity.UserEntity;
import group.serverhotelbooking.payload.request.ReservationRequest;
import group.serverhotelbooking.payload.response.ReservationResponse;
import group.serverhotelbooking.repository.ReservationRepository;
import group.serverhotelbooking.repository.RoomRepository;
import group.serverhotelbooking.repository.StatusRepository;
import group.serverhotelbooking.repository.UserRepository;
import group.serverhotelbooking.service.imp.ReservationServiceImp;
import group.serverhotelbooking.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService implements ReservationServiceImp {
    @Autowired
    private Common common;

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
            reservation.setDateCheckIn(common.convertStringToDate(reservationRequest.getDateCheckIn()));
            reservation.setDateCheckout(common.convertStringToDate(reservationRequest.getDateCheckOut()));
            reservation.setAdultNumber(reservationRequest.getAdultNumber());
            reservation.setChildNumber(reservationRequest.getChildNumber());
            reservation.setPrice(reservationRequest.getPrice());
            reservation.setDiscount(reservationRequest.getDiscount());
            reservation.setNote(reservationRequest.getNote());
            reservation.setRoom(room.get());
            reservation.setUser(user.get());
            reservation.setStatus(status.get());
            reservation.setCreateDate(common.getCurrentDateTime());

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
        List<ReservationEntity> result = reservationRepository.checkRoomAvailability(idRoom, dateCheckin, dateCheckout);
        return result.size() < 1;
    }

    @Override
    public List<ReservationResponse> getListReservation() {
        List<ReservationEntity> reservations = reservationRepository.findAll();
        List<ReservationResponse> responseList = new ArrayList<>();

        for (ReservationEntity reservationEntity : reservations) {
            ReservationResponse response = new ReservationResponse();
            response.setId(reservationEntity.getId());
            response.setDateCheckIn(reservationEntity.getDateCheckIn());
            response.setDateCheckout(reservationEntity.getDateCheckout());
            response.setAdultNumber(reservationEntity.getAdultNumber());
            response.setChildNumber(reservationEntity.getChildNumber());
            response.setPrice(reservationEntity.getPrice());
            response.setDiscount(reservationEntity.getDiscount());
            response.setCreateDate(reservationEntity.getCreateDate());
            response.setRoomName(reservationEntity.getRoom().getName());
            response.setEmailUser(reservationEntity.getUser().getEmail());
            response.setStatus(reservationEntity.getStatus().getName());
            response.setPhoneUser(reservationEntity.getUser().getPhone());

            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public ReservationResponse findReservationById(int id) {
        Optional<ReservationEntity> entity = reservationRepository.findById(id);
        ReservationResponse response = new ReservationResponse();

        if (entity.isPresent()) {
            ReservationEntity reservationEntity = entity.get();
            response.setId(reservationEntity.getId());
            response.setDateCheckIn(reservationEntity.getDateCheckIn());
            response.setDateCheckout(reservationEntity.getDateCheckout());
            response.setAdultNumber(reservationEntity.getAdultNumber());
            response.setChildNumber(reservationEntity.getChildNumber());
            response.setPrice(reservationEntity.getPrice());
            response.setDiscount(reservationEntity.getDiscount());
            response.setCreateDate(reservationEntity.getCreateDate());
            response.setNote(reservationEntity.getNote());
            response.setRoomName(reservationEntity.getRoom().getName());
            response.setEmailUser(reservationEntity.getUser().getEmail());
            response.setPhoneUser(reservationEntity.getUser().getPhone());
            response.setStatus(reservationEntity.getStatus().getName());
            response.setFirstName(reservationEntity.getUser().getFirstname());
            response.setLastName(reservationEntity.getUser().getLastName());
            response.setIdStatus(reservationEntity.getStatus().getId());
            response.setDeposit(reservationEntity.getDeposit());
        }

        return response;
    }

    @Override
    public boolean updateReservationById(int id, ReservationRequest request) {
        Optional<ReservationEntity> reservationEntity = reservationRepository.findById(id);

        if (reservationEntity.isPresent()) {
            ReservationEntity reservationTemp = reservationEntity.get();
            reservationTemp.setDeposit(request.getDeposit());

            StatusEntity status = new StatusEntity();
            status.setId(request.getIdStatus());
            reservationTemp.setStatus(status);

            try {
                reservationRepository.save(reservationTemp);
                return true;
            } catch (Exception ex) {
                System.out.println("Error " + ex);
                return false;
            }
        }

        return false;
    }

    @Override
    public List<ReservationResponse> getListReservationByIdUser(int idUser) {
        List<ReservationEntity> listReservation = reservationRepository.findListReservationByIdUser(idUser);
        List<ReservationResponse> reservationResponseList = new ArrayList<>();

        for(ReservationEntity reservationEntity : listReservation) {
            ReservationResponse response = new ReservationResponse();
            response.setId(reservationEntity.getId());
            response.setRoomName(reservationEntity.getRoom().getName());
            response.setDateCheckIn(reservationEntity.getDateCheckIn());
            response.setDateCheckout(reservationEntity.getDateCheckout());
            response.setPrice(reservationEntity.getPrice());
            response.setDiscount(reservationEntity.getDiscount());
            response.setStatus(reservationEntity.getStatus().getName());
            reservationResponseList.add(response);
        }

        return reservationResponseList;
    }
}
