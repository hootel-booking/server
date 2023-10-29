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

}
