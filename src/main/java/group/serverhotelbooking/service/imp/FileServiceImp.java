package group.serverhotelbooking.service.imp;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface FileServiceImp {
    String handleStoreImage(MultipartFile file, String pathFolderStore) throws IOException;

    Resource loadImage(String fileName, String pathFolderStore) throws MalformedURLException;
}
