package group.serverhotelbooking.service;

import group.serverhotelbooking.entity.BlogEntity;
import group.serverhotelbooking.payload.response.BlogResponse;
import group.serverhotelbooking.payload.response.UserResponse;
import group.serverhotelbooking.repository.BlogRepository;
import group.serverhotelbooking.service.imp.BlogServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService implements BlogServiceImp {
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public BlogResponse showBlog(int id) {
        Optional<BlogEntity> blogEntityOptional = blogRepository.findById(id);

        if (blogEntityOptional.isPresent()) {
            BlogEntity blogEntity = blogEntityOptional.get();

            BlogResponse blogResponse = new BlogResponse();
            blogResponse.setId(blogEntity.getId());
            blogResponse.setTitle(blogEntity.getTitle());
            blogResponse.setCreateDate(blogEntity.getCreateDate());
            blogResponse.setContent(blogEntity.getContent());
            blogResponse.setUrlMainImage(blogEntity.getUrlMainImage());
            blogResponse.setNameMainImage(blogEntity.getNameMainImage());

            UserResponse userResponse = new UserResponse();
            userResponse.setFirstname(blogEntity.getUserEntity().getFirstname());
            userResponse.setLastName(blogEntity.getUserEntity().getLastName());
            userResponse.setAvatar(blogEntity.getUserEntity().getAvatar());

            blogResponse.setUserResponse(userResponse);

            return blogResponse;
        }
            return null; // Handle case where blog with given id is not found
    }

    @Override
    public List<BlogResponse> showAllBlogs() {
        List<BlogEntity> blogEntityList = blogRepository.findAll();
        List<BlogResponse> blogResponseList = new ArrayList<>();

        for (BlogEntity blogEntity : blogEntityList) {
            BlogResponse blogResponse = new BlogResponse();
            blogResponse.setId(blogEntity.getId());
            blogResponse.setTitle(blogEntity.getTitle());
            blogResponse.setCreateDate(blogEntity.getCreateDate());
            blogResponse.setContent(blogEntity.getContent());
            blogResponse.setUrlMainImage(blogEntity.getUrlMainImage());
            blogResponse.setNameMainImage(blogEntity.getNameMainImage());

            blogResponseList.add(blogResponse);
        }
        return blogResponseList;
    }


}
