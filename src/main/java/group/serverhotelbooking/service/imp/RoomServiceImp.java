package group.serverhotelbooking.service.imp;

import group.serverhotelbooking.payload.request.RoomRequest;
import group.serverhotelbooking.payload.response.RoomResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RoomServiceImp {
    Page<RoomResponse> getAllRoom(int page, int size);

    RoomResponse getRoomById(int id);

    boolean addRoom(RoomRequest roomRequest, List <MultipartFile> multipartFile);
    boolean deleteRoom(int id);
    boolean updateRoom(RoomRequest roomRequest);
    List<RoomResponse> getListRoom();
}
