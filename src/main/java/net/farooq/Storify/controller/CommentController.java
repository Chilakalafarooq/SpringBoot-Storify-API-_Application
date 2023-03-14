package net.farooq.Storify.controller;

import net.farooq.Storify.entites.Comment;
import net.farooq.Storify.playload.CommentDto;
import net.farooq.Storify.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private CommentService commentService;
    private ModelMapper mapper;

    public CommentController(CommentService commentService, ModelMapper mapper) {
        this.commentService = commentService;
        this.mapper = mapper;
    }

    ///http://localhost:3306/api/comment
    @PostMapping
    public ResponseEntity<CommentDto> createAllComment(@RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.
                createAllComment(commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/farooq")

    public List<Comment> getallcomments() {
        return commentService.getallcomments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getcommentbyid(@PathVariable("id") long id) {
        return new ResponseEntity<>(commentService.getcommentbyid(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updatecommentbyid(@PathVariable("id") long id, @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.updatecommentbyid(commentDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> Deletecommentbyid(@PathVariable("id") long id) {
        commentService.deletePostById(id);
        return new ResponseEntity<>("Comment entity deleted successfully.", HttpStatus.OK);


    }


    @GetMapping("/page")
    public ResponseEntity<List<Comment>> getPaginatedPosts(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<Comment> paginatedComment = commentService.getPaginatedComment(pageNo, pageSize, sortBy);
        return ResponseEntity.ok(paginatedComment);
    }

}
