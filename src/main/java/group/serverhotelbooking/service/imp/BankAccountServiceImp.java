package group.serverhotelbooking.service.imp;

import group.serverhotelbooking.payload.request.BankAccountRequest;

public interface BankAccountServiceImp {
    int transfer(int idUser, BankAccountRequest bankAccountRequest);
    int deposit(int idUser, BankAccountRequest bankAccountRequest);
}
