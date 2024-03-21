package com.blog.service;

import com.blog.payload.PostDto;

import java.util.List;

public interface PostService {
    public PostDto createPost(PostDto postDto);

    void deletePost(long id);


    List<PostDto> getAllPosts(int pageno, int pagesize, String sortby, String sortDir);


   PostDto updatePost(long postId, PostDto postDto);
}
