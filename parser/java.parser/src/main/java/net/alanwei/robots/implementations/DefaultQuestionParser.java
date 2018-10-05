package net.alanwei.robots.implementations;

import net.alanwei.robots.interfaces.QuestionParser;
import net.alanwei.robots.models.Question;
import net.alanwei.robots.utils.Util;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultQuestionParser implements QuestionParser {
    public Question parse(Document doc) {

        Element element = doc.selectFirst("#question-header a");
        String link = element.attr("href");
        String name = element.text();
        String postContent = doc.selectFirst("#question .postcell .post-text").html();
        Element asked = doc.selectFirst("#qinfo tbody tr:nth-child(1) td:nth-child(2) p");
        Element viewed = doc.selectFirst("#qinfo tbody tr:nth-child(2) td:nth-child(2) p");
        long upVoteCount = Util.parseLong(doc.selectFirst("#question .vote-count-post").text());
        List<String> tags = doc.select(".post-taglist a").stream().map(Element::text).collect(Collectors.toList());

        Question question = new Question();
        question.setLink(link);
        question.setName(name);
        question.setPostContent(postContent);
        question.setAsked(asked.text());
        question.setAskTime(asked.attr("title"));
        question.setViewed(viewed.text());
        question.setUpVote(upVoteCount);
        question.setTags(tags);

        return question;
    }
}
