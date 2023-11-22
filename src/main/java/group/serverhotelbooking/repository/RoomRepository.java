package group.serverhotelbooking.repository;

import group.serverhotelbooking.entity.RoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Integer> {
    Page<RoomEntity> findAll(Pageable pageable);

    @Query("SELECT r FROM RoomEntity r WHERE r.name = :roomName")
    List<RoomEntity> findByName(@Param("roomName") String roomName);
}
