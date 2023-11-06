package group.serverhotelbooking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity (name = "blog")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class BlogEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column (name = "title")
    private String title;
    @Column (name = "create_date")
    private Date createDate;
    @Column (name = "url_main_image")
    private String urlMainImage;

    @Column (name = "name_main_image")
    private String nameMainImage;

    @Column (name = "content", columnDefinition = "TEXT")
    private String content;
    @ManyToOne
    @JoinColumn (name = "id_user")
    private UserEntity userEntity;

    @OneToMany (mappedBy = "blogEntity")
    private List<CommentEntity> commentEntity;

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

    public String getUrlMainImage() {
        return urlMainImage;
    }

    public void setUrlMainImage(String urlMainImage) {
        this.urlMainImage = urlMainImage;
    }

    public String getNameMainImage() {
        return nameMainImage;
    }

    public void setNameMainImage(String nameMainImage) {
        this.nameMainImage = nameMainImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public List<CommentEntity> getCommentEntity() {
        return commentEntity;
    }

    public void setCommentEntity(List<CommentEntity> commentEntity) {
        this.commentEntity = commentEntity;
    }
}
