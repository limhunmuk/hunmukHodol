package com.hunmuk.api.repository.post;

import com.hunmuk.api.domain.Post;
import com.hunmuk.api.request.post.PostSearch;

import java.util.List;

public interface PostRepositoryCostom {

    List<Post> getList(PostSearch search);
}
