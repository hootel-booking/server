package group.serverhotelbooking.service;

import group.serverhotelbooking.entity.BankAccountEntity;
import group.serverhotelbooking.payload.request.BankAccountRequest;
import group.serverhotelbooking.repository.BankAccountRepository;
import group.serverhotelbooking.service.imp.BankAccountServiceImp;
import group.serverhotelbooking.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankAccountService implements BankAccountServiceImp {
    @Autowired
    private Common common;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public boolean checkAccountNumber(int idUser, BankAccountRequest bankAccountRequest) {
        BankAccountEntity bankAccountEntity = bankAccountRepository.checkAccountByIdUser(idUser, bankAccountRequest.getAccountNumber());
        boolean result = bankAccountEntity != null && bankAccountEntity.getAccountNumber() != null ? true : false;

        return result;
    }

    @Override
    public int transfer(int idUser, BankAccountRequest bankAccountRequest) {
        Optional<BankAccountEntity> bankAccountEntity = Optional.ofNullable(
                bankAccountRepository.checkAccountByIdUser(
                        idUser,
                        bankAccountRequest.getAccountNumber()
                )
        );

        if (bankAccountEntity.isPresent()) {
            BankAccountEntity bankAccount = bankAccountEntity.get();
            double currentAmount = bankAccount.getAmount() + bankAccountRequest.getTransferAmount();
            bankAccount.setAmount(currentAmount);
            bankAccount.setTransferDate(common.getCurrentDateTime());

            try {
                bankAccountRepository.save(bankAccount);
                return 0;
            } catch (Exception ex) {
                System.out.println("Error " + ex);
                return -1;
            }
        }

        return -1;
    }

    @Override
    public int deposit(int idUser, BankAccountRequest bankAccountRequest) {
        Optional<BankAccountEntity> bankAccountEntity = Optional.ofNullable(
                bankAccountRepository.checkAccountByIdUser(
                        idUser,
                        bankAccountRequest.getAccountNumber()
                )
        );

        if (bankAccountEntity.isPresent()) {
            BankAccountEntity bankAccount = bankAccountEntity.get();

            if (bankAccount.getAmount() < bankAccountRequest.getDepositAmount()) {
                return 1;
            }

            double currentAmount = bankAccount.getAmount() - bankAccountRequest.getDepositAmount();
            bankAccount.setAmount(currentAmount);
            bankAccount.setDepositDate(common.getCurrentDateTime());

            try {
                bankAccountRepository.save(bankAccount);
                return 0;
            } catch (Exception ex) {
                System.out.println("Error " + ex);
                return -1;
            }
        }

        return -1;
    }
}
