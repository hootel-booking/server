package group.serverhotelbooking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@Entity(name="comment")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    @Column (name = "content")
    private String content;
    @Column (name = "like")
    private int like;
    @Column (name =  "email")
    private String email;
    @Column (name = "name")
    private String name;
    @Column (name = "create_date")
    private Date createDate;

    @ManyToOne
    @JoinColumn (name ="id_blog")
    private BlogEntity blogEntity;

}
