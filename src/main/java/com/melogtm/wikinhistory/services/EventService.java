package com.melogtm.wikinhistory.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.Duration;
import java.util.Random;

@Service
public class EventService {
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(7);
    private final WebClient.Builder webClientBuilder;

    public EventService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public static String buildEventUrl(Integer day, Integer month, String eventType) {
        return "https://byabbe.se/on-this-day/" + month + "/" + day + "/" + eventType + ".json";
    }

    public static int generateRandomValue(int max) {
        return new Random().nextInt(max);
    }

    public JSONArray getEvents(String eventUrl, String eventType) {
        try {
            return webClientBuilder.baseUrl(eventUrl).build().get().retrieve().bodyToMono(String.class).mapNotNull(
                    response -> new JSONObject(response).getJSONArray(eventType)
            ).block(REQUEST_TIMEOUT);
        } catch (IllegalStateException e) {
            return new JSONArray("[{\"year\": \"2084\", \"description\": \"Hit F5 or Reload this Page. Something went terribly wrong.\", \"wikipedia\": [{\"title\": \"Global catastrophic risk\", \"wikipedia\": \"a big flaw, innit?\"}]}]\n");
        }
    }

    public JSONObject getRandomEvent(JSONArray events) {;
        return events.getJSONObject(generateRandomValue(events.length()));
    }

    public String getEventTitle(JSONObject randomEventChosen) {
        return randomEventChosen.getJSONArray("wikipedia").getJSONObject(0).getString("title");
    }

    public String getEventDescription(JSONObject randomEventChosen) {
        return randomEventChosen.getString("description");
    }

    public String getYearOfEvent(JSONObject randomEventChosen) {
        return randomEventChosen.getString("year");
    }

    public String getChooseDateApiCall(String baseUrl) {
        try {
            return webClientBuilder.baseUrl(baseUrl).build().get().retrieve().bodyToMono(String.class).mapNotNull(
                    response -> {
                        JSONObject eventJson = new JSONObject(response);

                        return eventJson.getString("date");
                    }
            ).block(REQUEST_TIMEOUT);
        } catch (IllegalStateException e) {
            return "The End.";
        }
    }

    public String buildImageUrl(String eventName) {
        return "https://en.wikipedia.org/w/api.php?action=query&titles=" + eventName +
                "&prop=pageimages&format=json&pithumbsize=200";
    }
}

