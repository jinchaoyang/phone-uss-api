package com.renhe.callable;

import com.renhe.cdr.service.DBBackupService;
import com.renhe.utils.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class BackupTask implements Callable {

    private final static Logger logger = LoggerFactory.getLogger(BackupTask.class);

    @Override
    public Object call() throws Exception {
        boolean flag = true;
        DBBackupService service  = SpringUtil.getBean(DBBackupService.class);
        if(null!=service){
            while(flag) {
             flag =  service.backup();
            }
            logger.info("[BackupTask]: backup task end.");
        }else{
            logger.error("DBBackupService instance get failed!");
        }
        return null;
    }
}
