package group.serverhotelbooking.controller;

import group.serverhotelbooking.payload.BaseResponse;
import group.serverhotelbooking.payload.request.BankAccountRequest;
import group.serverhotelbooking.service.imp.BankAccountServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/bank_account")
public class BankAccountController {
    @Autowired
    private BankAccountServiceImp bankAccountServiceImp;

    // nạp tiền
    @PutMapping("/transfer/idUser={idUser}")
    private ResponseEntity<?> transfer(
            @PathVariable int idUser,
            @RequestBody BankAccountRequest bankAccountRequest
    ) {
        int result = bankAccountServiceImp.transfer(idUser, bankAccountRequest);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(result);
        baseResponse.setMessage("Transfer");
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    // thanh toán
    @PutMapping("/deposit/idUser={idUser}")
    private ResponseEntity<?> deposit(
            @PathVariable int idUser,
            @RequestBody BankAccountRequest bankAccountRequest
    ) {
        int result = bankAccountServiceImp.deposit(idUser, bankAccountRequest);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(result);
        baseResponse.setMessage("Deposit");
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
