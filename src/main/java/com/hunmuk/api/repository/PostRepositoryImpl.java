package com.hunmuk.api.repository;

import com.hunmuk.api.domain.Post;
import com.hunmuk.api.request.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hunmuk.api.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCostom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSearch search) {
        return jpaQueryFactory.selectFrom(post)
                .orderBy(post.id.desc())
                .limit(search.getSize())
                .offset(search.getOffset())
                .orderBy(post.id.desc())
                .fetch();
    }


}
