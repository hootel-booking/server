package group.serverhotelbooking.service;

import group.serverhotelbooking.entity.RoleEntity;
import group.serverhotelbooking.payload.response.RoleResponse;
import group.serverhotelbooking.repository.RoleRepository;
import group.serverhotelbooking.service.imp.RoleServiceImp;
import group.serverhotelbooking.util.ConvertRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService implements RoleServiceImp {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ConvertRole convertRole;

    @Override
    public List<RoleResponse> getRoles() {
        List<RoleResponse> roleResponses = new ArrayList<>();
        List<RoleEntity> roles = roleRepository.findAll();

        for (RoleEntity role : roles) {
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setId(role.getId());
            roleResponse.setName(convertRole.handleConvertRole(role.getName()));
            roleResponses.add(roleResponse);
        }

        return roleResponses;
    }
}
