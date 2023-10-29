package group.serverhotelbooking.entity.keys;

import javax.persistence.Column;
import java.io.Serializable;

public class RoomServiceKeys implements Serializable {

    @Column (name = "id_service")
    private int id_service;

    @Column (name = "id_room")
    private int id_room;

    public RoomServiceKeys(int id_service, int id_room) {
        this.id_service = id_service;
        this.id_room = id_room;
    }
}
