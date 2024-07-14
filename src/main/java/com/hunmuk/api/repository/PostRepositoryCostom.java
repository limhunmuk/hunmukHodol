package com.hunmuk.api.repository;

import com.hunmuk.api.domain.Post;
import com.hunmuk.api.request.PostSearch;

import java.util.List;

public interface PostRepositoryCostom {

    List<Post> getList(PostSearch search);
}
