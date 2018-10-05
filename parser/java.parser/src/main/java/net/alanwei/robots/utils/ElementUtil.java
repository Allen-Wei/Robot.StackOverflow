package net.alanwei.robots.utils;

import org.jsoup.nodes.Element;

public class ElementUtil {
    private ElementUtil() {
    }

    public static String text(Element element, String selector, String defVal) {
        try {
            return element.selectFirst(selector).text();
        } catch (Throwable ex) {
            return defVal;
        }
    }

    public static String attr(Element element, String selector, String attr, String defVal) {
        try {
            return element.selectFirst(selector).attr(attr);
        } catch (Throwable ex) {
            return defVal;
        }
    }

    public static String text(Element element, String selector) {
        return text(element, selector, null);
    }

    public static String attr(Element element, String selector, String attr) {
        return attr(element, selector, attr, null);

    }

}
