package com.renhe.cdr.jobs;

import com.renhe.cdr.service.CdrService;
import com.renhe.cdr.vo.BlackPhone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

public class CdrJob {

    @Autowired
    CdrService cdrService;

    static long phone = 20000000000l;

    @Scheduled(cron = "0 */1 * * * ?")
    public void doJob(){
        for(int i=0;i<50000;i++){
            phone = phone+i;
            BlackPhone _phone = new BlackPhone();
            _phone.setPhoneNo(phone+"");
            cdrService.save(_phone);
        }

    }

}
