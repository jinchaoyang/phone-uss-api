package com.renhe.cdr.jobs;

import com.renhe.callable.BackupTask;
import com.renhe.callable.ImportTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@Component
public class BackupJob {

    private final static Logger logger = LoggerFactory.getLogger(BackupJob.class);

    private static boolean RUN  = false;

    @Autowired
    StringRedisTemplate redisTemplate;



    @Scheduled(cron = "0 */1 * * * ?")
    public void doJob(){
        if(!RUN) {
            logger.info("begin job");
            int processors = Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(processors);
            CompletionService completionService = new ExecutorCompletionService(executor);
            for (int i = 0; i < processors; i++) {
                completionService.submit(new BackupTask());
            }
            executor.shutdown();
            RUN = true;
            logger.info("end job");
        }


    }
}
