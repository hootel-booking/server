package group.serverhotelbooking.service.imp;

import group.serverhotelbooking.payload.response.StatusResponse;

import java.util.List;

public interface StatusServiceImp {
    List<StatusResponse> getAllStatus();
}
