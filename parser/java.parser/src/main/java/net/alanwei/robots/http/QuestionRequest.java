package net.alanwei.robots.http;

import okhttp3.ResponseBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QuestionRequest {
    @GET("questions/{id}")
    Call<ResponseBody> request(@Path("id") long questionId);

    default Document requestThenParse(long questionId) {

        try {
            String response = this.request(questionId).execute().body().string();
            Document doc = Jsoup.parse(response);
            return doc;
        } catch (Throwable ex) {
            return null;
        }
    }
}
