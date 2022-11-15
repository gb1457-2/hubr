package ru.gb.hubr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.hubr.entity.Comment;

public interface CommentDao extends JpaRepository<Comment, Long> {

}