package com.example.resource.resourcems.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/person")
public class PersonController {

    @RequestMapping(method = RequestMethod.GET, value = "/hello/{name}")
    public String getHello(@PathVariable String name){
        return "hello " +  name;
    }

}
