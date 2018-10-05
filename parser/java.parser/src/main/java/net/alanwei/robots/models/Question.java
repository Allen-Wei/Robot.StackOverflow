package net.alanwei.robots.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Question {
    private Long id;
    private String name;
    private String link;
    private String asked;
    private String viewed;
    private Long upVote;
    private Long favorite;
    private String postContent;
    private List<String> tags;
}
