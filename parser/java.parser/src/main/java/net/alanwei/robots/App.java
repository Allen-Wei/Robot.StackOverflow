package net.alanwei.robots;

import net.alanwei.robots.interfaces.QuestionJobConsumer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 */
@ComponentScan
public class App {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        long id = 6;
//        QuestionAnswers aq = job.execute(id);

        QuestionJobConsumer consumer = context.getBean(QuestionJobConsumer.class);
        consumer.consume();

    }
}
