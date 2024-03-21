package com.blog.service.impl;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id:" + postId)
        );

        Comment comment=new Comment();
     comment.setName(commentDto.getName());
     comment.setBody(commentDto.getBody());
     comment.setEmail(commentDto.getEmail());
     comment.setPost(post);

        Comment saved = commentRepository.save(comment);

        CommentDto dto=new CommentDto();
        dto.setId(saved.getId());
        dto.setName(saved.getName());
        dto.setBody(saved.getBody());
        dto.setEmail(saved.getEmail());
        return dto;
    }

    @Override
    public void deleteComment(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment Not Found with id:" + commentId)
        );
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {

        List<Comment> byPostId = commentRepository.findByPostId(postId)
                ;
        List<CommentDto> commentDto = byPostId.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return commentDto;
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDto> collect = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return collect;
    }

    public CommentDto mapToDto(Comment comment)
    {
        CommentDto dto=new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        return dto;
    }
}
