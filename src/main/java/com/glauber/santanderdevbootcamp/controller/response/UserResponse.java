package com.glauber.santanderdevbootcamp.controller.response;

import com.glauber.santanderdevbootcamp.domain.model.Account;
import com.glauber.santanderdevbootcamp.domain.model.Card;
import com.glauber.santanderdevbootcamp.domain.model.Feature;
import com.glauber.santanderdevbootcamp.domain.model.News;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String name;
    private Account account;
    private Card card;
    private List<Feature> features;
    private List<News> news;
}
