package group.serverhotelbooking.service.imp;

import group.serverhotelbooking.constant.Constant;
import group.serverhotelbooking.payload.request.RoomRequest;
import group.serverhotelbooking.payload.response.RoomResponse;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface RoomServiceImp {
    Page<RoomResponse> getAllRoom(int page, int size);

    RoomResponse getRoomById(int id);

    boolean addRoom(RoomRequest roomRequest, String pathFolderStore) throws Exception;

    boolean deleteRoom(int id);

    boolean updateRoom(int id, RoomRequest roomRequest, String path) throws IOException;

    List<RoomResponse> getRooms();

    boolean findRoomByName(String roomName);
}
