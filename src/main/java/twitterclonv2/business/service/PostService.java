package twitterclonv2.business.service;

import twitterclonv2.domain.dto.post.request.PostRequest;
import twitterclonv2.domain.entity.PostEntity;

public interface PostService {
    PostEntity createOnePost(PostRequest postRequest);
}
