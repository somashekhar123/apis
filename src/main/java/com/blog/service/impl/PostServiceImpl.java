package com.blog.service.impl;


import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;
//constrctor based Dependacy injection
    public PostServiceImpl(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @Override
    public PostDto createPost(PostDto postDto)
    {
Post post=new Post();
post.setTitle(postDto.getTitle());
post.setContent(postDto.getContent());
post.setDescription(postDto.getDescription());
        Post savedPost = postRepo.save(post);

        PostDto dto=new PostDto();
        dto.setId(savedPost.getId());
         dto.setContent(savedPost.getContent());
         dto.setDescription(savedPost.getDescription());
         dto.setTitle(savedPost.getTitle());


        return dto;
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not foud with id:" + id)
        );
        postRepo.deleteById(id);
    }

    @Override
    public List<PostDto> getAllPosts(int pageno, int pagesize, String sortby, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortby).ascending() : Sort.by(sortby).descending();
        Pageable pageable=PageRequest.of(pageno,pagesize, sort);
        Page<Post> all = postRepo.findAll(pageable);
        List<Post> posts = all.getContent();
        List<PostDto> dtos = posts.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

        return dtos;
    }

    @Override
    public PostDto updatePost(long postId, PostDto postDto) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post not found with id:" + postId));
         post.setTitle(postDto.getTitle());
         post.setContent(postDto.getContent());
         post.setDescription(postDto.getDescription());

        Post saved = postRepo.save(post);
        PostDto dto = mapToDto(saved);
        return dto;
    }


    public PostDto mapToDto(Post posts)
    {
     PostDto dto=new PostDto();
      dto.setId(posts.getId());
      dto.setTitle(posts.getTitle());
      dto.setDescription(posts.getDescription());
      dto.setContent(posts.getContent());
        return dto;
    }
}
