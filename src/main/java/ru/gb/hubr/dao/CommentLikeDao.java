package ru.gb.hubr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.hubr.entity.Comment;
import ru.gb.hubr.entity.CommentLike;

public interface CommentLikeDao extends JpaRepository<CommentLike, Long> {

    long countCommentLikeByCommentAndDeletedAtIsNull(Comment comment);

    boolean existsByAuthor_UsernameAndDeletedAtIsNull(String username);

}