package com.renhe.callable;

import com.renhe.configuration.CustomerProperties;
import com.renhe.service.VerifyService;
import com.renhe.utils.SpringUtil;

import java.io.*;
import java.util.concurrent.Callable;

/**
 * 白名单号码过滤
 */
public class VipFilterTask implements Callable<String> {

    /**
     * 白名单源文件
     */
    private File sourceFile;

    /**
     * 任务ID
     */
    private String taskId;


    public VipFilterTask(File sourceFile,String taskId){
        this.sourceFile = sourceFile;
        this.taskId = taskId;
    }


    @Override
    public String call() throws Exception {
        String result = null;
        if(this.sourceFile.exists()){
          VerifyService verifyService =   SpringUtil.getBean(VerifyService.class);
          CustomerProperties properties = SpringUtil.getBean(CustomerProperties.class);
          String line = null;
          String destDirStr = properties.getDestPath();
          destDirStr = destDirStr.endsWith("/") ? destDirStr : destDirStr+"/";
          File destDir = new File(destDirStr);
          if(!destDir.exists()){
              destDir.mkdirs();
          }
          result = destDirStr+this.taskId+".txt";
          BufferedReader reader = new BufferedReader(new FileReader(this.sourceFile));
          BufferedWriter writer = new BufferedWriter(new FileWriter(result));
          while((line = reader.readLine())!=null){
              TaskManager.incr(this.taskId);
              if(verifyService.verifyVip(line)){
                  writer.write(line+"\r\n");
              }

          }
          writer.flush();
          writer.close();
          reader.close();
          this.sourceFile.delete();
        }

        return result;
    }



    public File getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
