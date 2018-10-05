package net.alanwei.robots.models;

import lombok.Getter;
import lombok.Setter;
import net.alanwei.robots.utils.Util;

import java.util.List;

@Getter
@Setter
public class QuestionAnswers {
    private List<Answer> answers;
    private Question question;

    @Override
    public String toString() {
        return Util.toJson(this);
    }
}
