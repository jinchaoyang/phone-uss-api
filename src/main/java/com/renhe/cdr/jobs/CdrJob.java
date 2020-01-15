package com.renhe.cdr.jobs;

import com.renhe.cdr.service.CdrService;
import com.renhe.cdr.vo.BlackPhone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


public class CdrJob {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    CdrService cdrService;


    @Scheduled(cron = "0 */1 * * * ?")
    public void doJob(){


    }

}
