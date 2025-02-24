package com.hwk9407.bookmanagementassignment.domain.book;

import com.hwk9407.bookmanagementassignment.domain.author.QAuthor;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BookRepositoryQueryImpl implements BookRepositoryQuery {

    private final JPAQueryFactory queryFactory;
    QBook book = QBook.book;
    QAuthor author = QAuthor.author;

    @Override
    public Page<Book> retrieveBooksWithFilter(Long authorId, LocalDate startPubDate, LocalDate endPubDate, Pageable pageable) {

        BooleanBuilder whereClause = new BooleanBuilder();
        if (authorId != null) {
            whereClause.and(book.author.id.eq(authorId));
        }
        if (startPubDate != null) {
            whereClause.and(book.publicationDate.goe(startPubDate));
        }
        if (endPubDate != null) {
            whereClause.and(book.publicationDate.loe(endPubDate));
        }
        JPAQuery<Book> query = queryFactory.selectFrom(book)
                .join(book.author, author).fetchJoin()
                .where(whereClause);
        applyOrderBy(query, pageable)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        List<Book> books = query.fetch();
        Long total = Optional.ofNullable(
                queryFactory.select(book.count())
                        .from(book)
                        .where(whereClause)
                        .fetchOne()
        ).orElse(0L);
        return new PageImpl<>(books, pageable, total);
    }

    private JPAQuery<Book> applyOrderBy(JPAQuery<Book> query, Pageable pageable) {
        if (pageable.getSort().isSorted()) {
            for (Sort.Order order : pageable.getSort()) {
                PathBuilder<Book> path = new PathBuilder<>(Book.class, "book");
                OrderSpecifier<?> orderSpecifier = order.isAscending()
                        ? path.getString(order.getProperty()).asc()
                        : path.getString(order.getProperty()).desc();
                query.orderBy(orderSpecifier);
            }
        }
        return query;
    }
}
