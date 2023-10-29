package group.serverhotelbooking.service.imp;

import group.serverhotelbooking.entity.BlogEntity;
import group.serverhotelbooking.entity.CommentEntity;
import group.serverhotelbooking.payload.response.BlogResponse;

import java.util.List;

public interface BlogServiceImp {
    BlogResponse showBlog(int id);
    List<BlogResponse> showAllBlogs();

}
