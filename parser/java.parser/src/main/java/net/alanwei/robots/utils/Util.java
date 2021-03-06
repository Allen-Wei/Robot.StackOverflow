package net.alanwei.robots.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.util.StringUtils;

public class Util {
    private Util() {
    }

    public static String toJson(Object data) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(data);
    }

    public static <T> T fromJson(String json, Class<T> cls) {
        try {
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(json, cls);
        } catch (Throwable ex) {
            return null;
        }
    }

    public static Long parseLong(String value) {
        if (StringUtils.isEmpty(value)) {
            return -1L;
        }
        try {
            return Long.parseLong(value);
        } catch (Throwable ex) {
            return -1L;
        }
    }
}
