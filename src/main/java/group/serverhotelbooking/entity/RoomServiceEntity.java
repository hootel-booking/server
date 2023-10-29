package group.serverhotelbooking.entity;

import group.serverhotelbooking.entity.keys.RoomServiceKeys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity (name = "roomservice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomServiceEntity {
    @EmbeddedId
    private RoomServiceKeys keys;

    @ManyToOne
    @JoinColumn  (name = "id_service", insertable = false, updatable = false)
    private ServiceEntity serviceEntity;

    @ManyToOne
    @JoinColumn (name = "id_room", insertable = false, updatable =false)
    private RoomEntity room;
}
