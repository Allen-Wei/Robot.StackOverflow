package net.alanwei.robots.implementations;

import net.alanwei.robots.interfaces.AnswerParser;
import net.alanwei.robots.models.Answer;
import net.alanwei.robots.utils.ElementUtil;
import net.alanwei.robots.utils.Util;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultAnswerParser implements AnswerParser {
    public List<Answer> parse(Document doc) {
        Elements answerElements = doc.select("#answers .answer");
        if (answerElements.size() <= 0) {
            return Arrays.asList();
        }

        Long questionId = Util.parseLong(ElementUtil.attr(doc, "#question", "data-questionid"));
        ;
        List<Answer> answers = answerElements.stream()
                .map(element -> {
                    try {
                        Long id = Util.parseLong(ElementUtil.attr(element, null, "data-answerid"));
                        Long upVote = Util.parseLong(ElementUtil.text(element, ".vote .vote-count-post"));
                        String bountyAward = ElementUtil.text(element, ".vote .bounty-award");
                        String postContent = ElementUtil.html(element, ".answercell .post-text");

                        Element author = element.selectFirst(".answercell .post-signature:last-child");
                        String answerTime = ElementUtil.attr(author, ".user-action-time .relativetime", "title");
                        String avatar = ElementUtil.attr(author, "img", "src");
                        String userLink = ElementUtil.attr(author, ".user-details a", "href");

                        Answer answer = new Answer();
                        answer.setQuestionId(questionId);
                        answer.setId(id);
                        answer.setPostContent(postContent);
                        answer.setUpVote(upVote);
                        answer.setAnswerTime(answerTime);
                        answer.setBountyAward(bountyAward);
                        answer.setUserAvatar(avatar);
                        answer.setUserLink(userLink);
                        return answer;
                    } catch (Throwable ex) {
                        return null;
                    }
                })
                .filter(answer -> answer != null)
                .collect(Collectors.toList());

        return answers;
    }
}
