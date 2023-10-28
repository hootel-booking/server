package group.serverhotelbooking.service.imp;

import group.serverhotelbooking.payload.request.CartRequest;
import group.serverhotelbooking.payload.response.CartResponse;

import java.util.List;

public interface CartServiceImp {
    List<CartResponse> getListCartByIdUser(int idUser);

    boolean insertRoom(CartRequest cartRequest);

    boolean deleteCartById(int id);

    int countRoomsInCartByIdUser(int idUser);

    int deleteCart(int idUser);
}
