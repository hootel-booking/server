package group.serverhotelbooking.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {
    private int id;
    private String name;
    private double  price;
    private int discount;
    private Date createDate;
    private Date updateDate;
    private String mainImage;
    private String description;
    private int id_size;
    private int id_type;

}
