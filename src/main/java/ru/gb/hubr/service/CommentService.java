package ru.gb.hubr.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gb.hubr.api.dto.CommentDto;
import ru.gb.hubr.api.mapper.CommentMapper;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.dao.CommentDao;
import ru.gb.hubr.entity.Comment;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentDao commentDao;
    private final AccountUserDao accountUserDao;
    private final CommentMapper commentMapper;

    public CommentDto save(CommentDto commentDto) {
        Comment comment = commentMapper.toComment(commentDto, accountUserDao);
        return commentMapper.toCommentDto(commentDao.save(comment), accountUserDao);
    }

    public void delete(Long commentId) {
        commentDao.deleteById(commentId);
    }

    public void complain(Long commentId, Long userId) {

    }


}
