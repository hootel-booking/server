package group.serverhotelbooking.service;

import group.serverhotelbooking.service.imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileService implements FileServiceImp {
    @Value("${root.folder}")
    private String rootFolder;

    @Override
    public String handleStoreImage(MultipartFile file, String pathFolderStore) throws IOException {
        if (!file.getOriginalFilename().equals("")) {
            String pathFolder = rootFolder + pathFolderStore;

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            String pathImage = pathFolder + File.separator + fileName;
            Path path = Paths.get(pathFolder);
            Path pathImageCopy = Paths.get(pathImage);

            try (InputStream inputStream = file.getInputStream()) {
                if (!Files.exists(path)) {
                    Files.createDirectory(path);
                }
                Files.copy(inputStream, pathImageCopy, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw e;
            }

            return fileName;
        }
        return null;
    }

    @Override
    public Resource loadImage(String fileName, String pathFolderStore) throws MalformedURLException {
        String pathFolder = rootFolder + "\\" + pathFolderStore;
        Path path = Paths.get(pathFolder);
        Path pathImage = path.resolve(fileName);
        Resource resource = new UrlResource(pathImage.toUri());

        if (resource.exists()) {
            return resource;
        }
        return null;
    }
}
