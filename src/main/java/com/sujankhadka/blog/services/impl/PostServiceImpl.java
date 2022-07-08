package com.sujankhadka.blog.services.impl;

import com.sujankhadka.blog.entities.Category;
import com.sujankhadka.blog.entities.Post;
import com.sujankhadka.blog.entities.User;
import com.sujankhadka.blog.exceptions.ResourceNotFoundException;
import com.sujankhadka.blog.payloads.PostDto;
import com.sujankhadka.blog.payloads.PostResponse;
import com.sujankhadka.blog.repositories.CategoryRepository;
import com.sujankhadka.blog.repositories.PostRepository;
import com.sujankhadka.blog.repositories.UserRepository;
import com.sujankhadka.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) {

        User user=this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User ","User id",userId));

        Category category=this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category ","category id",categoryId));
        Post post= this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost= this.postRepository.save(post);
        return this.modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post=this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post "," post id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = this.postRepository.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
       Post post=this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post "," post id", postId));
       this.postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy,String sortDir) {

        Sort sort=null;
        if(sortDir.equalsIgnoreCase("asc"))
        {
           sort= Sort.by(sortBy).ascending();
        }
        else
        {
            sort= Sort.by(sortBy).descending();
        }

        Pageable a= PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> pagePost = this.postRepository.findAll(a);

        List<Post> allPosts = pagePost.getContent();
//        List<Post> posts = this.postRepository.findAll();
        List<PostDto> postDtos= allPosts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return  postResponse;



    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post posts = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post id", postId));
        return this.modelMapper.map(posts,PostDto.class);

    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category cat=this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","category id",categoryId));
        List<Post> posts=this.postRepository.findByCategory(cat);

        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;


    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user=this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","user id",userId));

        List<Post> posts = this.postRepository.findByUser(user);

        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;


    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepository.searchByTitle("%"+keyword+"%");
        List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
