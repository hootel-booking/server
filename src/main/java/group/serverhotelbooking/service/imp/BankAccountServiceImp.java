package group.serverhotelbooking.service.imp;

import group.serverhotelbooking.entity.BankAccountEntity;
import group.serverhotelbooking.payload.request.BankAccountRequest;

public interface BankAccountServiceImp {
    boolean checkAccountNumber(int idUser, BankAccountRequest bankAccountRequest);
    int transfer(int idUser, BankAccountRequest bankAccountRequest);
    int deposit(int idUser, BankAccountRequest bankAccountRequest);
}
