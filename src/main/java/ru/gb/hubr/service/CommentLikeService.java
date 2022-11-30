package ru.gb.hubr.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.hubr.dao.CommentLikeDao;
import ru.gb.hubr.entity.Comment;
import ru.gb.hubr.entity.CommentLike;
import ru.gb.hubr.entity.user.AccountUser;

import java.util.Objects;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeDao commentLikeDao;

    private final AccountUserService accountUserService;

    public Long countCommentLikeByCommentAndDeletedAtIsNull(Comment comment) {
        return commentLikeDao.countCommentLikeByCommentAndDeletedAtIsNull(comment);
    }

    public Long getCurrentUserLikeId(AccountUser accountUser, Long commentId) {
        if (isNull(accountUser)) return 0L;
        if (!isEmpty(accountUser.getCommentLikes())) {
            Optional<CommentLike> commentLikeOptional = accountUser.getCommentLikes().stream()
                    .filter(commentLike -> isNull(commentLike.getDeletedAt())
                            && (Objects.equals(commentLike.getComment().getId(), commentId)))
                    .findFirst();
            CommentLike commentLike = commentLikeOptional.orElse(null);
            if (nonNull(commentLike)) return commentLike.getId();
        }
        return 0L;
    }

    @Transactional
    public void logicalDelete(Long commentLikeId, String currentUserName) {
        Long currentUserId = accountUserService.findByUsername(currentUserName).getId();
        CommentLike commentLike = commentLikeDao.findById(commentLikeId).orElse(null);
        if (nonNull(commentLike) && (nonNull(commentLike.getAuthor()))
                && (Objects.equals(commentLike.getAuthor().getId(), currentUserId))) {
            commentLike.setDeletedAt(now());
            commentLikeDao.save(commentLike);
        }
    }

    public void add(Long commentId, String currentUserName) {
        Long currentUserId = accountUserService.findByUsername(currentUserName).getId();
        CommentLike commentLike = new CommentLike();
        Comment comment = new Comment();
        comment.setId(commentId);
        commentLike.setComment(comment);
        AccountUser accountUser = new AccountUser();
        accountUser.setId(currentUserId);
        commentLike.setAuthor(accountUser);
        commentLike.setCreatedAt(now());
        commentLikeDao.save(commentLike);
    }
}