package group.serverhotelbooking.payload.response;

import lombok.Data;

import java.util.Date;
@Data
public class BlogResponse {
    private int id;
    private String title;
    private Date createDate;
    private String content;
    private String urlMainImage;
    private String nameMainImage;
    private UserResponse userResponse;

}