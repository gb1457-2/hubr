package ru.gb.hubr.api.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import ru.gb.hubr.api.dto.ArticleDto;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.entity.AccountUser;
import ru.gb.hubr.entity.Article;

import java.util.NoSuchElementException;

@Mapper(uses = CommentMapper.class)
public interface ArticleMapper {

    Article toArticle(ArticleDto articleDto, @Context AccountUserDao accountUserDao);

    ArticleDto toArticleDto(Article article, @Context AccountUserDao accountUserDao);

    default AccountUser getAuthor(String author, @Context AccountUserDao accountUserDao) {
        return accountUserDao.findByUsername(author).orElseThrow(
                () -> new NoSuchElementException("There isn't author with name " + author));
    }

    default String getAuthor(AccountUser author) {
        return author.getUsername();
    }

//    default String getTopic(ArticleTopic topic) {
//        return topic.getTitle();
//    }

}
