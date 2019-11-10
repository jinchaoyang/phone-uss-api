package com.renhe.cdr.service;

import com.renhe.cdr.vo.BlackPhone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class CdrService {

    @Autowired
    MongoTemplate mongoTemplate;


    public void save(BlackPhone phone){
        mongoTemplate.save(phone);
    }

}
