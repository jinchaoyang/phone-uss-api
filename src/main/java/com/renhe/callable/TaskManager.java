package com.renhe.callable;

import java.util.HashMap;
import java.util.Map;

public class TaskManager {

    public static Map<String,Long> TASK_PROGRESS = new HashMap<>();

    public static Map<String,Long> TASK_SANPSHOT = new HashMap<>();

    public static void incr(String taskId){
        long count = TASK_PROGRESS.getOrDefault(taskId,0l);
        TASK_PROGRESS.put(taskId,count+1);
    }

    public static void remove(String taskId){
        TASK_PROGRESS.remove(taskId);
        TASK_SANPSHOT.remove(taskId);
    }

    public static long getProgress(String taskId){
      return  TASK_PROGRESS.getOrDefault(taskId,0l);
    }

    public static void setSnapshot(String taskId,long total){
        TASK_SANPSHOT.put(taskId,total);
    }

    public static long getSnapshot(String taskId){
       return TASK_SANPSHOT.getOrDefault(taskId,0l);
    }
}
