package com.compwnd.megarten.controllers;

import com.compwnd.megarten.models.Quote;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class HelloController {

    @RequestMapping("/")
    public String hello(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);

        ResponseEntity<List<Quote>> response = restTemplate.exchange(
                "http://gturnquist-quoters.cfapps.io/api/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Quote>>(){});
        List<Quote> quotes = response.getBody();

        model.addAttribute("message", quote);
        model.addAttribute("messages", quotes);
        return "hello";
    }

    @RequestMapping("/quote/{id}")
    public String quote(@PathVariable("id") int id, Model model) {

        RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/" + id, Quote.class);

        model.addAttribute("message", quote);
        model.addAttribute("id", id);
        return "quote";
    }


}
