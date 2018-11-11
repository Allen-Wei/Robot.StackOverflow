package net.alanwei.robots;

import net.alanwei.robots.interfaces.QuestionJobConsumer;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
@ComponentScan
public class App {
    public static void main(String[] args) throws Throwable{

        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);

//        QuestionJobConsumer consumer = context.getBean(QuestionJobConsumer.class);
//        consumer.consume();

        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("demo.html");
//        InputStream is = App.class.getClassLoader().getResourceAsStream("demo.html");
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))){
            String lines = reader.lines().collect(Collectors.joining("\n"));
            System.out.println(lines);
        }

    }
}
