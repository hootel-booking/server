package group.serverhotelbooking.controller;

import group.serverhotelbooking.constant.Constant;
import group.serverhotelbooking.service.imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/file")
public class FileController {
    @Autowired
    private FileServiceImp fileServiceImp;

    @GetMapping("/pathImage={pathImage}&fileName={fileName}")
    private ResponseEntity<?> getImage(@PathVariable String pathImage, @PathVariable String fileName) throws MalformedURLException {
        Resource resource = fileServiceImp.loadImage(fileName, pathImage);

         return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(resource);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        Map<String, String> map = new HashMap<>();
        try {
            String fileName = fileServiceImp.handleStoreImage(file, Constant.PATH_AVATARS);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            map.put("result", message);
            map.put("fileName", fileName);

            return ResponseEntity.status(HttpStatus.OK).body(map);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            map.put("result", message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(map);
        }
    }

}
