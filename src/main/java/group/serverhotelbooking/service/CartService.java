package group.serverhotelbooking.service;

import group.serverhotelbooking.entity.CartEntity;
import group.serverhotelbooking.entity.RoomEntity;
import group.serverhotelbooking.entity.UserEntity;
import group.serverhotelbooking.payload.request.CartRequest;
import group.serverhotelbooking.payload.response.CartResponse;
import group.serverhotelbooking.repository.CartRepository;
import group.serverhotelbooking.repository.RoomRepository;
import group.serverhotelbooking.repository.UserRepository;
import group.serverhotelbooking.service.imp.CartServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService implements CartServiceImp {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CartResponse> getListCartByIdUser(int idUser) {
        List<CartEntity> cartEntities = cartRepository.findByIdUser(idUser);
        List<CartResponse> cartResponses = new ArrayList<>();

        for(CartEntity cart : cartEntities) {
            CartResponse cartTemp = new CartResponse();
            cartTemp.setId(cart.getId());
            cartTemp.setPrice(cart.getRoom().getPrice());
            cartTemp.setNameRoom(cart.getRoom().getName());

            cartResponses.add(cartTemp);
       }

        return cartResponses;
    }

    @Override
    public boolean insertRoom(CartRequest cartRequest) {
        CartEntity cart = new CartEntity();

        Optional<RoomEntity> roomEntity = roomRepository.findById(cartRequest.getIdRoom());
        Optional<UserEntity> userEntity = userRepository.findById(cartRequest.getIdUser());

        CartEntity roomBooked = cartRepository.findByIdRoomAndIdUser(cartRequest.getIdUser(), cartRequest.getIdRoom());

        if (roomEntity.isPresent() && userEntity.isPresent() && roomBooked == null) {
            cart.setDelete(false);
            cart.setRoom(roomEntity.get());
            cart.setUser(userEntity.get());

            try {
                cartRepository.save(cart);
                return true;
            } catch (Exception ex) {
                System.out.println("Error " + ex);
                return false;
            }
        }

        return false;
    }

    @Override
    public boolean deleteCartById(int id) {
        try {
            cartRepository.deleteById(id);
            return true;
        } catch (Exception ex) {
            System.out.println("Error " + ex);
            return false;
        }
    }

    @Override
    public int countRoomsInCartByIdUser(int idUser) {
        try {
            int number = cartRepository.countRoomsInCart(idUser);
            return number;
        } catch (Exception ex) {
            System.out.println("Error " + ex);
            return 0;
        }
    }

    @Override
    public int deleteCart(int idUser) {
        try {
            cartRepository.deleteCart(idUser);
            return 0;
        } catch (Exception ex) {
            System.out.println("Error " + ex);
            return -1;
        }
    }
}
