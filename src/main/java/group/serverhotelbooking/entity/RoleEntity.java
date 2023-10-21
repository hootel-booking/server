package group.serverhotelbooking.entity;

import javax.persistence.*;
import java.util.List;

@Entity (name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column (name = "name")
    private String name;

    @OneToMany (mappedBy = "roleEntity")
    private List<UserEntity> userEntityList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserEntity> getUserEntityList() {
        return userEntityList;
    }

    public void setUserEntityList(List<UserEntity> userEntityList) {
        this.userEntityList = userEntityList;
    }
}
