package group.serverhotelbooking.service;

import group.serverhotelbooking.entity.ImageEntity;
import group.serverhotelbooking.entity.RoomEntity;
import group.serverhotelbooking.payload.response.ImageResponse;
import group.serverhotelbooking.payload.response.RoomResponse;
import group.serverhotelbooking.repository.ImageRepository;
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
import java.util.Optional;

@Service
public class RoomService implements RoomServiceImp {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ImageRepository imageRepository;

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
            roomTemp.setImage(room.getMainImage());
            roomTemp.setDiscount(room.getDiscount());

            roomResponses.add(roomTemp);
        }

        Page<RoomResponse> pages = new PageImpl<>(roomResponses, pageable, rooms.getTotalElements());

        return pages;
    }

    @Override
    public RoomResponse getRoomById(int id) {
        RoomResponse roomResponse = new RoomResponse();
        Optional<RoomEntity> room = roomRepository.findById(id);

        List<ImageResponse> imageResponseList = new ArrayList<>();
        List<ImageEntity> imageEntityList = imageRepository.findByIdRoom(id);

        if (room.isPresent()) {
            RoomEntity roomEntity = room.get();
            roomResponse.setId(roomEntity.getId());
            roomResponse.setName(roomEntity.getName());
            roomResponse.setPrice(roomEntity.getPrice());
            roomResponse.setNameType(roomEntity.getType().getName());
            roomResponse.setSquare(roomEntity.getSize().getSquare());
            roomResponse.setImage(roomEntity.getMainImage());
            roomResponse.setDescription(roomEntity.getDescription());
            roomResponse.setDiscount(roomEntity.getDiscount());

            for (ImageEntity image : imageEntityList) {
                ImageResponse imageResponse = new ImageResponse();
                imageResponse.setId(image.getId());
                imageResponse.setName(image.getName());

                imageResponseList.add(imageResponse);
            }
            roomResponse.setImages(imageResponseList);
        }

        return roomResponse;
    }
}
