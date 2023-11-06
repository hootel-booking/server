package group.serverhotelbooking.controller;

import group.serverhotelbooking.payload.BaseResponse;
import group.serverhotelbooking.payload.response.BlogResponse;
import group.serverhotelbooking.service.imp.BlogServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogServiceImp blogServiceImp;

    @GetMapping("")
    public ResponseEntity<?> getAllBlog(){
        List<BlogResponse> blogResponses =  blogServiceImp.showAllBlogs();
        BaseResponse baseResponse = new BaseResponse(200, "show all blogs successfully", blogResponses);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/details")
    public ResponseEntity<?> getBlogDetails(@RequestParam int id) {
        BlogResponse blogResponse = blogServiceImp.showBlog(id);
        BaseResponse baseResponse = new BaseResponse(200, "show blog detail successfully", blogResponse);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
