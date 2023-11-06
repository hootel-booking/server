package group.serverhotelbooking.payload.response;

import lombok.Data;

import java.util.Date;
@Data
public class BlogResponse {
    private int id;
    private String title;
    private Date createDate;
    private String content;
    private String nameMainImage;
    private UserResponse userResponse;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNameMainImage() {
        return nameMainImage;
    }

    public void setNameMainImage(String nameMainImage) {
        this.nameMainImage = nameMainImage;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }
}