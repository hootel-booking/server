package group.serverhotelbooking.repository;

import group.serverhotelbooking.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {
    @Query("SELECT c FROM CartEntity c  WHERE c.user.id = :idUser")
    List<CartEntity> findByIdUser(@Param("idUser") int idUser);

    @Query("SELECT c FROM CartEntity c WHERE c.room.id = :idRoom AND c.user.id = :idUser")
    CartEntity findByIdRoomAndIdUser(@Param("idUser") int idUser, @Param("idRoom") int idRoom);

    @Query("SELECT COUNT(id) AS roomNumber FROM CartEntity WHERE user.id = :idUser")
    int countRoomsInCart(@Param("idUser") int idUser);

    @Modifying
    @Query("DELETE FROM CartEntity WHERE user.id = :idUser")
    void deleteCart(@Param("idUser") int idUser);
}
