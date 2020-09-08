package com.cyrilsebastian.tvitter.api.post;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class PostDto {
    private UUID id;
    private String content;
    private String description;
    private String title;
}
