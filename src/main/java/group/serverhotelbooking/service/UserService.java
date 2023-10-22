package group.serverhotelbooking.service;

import group.serverhotelbooking.entity.UserEntity;
import group.serverhotelbooking.payload.request.UserRequest;
import group.serverhotelbooking.payload.response.UserResponse;
import group.serverhotelbooking.repository.UserRepository;
import group.serverhotelbooking.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceImp {
    @Autowired
    private UserRepository userRepository;

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
            userTemp.setAvatar(userRequest.getAvatar());

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
}
