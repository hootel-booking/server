package group.serverhotelbooking.service;

import group.serverhotelbooking.entity.ImageEntity;
import group.serverhotelbooking.entity.RoomEntity;
import group.serverhotelbooking.entity.SizeEntity;
import group.serverhotelbooking.entity.TypeEntity;
import group.serverhotelbooking.payload.request.RoomRequest;
import group.serverhotelbooking.payload.response.ImageResponse;
import group.serverhotelbooking.payload.response.RoomResponse;
import group.serverhotelbooking.payload.response.SizeResponse;
import group.serverhotelbooking.payload.response.TypeResponse;
import group.serverhotelbooking.repository.ImageRepository;
import group.serverhotelbooking.repository.ModifyRoomRepository;
import group.serverhotelbooking.repository.RoomRepository;
import group.serverhotelbooking.service.imp.RoomServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service

public class RoomService implements RoomServiceImp {
    @Value("${assets.root.folder}")
    private String folderRoot;
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ModifyRoomRepository modifyRoomRepository;

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
    public boolean addRoom(RoomRequest roomRequest, List <MultipartFile> multipartFile)  {
        try {
            Path root = Paths.get(folderRoot);
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
            String listImageName = "";
            for(MultipartFile file : multipartFile){
                Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                listImageName+= file.getOriginalFilename() + ",";

            }
            listImageName= listImageName.substring(0, listImageName.length()-1);
            RoomEntity roomEntity = new RoomEntity();

            roomEntity.setName(roomRequest.getName());
            roomEntity.setDiscount(roomRequest.getDiscount());
            roomEntity.setPrice(roomRequest.getPrice());

            Date now = new Date();
            roomEntity.setCreateDate(now);


            roomEntity.setMainImage(listImageName.trim());
            roomEntity.setDescription(roomRequest.getDescription());

            SizeEntity sizeEntity = new SizeEntity();
            sizeEntity.setId(roomRequest.getId_size());
            roomEntity.setSize(sizeEntity);
            TypeEntity typeEntity = new TypeEntity();
            typeEntity.setId(roomRequest.getId_type());
            roomEntity.setType(typeEntity);
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
    public boolean updateRoom(RoomRequest roomRequest) {
        try {
            Optional<RoomEntity> roomRequestOption = modifyRoomRepository.findById(roomRequest.getId());
            RoomEntity roomEntity = null;
            if (roomRequestOption.isPresent()) {
                roomEntity = roomRequestOption.get();
                roomEntity.setName(roomRequest.getName());
                roomEntity.setPrice(roomRequest.getPrice());
                roomEntity.setDiscount(roomRequest.getDiscount());
                roomEntity.setMainImage(roomRequest.getMainImage());
                roomEntity.setDescription(roomRequest.getDescription());

                Date now = new Date();

                // Kiểm tra xem phòng đã tồn tại hay chưa
                if (roomRequest.getId_size() != 0) {
                    roomEntity.setUpdateDate(now);
                    SizeEntity sizeEntity = new SizeEntity();
                    sizeEntity.setId(roomRequest.getId_size());
                    roomEntity.setSize(sizeEntity);
                } else {
                    roomEntity.setCreateDate(now);
                }
                if (roomRequest.getId_type() != 0) {
                    TypeEntity typeEntity = new TypeEntity();
                    typeEntity.setId(roomRequest.getId_type());
                    roomEntity.setType(typeEntity);
                }
                modifyRoomRepository.save(roomEntity);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<RoomResponse> getListRoom() {
        try {
            List<RoomEntity> roomEntityList = modifyRoomRepository.findAll();
            return
                    roomEntityList.stream().map(roomEntity -> RoomResponse.builder()
                            .id(roomEntity.getId())
                            .name(roomEntity.getName())
                            .price(roomEntity.getPrice())
                            .description(roomEntity.getDescription())
                            .createDate(roomEntity.getCreateDate())
                            .updateDate(roomEntity.getUpdateDate())
                            .mainImage(List.of(roomEntity.getMainImage().split(",")))
                            .discount(roomEntity.getDiscount())
                            .sizeResponse(new SizeResponse(0, roomEntity.getSize().getSquare()))
                            .typeResponse(new TypeResponse(0, roomEntity.getType().getName()))
                            .build()).toList();


        } catch (Exception e) {
            log.info("Exception " + e.getLocalizedMessage());
            return null;
        }
    }
}
