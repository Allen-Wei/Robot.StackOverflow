package net.alanwei.robots.utils;

import org.jsoup.nodes.Element;

public class ElementUtil {
    private ElementUtil() {
    }

    public static String text(Element element, String selector, String defVal) {
        try {
            return selector == null ? element.text() : element.selectFirst(selector).text();
        } catch (Throwable ex) {
            return defVal;
        }
    }

    public static String attr(Element element, String selector, String attr, String defVal) {
        try {
            return selector == null ? element.attr(attr) : element.selectFirst(selector).attr(attr);
        } catch (Throwable ex) {
            return defVal;
        }
    }

    public static String html(Element element, String selector, String defVal) {
        try {
            return selector == null ? element.html() : element.selectFirst(selector).html();
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

    public static String html(Element element, String selector) {
        return html(element, selector, null);
    }

}
