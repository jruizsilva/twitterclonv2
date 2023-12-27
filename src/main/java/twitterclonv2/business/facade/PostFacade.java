package twitterclonv2.business.facade;

import twitterclonv2.domain.dto.post.request.PostRequest;
import twitterclonv2.domain.entity.PostEntity;

public interface PostFacade {
    PostEntity createOnePost(PostRequest postRequest);
}
