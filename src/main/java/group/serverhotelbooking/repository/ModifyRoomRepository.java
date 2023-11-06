package group.serverhotelbooking.repository;

import group.serverhotelbooking.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModifyRoomRepository extends JpaRepository<RoomEntity, Integer> {
}
