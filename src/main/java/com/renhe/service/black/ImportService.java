package com.renhe.service.black;

import com.renhe.callable.ImportTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.*;

/**
 * 号码导入
 */
@Service
public class ImportService {

    @Autowired
    StringRedisTemplate redisTemplate;


    public void importRecord(){
        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(processors);
        CompletionService completionService = new ExecutorCompletionService(executor);
        completionService.submit(new ImportTask(redisTemplate));
        executor.shutdown();

    }

}
