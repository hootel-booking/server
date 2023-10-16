package group.serverhotelbooking.service;

import group.serverhotelbooking.entity.RoomEntity;
import group.serverhotelbooking.payload.response.RoomResponse;
import group.serverhotelbooking.repository.RoomRepository;
import group.serverhotelbooking.service.imp.RoomServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService implements RoomServiceImp {
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Page<RoomResponse> getAllRoom(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RoomEntity> rooms = roomRepository.findAll(pageable);
        List<RoomResponse> roomResponses = new ArrayList<>();

        for(RoomEntity room: rooms) {
            RoomResponse roomTemp = new RoomResponse();
            roomTemp.setId(room.getId());
            roomTemp.setName(room.getName());
            roomTemp.setPrice(room.getPrice());
            roomTemp.setNameType(room.getType().getName());
            roomTemp.setSquare(room.getSize().getSquare());

            roomResponses.add(roomTemp);
        }

        Page<RoomResponse> pages = new PageImpl<>(roomResponses, pageable, rooms.getTotalElements());

        return pages;
    }
}
