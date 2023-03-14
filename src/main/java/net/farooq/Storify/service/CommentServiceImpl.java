package net.farooq.Storify.service;

import net.farooq.Storify.entites.Comment;
import net.farooq.Storify.entites.Post;
import net.farooq.Storify.exception.DataNotFoundedException;
import net.farooq.Storify.playload.CommentDto;
import net.farooq.Storify.playload.CommentResponse;
import net.farooq.Storify.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl  implements  CommentService {
    private CommentRepository commentRepository;
    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createAllComment(CommentDto commentDto) {
        //Convert dto to entity
        Comment comment = mapToEntity(commentDto);
        Comment newcommentuser =commentRepository.save(comment);
        //Convert entity to dto
        CommentDto dto = mapToDto(newcommentuser);
        return dto;
    }

    @Override
    public Comment getcommentbyid(long id) {
        Comment com = commentRepository.findById(id).
                orElseThrow(() -> new DataNotFoundedException("Comment", "id", id));
        return com;
    }

    @Override
    public CommentDto updatecommentbyid(CommentDto commentDto, long id) {
        Comment existingUser = commentRepository.findById(id).orElseThrow(
                ()-> new DataNotFoundedException("Comment", "id", id));
        existingUser.setBody(commentDto.getBody());
        existingUser.setEmail(commentDto.getEmail());
        existingUser.setName(commentDto.getName());
        Comment updatedUser = commentRepository.save(existingUser);
        return mapToDto(updatedUser);
    }

    @Override
    public void deletePostById(long id) {
        Comment user = commentRepository.findById(id).orElseThrow(() ->
                new DataNotFoundedException("Post", "id", id));
        commentRepository.delete(user);
    }

    @Override
    public List<Comment> getallcomments() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> getPaginatedComment(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Comment> pagedResult = commentRepository.findAll(paging);
        return pagedResult.getContent();
    }

//    @Override
//    public CommentResponse getAllcomments(int pageNo, int pageSize, String sortBy, String sortDir) {
//        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
//                : Sort.by(sortBy).descending();
//
//        // create Pageable instance
//        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
//        Page<Comment> posts = commentRepository.findAll(pageable);
//        // get content for page object
//        List<Comment> listOfPosts = posts.getContent();
//        List<CommentDto> content= listOfPosts.stream().
//                map(Comment ->  mapToDto(Comment)).collect(Collectors.toList());
//        CommentResponse commentResponse = new CommentResponse();
//        commentResponse.setContent(content);
//        commentResponse.setPageNo(posts.getNumber());
//        commentResponse.setPageSize(posts.getSize());
//        commentResponse.setTotalElements(posts.getTotalElements());
//        commentResponse.setTotalPages(posts.getTotalPages());
//        commentResponse.setLast(posts.isLast());
//        return commentResponse;

    private CommentDto mapToDto(Comment newcommentuser) {
        CommentDto m = mapper.map(newcommentuser, CommentDto.class);
        return m;
    }
    private Comment mapToEntity(CommentDto commentDto) {
        Comment map = mapper.map(commentDto, Comment.class);
        return map;
    }

}