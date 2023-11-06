package group.serverhotelbooking.service;

import group.serverhotelbooking.entity.StatusEntity;
import group.serverhotelbooking.payload.response.StatusResponse;
import group.serverhotelbooking.repository.StatusRepository;
import group.serverhotelbooking.service.imp.StatusServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatusService implements StatusServiceImp {
    @Autowired
    private StatusRepository statusRepository;

    @Override
    public List<StatusResponse> getAllStatus() {
        List<StatusEntity> statusEntities = statusRepository.findAll();
        List<StatusResponse> listStatus = new ArrayList<>();

        for(StatusEntity status : statusEntities) {
            StatusResponse response = new StatusResponse();
            response.setId(status.getId());
            response.setName(status.getName());
            listStatus.add(response);
        }

        return listStatus;
    }
}
