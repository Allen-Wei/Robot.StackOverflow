package net.alanwei.robots.utils;

import net.alanwei.robots.http.QuestionRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;

@Configuration
public class Configurations {
    @Bean
    public QuestionRequest getQuestionRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://stackoverflow.com/")
                .build();

        QuestionRequest service = retrofit.create(QuestionRequest.class);
        return service;
    }
}
