package com.melogtm.wikinhistory.model;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class Image {
    private String imageUrl;

    public Image() {}

    @Bean
    public WebClient imageWebClient() {
        return WebClient.create(imageUrl);
    }
}
