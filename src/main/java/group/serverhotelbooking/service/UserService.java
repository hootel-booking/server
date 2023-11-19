package group.serverhotelbooking.service;

import group.serverhotelbooking.entity.BankAccountEntity;
import group.serverhotelbooking.entity.RoleEntity;
import group.serverhotelbooking.entity.UserEntity;
import group.serverhotelbooking.payload.request.UserRequest;
import group.serverhotelbooking.payload.response.UserResponse;
import group.serverhotelbooking.repository.BankAccountRepository;
import group.serverhotelbooking.repository.UserRepository;
import group.serverhotelbooking.service.imp.FileServiceImp;
import group.serverhotelbooking.service.imp.UserServiceImp;
import group.serverhotelbooking.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceImp {
    @Value("${root.folder}")
    private String rootFolder;

    @Autowired
    private FileServiceImp fileServiceImp;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Common common;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public List<UserResponse> getAllUser() {
        List<UserResponse> users = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findAll();

        for(UserEntity user : userEntities) {
            UserResponse userTemp = new UserResponse();
            userTemp.setId(user.getId());
            userTemp.setFirstname(user.getFirstname());
            userTemp.setLastName(user.getLastName());
            userTemp.setUserName(user.getUserName());
            userTemp.setEmail(user.getEmail());
            userTemp.setPhone(user.getPhone());
            userTemp.setRoleName(common.handleConvertRole(user.getRoleEntity().getName()));

            users.add(userTemp);
        }

        return users;
    }

    @Override
    public boolean editUserById(int id, UserRequest userRequest) {
        Optional<UserEntity> userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            UserEntity userTemp = userEntity.get();
            userTemp.setFirstname(userRequest.getFirstname());
            userTemp.setLastName(userRequest.getLastName());
            userTemp.setPhone(userRequest.getPhone());

            RoleEntity role = new RoleEntity();
            role.setId(userRequest.getIdRole());
            userTemp.setRoleEntity(role);

            try {
                userRepository.save(userTemp);
                return true;
            } catch (Exception ex) {
                System.out.println("Error " + ex);
                return false;
            }
        }

        return false;
    }


    @Override
    public boolean deleteUserById(int id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception ex) {
            System.out.println("Error " + ex);
            return false;
        }
    }

    @Override
    public UserResponse getUserById(int id, String hostName) {
        UserResponse userResponse = new UserResponse();
        Optional<UserEntity> user = userRepository.findById(id);

        if (user.isPresent()) {
            final UserEntity userEntity = user.get();
            userResponse.setId(userEntity.getId());
            userResponse.setFirstname(userEntity.getFirstname());
            userResponse.setLastName(userEntity.getLastName());
            userResponse.setUserName(userEntity.getUserName());
            userResponse.setEmail(userEntity.getEmail());
            userResponse.setPhone(userEntity.getPhone());
            userResponse.setIdRole(userEntity.getRoleEntity().getId());
            userResponse.setAvatar(userEntity.getAvatar());

            if (userEntity.getBankAccountEntity() != null) {
                userResponse.setAccountNumber(userEntity.getBankAccountEntity().getAccountNumber());
                userResponse.setAmount(userEntity.getBankAccountEntity().getAmount());
            }
        }

        return userResponse;
    }
    @Override
    public boolean editProfile(int id, UserRequest userRequest, String pathFolderStore) throws Exception {
        Optional<UserEntity> user = userRepository.findById(id);

        if (user.isPresent()) {
            String filename = fileServiceImp.handleStoreImage(userRequest.getFile(), pathFolderStore);
            UserEntity userTemp = user.get();
            userTemp.setFirstname(userRequest.getFirstname());
            userTemp.setLastName(userRequest.getLastName());
            userTemp.setPhone(userRequest.getPhone());
            userTemp.setAvatar(filename);

            if (!userRequest.getAccountNumber().equals("")) {
                BankAccountEntity bankAccount = bankAccountRepository.checkAccountByIdUser(id, userRequest.getAccountNumber());

                if (userRequest.getTransferAmount() < 0) {
                    System.out.println("Error: số tiền chuyển khoản không hợp lệ");
                    return false;
                }

                BankAccountEntity bankAccountEntity = new BankAccountEntity();
                bankAccountEntity.setIdUser(id);
                bankAccountEntity.setAccountNumber(userRequest.getAccountNumber());

                // update
                if (bankAccount != null && bankAccount.getAccountNumber() != null) {
                    bankAccountEntity.setAmount(
                            userTemp.getBankAccountEntity().getAmount() + userRequest.getTransferAmount()
                    );
                    bankAccountEntity.setTransferDate(common.getCurrentDateTime());
                    userTemp.setBankAccountEntity(bankAccountEntity);
                } else {
                    // create
                    if (userRequest.getTransferAmount() > 0) {
                        bankAccountEntity.setAmount(userRequest.getTransferAmount());
                        bankAccountEntity.setTransferDate(common.getCurrentDateTime());
                    } else {
                        bankAccountEntity.setAmount(0);
                    }
                }

                try {
                    bankAccountRepository.save(bankAccountEntity);
                } catch (Exception ex) {
                    System.out.println("Error " + ex);
                    return false;
                }
            }

            try {
                userRepository.save(userTemp);
                return true;
            } catch (Exception ex) {
                System.out.println("Error " + ex);
                return false;
            }
        }

        return false;
    }

    @Override
    public Resource downloadUserFile(String fileName, String pathFolderStore) throws MalformedURLException {
        return fileServiceImp.loadImage(fileName, pathFolderStore);
    }
}
