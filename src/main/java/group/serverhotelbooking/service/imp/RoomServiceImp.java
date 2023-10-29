package group.serverhotelbooking.service.imp;

import group.serverhotelbooking.payload.request.RoomRequest;
import group.serverhotelbooking.payload.response.RoomResponse;
import org.springframework.data.domain.Page;

public interface RoomServiceImp {
    Page<RoomResponse> getAllRoom(int page, int size);

    RoomResponse getRoomById(int id);

    boolean addRoom(RoomRequest roomRequest);
    boolean deleteRoom(int id);
    boolean updateRoom(RoomRequest roomRequest);
}
