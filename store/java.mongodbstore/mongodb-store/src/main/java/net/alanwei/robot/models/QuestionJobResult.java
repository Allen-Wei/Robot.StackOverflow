package net.alanwei.robot.models;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionJobResult {
    private Long questionId;
    private String message;
    private Question question;
    private List<Answer> answers;
}

