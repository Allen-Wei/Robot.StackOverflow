package net.alanwei.robots.implementations;

import net.alanwei.robots.interfaces.AnswerParser;
import net.alanwei.robots.models.Answer;
import net.alanwei.robots.utils.ElementUtil;
import net.alanwei.robots.utils.Util;
import org.jsoup.nodes.Document;
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
        List<Answer> answers = answerElements.stream()
                .map(element -> {
                    try {
                        Long upVote = Util.parseLong(ElementUtil.text(element, ".vote .vote-count-post"));
                        String bountyAward = ElementUtil.text(element, ".vote .bounty-award");
                        String postContent = ElementUtil.text(element, ".answercell .post-text");
                        String answerTime = ElementUtil.attr(element, ".answercell .owner .user-action-time .relativetime", "title");
                        String avatar = ElementUtil.attr(element, ".answercell .owner img", "src");
                        String userLink = element.selectFirst(".answercell .owner .user-details a").attr("href");

                        Answer answer = new Answer();
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
