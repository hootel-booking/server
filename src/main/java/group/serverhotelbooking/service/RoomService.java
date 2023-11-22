package group.serverhotelbooking.service;

import group.serverhotelbooking.entity.ImageEntity;
import group.serverhotelbooking.entity.RoomEntity;
import group.serverhotelbooking.entity.SizeEntity;
import group.serverhotelbooking.entity.TypeEntity;
import group.serverhotelbooking.payload.request.RoomRequest;
import group.serverhotelbooking.payload.response.ImageResponse;
import group.serverhotelbooking.payload.response.RoomResponse;
import group.serverhotelbooking.repository.ImageRepository;
import group.serverhotelbooking.repository.ModifyRoomRepository;
import group.serverhotelbooking.repository.RoomRepository;
import group.serverhotelbooking.service.imp.FileServiceImp;
import group.serverhotelbooking.service.imp.RoomServiceImp;
import group.serverhotelbooking.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService implements RoomServiceImp {
    @Autowired
    private Common common;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ModifyRoomRepository modifyRoomRepository;

    @Autowired
    private FileServiceImp fileServiceImp;

    @Override
    public Page<RoomResponse> getAllRoom(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RoomEntity> rooms = roomRepository.findAll(pageable);
        List<RoomResponse> roomResponses = new ArrayList<>();

        for (RoomEntity room : rooms) {
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
            roomResponse.setIdSize(roomEntity.getSize().getId());
            roomResponse.setIdType(roomEntity.getType().getId());

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

    @Override
    public boolean addRoom(RoomRequest roomRequest, String pathFolderStore) throws IOException {
        String fileName = fileServiceImp.handleStoreImage(roomRequest.getFile(), pathFolderStore);

        RoomEntity roomEntity = new RoomEntity();

        roomEntity.setName(roomRequest.getName());
        roomEntity.setDiscount(roomRequest.getDiscount());
        roomEntity.setPrice(roomRequest.getPrice());
        roomEntity.setCreateDate(common.getCurrentDateTime());
        roomEntity.setDescription(roomRequest.getDescription());
        roomEntity.setMainImage(fileName);

        SizeEntity sizeEntity = new SizeEntity();
        sizeEntity.setId(roomRequest.getId_size());
        roomEntity.setSize(sizeEntity);

        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setId(roomRequest.getId_type());
        roomEntity.setType(typeEntity);

        try {
            modifyRoomRepository.save(roomEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
            return false;
        }
    }


    @Override
    public boolean deleteRoom(int id) {
        try {
            System.out.println("giá trị id " + id);
            modifyRoomRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println("can't delete room " + id);
            return false;
        }
    }

    @Override
    public boolean updateRoom(int id, RoomRequest roomRequest, String pathFolderStore) throws IOException {
        Optional<RoomEntity> roomRequestOption = modifyRoomRepository.findById(id);

        if (roomRequestOption.isPresent()) {
            String filename = fileServiceImp.handleStoreImage(roomRequest.getFile(), pathFolderStore);
            RoomEntity roomEntity = roomRequestOption.get();

            roomEntity.setName(roomRequest.getName());
            roomEntity.setPrice(roomRequest.getPrice());
            roomEntity.setDiscount(roomRequest.getDiscount());
            roomEntity.setMainImage(filename);
            roomEntity.setDescription(roomRequest.getDescription());
            roomEntity.setUpdateDate(common.getCurrentDateTime());

            SizeEntity sizeEntity = new SizeEntity();
            sizeEntity.setId(roomRequest.getId_size());
            roomEntity.setSize(sizeEntity);

            TypeEntity typeEntity = new TypeEntity();
            typeEntity.setId(roomRequest.getId_type());
            roomEntity.setType(typeEntity);

            try {
                roomRepository.save(roomEntity);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }

    @Override
    public List<RoomResponse> getRooms() {
        List<RoomEntity> rooms = roomRepository.findAll();
        List<RoomResponse> roomResponses = new ArrayList<>();

        for(RoomEntity room : rooms) {
            RoomResponse response = new RoomResponse();
            response.setId(room.getId());
            response.setName(room.getName());
            response.setPrice(room.getPrice());
            response.setDiscount(room.getDiscount());
            response.setCreateDate(room.getCreateDate());
            response.setNameType(room.getType().getName());
            response.setSquare(room.getSize().getSquare());
            roomResponses.add(response);
        }

        return roomResponses;
    }

    @Override
    public boolean findRoomByName(String roomName) {
        List<RoomEntity> rooms = roomRepository.findByName(roomName);
        return rooms.size() > 0;
    }
}
