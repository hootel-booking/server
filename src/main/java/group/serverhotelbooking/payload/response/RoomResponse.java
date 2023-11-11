package group.serverhotelbooking.payload.response;

import group.serverhotelbooking.entity.ImageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {
    private int id;
    private String name;
    private double price;
    private int square;
    private String nameType;
    private String image;
    private String description;
    private int discount;
    private List<ImageResponse> images;

    private Date createDate;
    private Date updateDate;
    private  List <String> mainImage;

    private SizeResponse sizeResponse;
    private TypeResponse typeResponse;


}
