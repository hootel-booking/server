package group.serverhotelbooking.service;

import group.serverhotelbooking.entity.TypeEntity;
import group.serverhotelbooking.payload.response.TypeResponse;
import group.serverhotelbooking.repository.TypeRepository;
import group.serverhotelbooking.service.imp.TypeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeService implements TypeServiceImp {
    @Autowired
    private TypeRepository typeRepository;

    @Override
    public List<TypeResponse> getTypes() {
        List<TypeEntity> listTypes = typeRepository.findAll();
        List<TypeResponse> typeResponses = new ArrayList<>();

        for(TypeEntity type : listTypes) {
            TypeResponse response = new TypeResponse();
            response.setId(type.getId());
            response.setName(type.getName());
            typeResponses.add(response);
        }

        return typeResponses;
    }
}
