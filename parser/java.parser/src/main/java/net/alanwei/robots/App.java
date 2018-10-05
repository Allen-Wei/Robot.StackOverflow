package net.alanwei.robots;

import net.alanwei.robots.http.QuestionRequest;
import net.alanwei.robots.interfaces.AnswerParser;
import net.alanwei.robots.interfaces.QuestionParser;
import net.alanwei.robots.models.Answer;
import net.alanwei.robots.models.Question;
import net.alanwei.robots.utils.Util;
import org.jsoup.nodes.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

/**
 * Hello world!
 */
@ComponentScan
public class App {
    public static void main(String[] args) throws Throwable {

        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        long id = 5;
        QuestionRequest service = context.getBean(QuestionRequest.class);
        Document doc = service.requestThenParse(id);
        if(doc == null){
            System.out.println("doc is null");
            return;
        }

        QuestionParser questionParser = context.getBean(QuestionParser.class);
        Question question = questionParser.parse(doc);

        AnswerParser answerParser = context.getBean(AnswerParser.class);
        List<Answer> answers = answerParser.parse(doc);

        System.out.println(Util.toJson(question));
        System.out.println(Util.toJson(answers));

    }
}
