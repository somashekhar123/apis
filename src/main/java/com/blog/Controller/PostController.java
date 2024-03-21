package com.blog.Controller;

import com.blog.entity.Post;
import com.blog.payload.PostDto;
import com.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
   private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('Admin')")
    @PostMapping
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDto postdto, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.createPost(postdto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String > deletePost(@PathVariable long id)
    {
        postService.deletePost(id);
        return new ResponseEntity<>("Successfully Delete id",HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(@RequestParam(name="pageNo",defaultValue = "0",required = false)int pageno,
                                                     @RequestParam(name="pageSize",defaultValue = "3",required = false)int pagesize,
                                                     @RequestParam(name="sortBy",defaultValue = "id",required = false)String sortby,
                                                     @RequestParam(name="sotrDir",defaultValue = "asc",required = false) String sortDir)
    {
         List<PostDto> dtos=postService.getAllPosts(pageno,pagesize,sortby,sortDir
         );
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('Admin')")
@PutMapping
    public ResponseEntity<PostDto> updatePost(@RequestParam("postId")long postId,@RequestBody PostDto postDto)
    {
        PostDto dto = postService.updatePost(postId, postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}
