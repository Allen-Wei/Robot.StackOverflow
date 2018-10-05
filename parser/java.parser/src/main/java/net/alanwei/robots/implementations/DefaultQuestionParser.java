package net.alanwei.robots.implementations;

import net.alanwei.robots.interfaces.QuestionParser;
import net.alanwei.robots.models.Question;
import net.alanwei.robots.utils.ElementUtil;
import net.alanwei.robots.utils.Util;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultQuestionParser implements QuestionParser {
    public Question parse(Document doc) {
        Element elementQuestion = doc.selectFirst("#question");
        Element elementQuestionInfo = doc.selectFirst("#qinfo");
        Element elementHeader = doc.selectFirst("#question-header");

        Long id = Util.parseLong(ElementUtil.attr(elementQuestion, null, "data-questionid"));

        String link = ElementUtil.attr(elementHeader, "a", "href");
        String name = ElementUtil.text(elementHeader, "a");

        String postContent = ElementUtil.html(elementQuestion, ".postcell .post-text");
        String asked = ElementUtil.attr(elementQuestionInfo, "tbody tr:nth-child(1) td:nth-child(2) p", "title");
        String viewed = ElementUtil.text(elementQuestionInfo, "tbody tr:nth-child(2) td:nth-child(2) p");
        long upVoteCount = Util.parseLong(ElementUtil.text(elementQuestion, ".vote-count-post"));
        List<String> tags = elementQuestion.select(".post-taglist a").stream().map(Element::text).collect(Collectors.toList());

        Question question = new Question();
        question.setId(id);
        question.setLink(link);
        question.setName(name);
        question.setPostContent(postContent);
        question.setAsked(asked);
        question.setViewed(viewed);
        question.setUpVote(upVoteCount);
        question.setTags(tags);

        return question;
    }
}
