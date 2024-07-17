package com.melogtm.wikinhistory.services;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.json.JSONObject;
import java.time.Duration;

@Service
public class ImageService {
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(7);
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public ImageService(WebClient webClient) {
        this.webClientBuilder = WebClient.builder();
    }

    public String getImage(String myUrl) {
        try {
            return webClientBuilder.baseUrl(myUrl).build().get().retrieve().bodyToMono(String.class).mapNotNull(
                    response -> {
                        JSONObject json = new JSONObject(response);
                        JSONObject pages = json.getJSONObject("query").getJSONObject("pages");

                        String wikiPageId = pages.keys().next();
                        try {
                            JSONObject page = pages.getJSONObject(wikiPageId);
                            JSONObject thumbnail = page.getJSONObject("thumbnail");

                            return thumbnail.getString("source");
                        } catch (JSONException e) {
                            return null;
                        }
                    }
            ).block(REQUEST_TIMEOUT);
        } catch (IllegalStateException e) {
            System.out.println("Error while retrieving image: " + e.getMessage());
            return "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cb/Impact_event.jpg/200px-Impact_event.jpg";
        }
    }
}
