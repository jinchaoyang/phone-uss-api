package com.renhe.tenant.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/v1.0/dbl")
public class DBLController {

    private final static Logger logger = LoggerFactory.getLogger(DBLController.class);

    @PostMapping("/notify")
    public JSONObject receiveEvent(@RequestBody String resp){
        logger.info("resp -> {}",resp);
        JSONObject result = new JSONObject();
        result.put("result","true");
        result.put("resultCode","1000");
        result.put("reason","成功");
        return result;
    }
}
