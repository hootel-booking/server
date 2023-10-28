package group.serverhotelbooking.controller;

import group.serverhotelbooking.service.imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@CrossOrigin
@RestController
@RequestMapping(value = "/file")
public class FileController {
    @Autowired
    private FileServiceImp fileServiceImp;

    @GetMapping("")
    private ResponseEntity<?> getImage(@RequestBody String fileName, @RequestBody String pathImage) throws MalformedURLException {
        Resource resource = fileServiceImp.loadImage(fileName, pathImage);

         return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(resource);
    }
}
