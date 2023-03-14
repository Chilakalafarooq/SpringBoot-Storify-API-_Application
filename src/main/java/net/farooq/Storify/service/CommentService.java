package net.farooq.Storify.service;

import net.farooq.Storify.entites.Comment;
import net.farooq.Storify.entites.Post;
import net.farooq.Storify.playload.CommentDto;
import net.farooq.Storify.playload.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentDto createAllComment(CommentDto commentDto);
    Comment getcommentbyid(long id);
    CommentDto updatecommentbyid(CommentDto commentDto, long id);
    void deletePostById(long id);
    List<Comment> getallcomments();

    //CommentResponse getAllcomments(int pageNo, int pageSize, String sortBy, String sortDir);

    List<Comment> getPaginatedComment(Integer pageNo, Integer pageSize, String sortBy);
}
