package com.jwt.week6.post.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostUpdateReq {
    private final String title;
    private final String content;
}
