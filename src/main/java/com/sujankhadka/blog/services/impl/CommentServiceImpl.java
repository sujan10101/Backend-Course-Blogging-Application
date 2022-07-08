package com.sujankhadka.blog.services.impl;

import com.sujankhadka.blog.entities.Comment;
import com.sujankhadka.blog.entities.Post;
import com.sujankhadka.blog.exceptions.ResourceNotFoundException;
import com.sujankhadka.blog.payloads.CommentDto;
import com.sujankhadka.blog.repositories.CommentRepository;
import com.sujankhadka.blog.repositories.PostRepository;
import com.sujankhadka.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post= this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","post id",postId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment= this.commentRepository.save(comment);
        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment= this.commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","comment id",commentId));
        this.commentRepository.delete(comment);
    }
}
