package com.glauber.santanderdevbootcamp.controller.request;

import com.glauber.santanderdevbootcamp.domain.model.Account;
import com.glauber.santanderdevbootcamp.domain.model.Card;
import com.glauber.santanderdevbootcamp.domain.model.Feature;
import com.glauber.santanderdevbootcamp.domain.model.News;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserRequest {
    private String name;
    private Account account;
    private Card card;
    private List<Feature> features;
    private List<News> news;
}
