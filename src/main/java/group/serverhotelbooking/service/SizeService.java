package group.serverhotelbooking.service;

import group.serverhotelbooking.entity.SizeEntity;
import group.serverhotelbooking.payload.response.SizeResponse;
import group.serverhotelbooking.repository.SizeRepository;
import group.serverhotelbooking.service.imp.SizeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SizeService implements SizeServiceImp {
    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public List<SizeResponse> getSizes() {
        List<SizeEntity> listSize = sizeRepository.findAll();
        List<SizeResponse> sizeResponses = new ArrayList<>();

        for(SizeEntity size : listSize) {
            SizeResponse response = new SizeResponse();
            response.setId(size.getId());
            response.setSquare(size.getSquare());
            sizeResponses.add(response);
        }

        return sizeResponses;
    }
}
