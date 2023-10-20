package group.serverhotelbooking.repository;

import group.serverhotelbooking.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
    @Query("SELECT i FROM ImageEntity i WHERE i.room.id = :idRoom")
    List<ImageEntity> findByIdRoom(@Param("idRoom") int idRoom);
}
