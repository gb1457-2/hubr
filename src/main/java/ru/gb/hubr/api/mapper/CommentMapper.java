package ru.gb.hubr.api.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.gb.hubr.api.dto.CommentDto;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.entity.Article;
import ru.gb.hubr.entity.Comment;
import ru.gb.hubr.entity.user.AccountUser;

import java.util.List;
import java.util.NoSuchElementException;

@Mapper
public interface CommentMapper {

    @Mapping(target = "article.id", source = "articleId")
    @Mapping(target = "author", ignore = true)
    Comment toComment(CommentDto commentDto, @Context AccountUserDao accountUserDao);

    @Mapping(target = "articleId", source = "article.id")
    @Mapping(target = "username", ignore = true)
    CommentDto toCommentDto(Comment comment, @Context AccountUserDao accountUserDao);

    List<CommentDto> toCommentDto(List<Comment> comments, @Context AccountUserDao accountUserDao);

    @AfterMapping
    default void commentDtoComplete(@MappingTarget CommentDto commentDto, Comment comment,
                                    @Context AccountUserDao accountUserDao) {
        Long userId = comment.getAuthor().getId();
        String userName = accountUserDao.findById(userId).orElseThrow(
                () -> new NoSuchElementException("There isn't author with id " + userId)
        ).getUsername();
        commentDto.setUsername(userName);
        commentDto.setUsername(comment.getAuthor().getUsername());
    }

    @AfterMapping
    default void commentComplete(@MappingTarget Comment comment, CommentDto commentDto,
                                 @Context AccountUserDao accountUserDao) {
        AccountUser accountUser = accountUserDao.findByUsername(commentDto.getUsername()).orElseThrow(
                () -> new NoSuchElementException("There isn't author with username " +
                        commentDto.getUsername())
        );
        comment.setAuthor(accountUser);
    }

    default String getArticle(Article article) {
        return article.getName();
    }


}
