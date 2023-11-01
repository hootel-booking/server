package group.serverhotelbooking.service;

import group.serverhotelbooking.entity.UserEntity;
import group.serverhotelbooking.payload.request.SignUpRequest;
import group.serverhotelbooking.repository.UserRepository;
import group.serverhotelbooking.service.imp.SignupServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignupService implements SignupServiceImp {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean insertUser(SignUpRequest signUpRequest) {

        boolean insertIsSuccess = false;

        UserEntity userEntity = new UserEntity();

        userEntity.setUserName(signUpRequest.getUsername());
        userEntity.setPhone(signUpRequest.getPhone());
        userEntity.setEmail(signUpRequest.getEmail());
        userEntity.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        try{
            userRepository.save(userEntity);
            insertIsSuccess = true;
        }catch (Exception exception){
            System.out.println("add failed");
            insertIsSuccess = false;
        }
        return insertIsSuccess;
    }
}
