package com.blog.Controller;

import com.blog.payload.CommentDto;
import com.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService=commentService;
    }
@PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestParam("postId")long postId,
                                                    @RequestBody CommentDto commentDto)
    {
        CommentDto comment = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String > deleteComment(@PathVariable("commentId") long commentId)
    {
        commentService.deleteComment(commentId);

        return new ResponseEntity<>("comment deleted!!",HttpStatus.OK);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentByPostId(@PathVariable long postId)
    {
        List<CommentDto> cm=commentService.getCommentByPostId(postId);
        return new ResponseEntity<>(cm,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments()
    {
        List<CommentDto> cm=commentService.getAllComments();
        return new ResponseEntity<>(cm,HttpStatus.OK);
    }
}
