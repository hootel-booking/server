package group.serverhotelbooking.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity (name = "blog")
public class BlogEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column (name = "title")
    private String title;
    @Column (name = "start_date")
    private Date startDate;
    @Column (name = "image")
    private String image;
    @Column (name = "content")
    private String content;
    @ManyToOne
    @JoinColumn (name = "id_user")
    private UserEntity userEntity;

    @OneToMany (mappedBy = "blogEntity")
    private List<CommentEntity> commentEntity;


}
