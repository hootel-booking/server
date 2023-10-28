package group.serverhotelbooking.service.imp;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;

public interface FileServiceImp {
    void handleStoreImage(MultipartFile file, String pathFolderStore) throws Exception;

    Resource loadImage(String fileName, String pathFolderStore) throws MalformedURLException;
}
