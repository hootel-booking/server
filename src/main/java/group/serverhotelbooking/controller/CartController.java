package group.serverhotelbooking.controller;

import group.serverhotelbooking.payload.BaseResponse;
import group.serverhotelbooking.payload.request.CartRequest;
import group.serverhotelbooking.payload.response.CartResponse;
import group.serverhotelbooking.service.imp.CartServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/carts")
public class CartController {
    @Autowired
    private CartServiceImp cartServiceImp;

    @GetMapping("/idUser={idUser}")
    private ResponseEntity<?> getCarts(@PathVariable int idUser) {
        List<CartResponse> carts = cartServiceImp.getListCartByIdUser(idUser);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Get Carts By Id User");
        baseResponse.setData(carts);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("")
    private ResponseEntity<?> insertCart(@RequestBody CartRequest cartRequest) {
        boolean isSuccess = cartServiceImp.insertRoom(cartRequest);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(isSuccess);
        baseResponse.setMessage("Insert Room Into Cart");
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @DeleteMapping("/id={id}")
    private ResponseEntity<?> deleteCartById(@PathVariable int id) {
        boolean isSuccess = cartServiceImp.deleteCartById(id);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(isSuccess);
        baseResponse.setMessage("Delete Cart By Id");
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/badge-cart/id={idUser}")
    private ResponseEntity<?> countRoomsInCartByIdUser(@PathVariable int idUser) {
        int number = cartServiceImp.countRoomsInCartByIdUser(idUser);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(number);
        baseResponse.setMessage("Count Rooms In Cart");
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @DeleteMapping("/idUser={idUser}")
    private ResponseEntity<?> deleteCart(@PathVariable int idUser) {
        int number = cartServiceImp.deleteCart(idUser);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(number);
        baseResponse.setMessage("Delete Cart By Id User");
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
