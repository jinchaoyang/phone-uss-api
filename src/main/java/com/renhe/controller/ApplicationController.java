package com.renhe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jinchaoyang on 2018/12/18.
 */
@Controller
@RequestMapping(value="/")
public class ApplicationController {

    @RequestMapping(value="",method = RequestMethod.GET)
    public String index(){
        return "index.html";
    }
}
