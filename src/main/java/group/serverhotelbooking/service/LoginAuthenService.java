package group.serverhotelbooking.service;

import group.serverhotelbooking.entity.UserEntity;
import group.serverhotelbooking.payload.request.LoginRequest;
import group.serverhotelbooking.repository.LoginRepository;
import group.serverhotelbooking.service.imp.LoginAuthenServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginAuthenService implements LoginAuthenServiceImp {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean authenticate(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        UserEntity userEntity = loginRepository.findByEmail(email);

        if(userEntity != null){
            boolean passwordMatches = passwordEncoder.matches(password,userEntity.getPassword());

            if (passwordMatches){
                return true;
            }else {
                System.out.println("password is not correct");
                return false;
            }
        }else {
            System.out.println("no user entity found");
            return false;
        }

    }
}
