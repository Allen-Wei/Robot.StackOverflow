package net.alanwei.robots.interfaces;

import net.alanwei.robots.models.Answer;
import org.jsoup.nodes.Document;

import java.util.List;

public interface AnswerParser {
    List<Answer> parse(Document doc);
}
