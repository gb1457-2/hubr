package ru.gb.hubr.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gb.hubr.api.dto.CommentDto;
import ru.gb.hubr.api.mapper.CommentMapper;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.dao.CommentDao;
import ru.gb.hubr.entity.Comment;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentDao commentDao;
    private final CommentLikeService commentLikeService;
    private final AccountUserDao accountUserDao;
    private final CommentMapper commentMapper;

    public List<CommentDto> findAll(String currentUserName) {
        return commentDao.findAll(Sort.by("createdAt").descending())
                .stream()
                .map(comment -> commentMapper.toCommentDto(comment, accountUserDao,
                        currentUserName, commentLikeService))
                .collect(Collectors.toList());
    }

    public CommentDto findCommentById(Long id, String currentUserName) {
        return commentMapper.toCommentDto(commentDao.findById(id).orElse(null), accountUserDao,
                currentUserName, commentLikeService);
    }

    public CommentDto save(CommentDto commentDto, String currentUserName) {
        Comment comment = commentMapper.toComment(commentDto, accountUserDao);
        return commentMapper.toCommentDto(commentDao.save(comment), accountUserDao,
                currentUserName, commentLikeService);
    }

    public void delete(Long commentId) {
        commentDao.deleteById(commentId);
    }

    public void complain(Long commentId, Long userId) {

    }
}
