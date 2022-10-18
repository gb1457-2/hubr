package ru.gb.hubr.web.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.entity.AccountUser;
import ru.gb.hubr.entity.Article;
import ru.gb.hubr.web.ArticleDto;

import java.util.NoSuchElementException;

@Mapper
public interface ArticleMapper {

    Article toArticle(ArticleDto articleDto, @Context AccountUserDao accountUserDao);

    ArticleDto toArticleDto(Article article);

    default AccountUser getAuthor(String author, @Context AccountUserDao accountUserDao) {
        return accountUserDao.findAccountUserByLogin("system").orElseThrow(
                () -> new NoSuchElementException("There isn't author with name " + author));
    }

    default String getAuthor(AccountUser author) {
        return author.getLogin();
    }


}