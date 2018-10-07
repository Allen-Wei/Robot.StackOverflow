package net.alanwei.robot.models;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Answer {
    private Long id;
    private Long questionId;
    private Long upVote;
    private String bountyAward;
    private String postContent;
    private String answerTime;
    private String userLink;
    private String userAvatar;
}

