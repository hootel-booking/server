package group.serverhotelbooking.repository;

import group.serverhotelbooking.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer> {
    @Query("SELECT r FROM ReservationEntity r WHERE r.room.id = :idRoom AND " +
            "((:dateCheckIn BETWEEN dateCheckIn AND dateCheckout) OR " +
            "(:dateCheckout BETWEEN dateCheckIn AND dateCheckout) " +
            "OR (dateCheckIn BETWEEN :dateCheckIn AND :dateCheckout))")
    List<ReservationEntity> checkRoomAvailability(
            @Param("idRoom") int idRoom,
            @Param("dateCheckIn") Date dateCheckIn,
            @Param("dateCheckout") Date dateCheckout
    );

    @Query("SELECT r FROM ReservationEntity r WHERE r.user.id = :idUser")
    List<ReservationEntity> findListReservationByIdUser(@Param("idUser") int idUser);
}
