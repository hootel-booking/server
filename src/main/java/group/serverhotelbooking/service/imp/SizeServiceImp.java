package group.serverhotelbooking.service.imp;

import group.serverhotelbooking.payload.response.SizeResponse;

import java.util.List;

public interface SizeServiceImp {
    List<SizeResponse> getSizes();
}
