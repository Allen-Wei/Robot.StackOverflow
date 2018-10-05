package net.alanwei.robots.interfaces;

import net.alanwei.robots.models.Question;
import org.jsoup.nodes.Document;

public interface QuestionParser {
    Question parse(Document doc);
}
