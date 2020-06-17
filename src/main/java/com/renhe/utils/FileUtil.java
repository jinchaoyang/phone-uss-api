package com.renhe.utils;

import java.io.*;

public class FileUtil {

    public static byte[] readFileByBytes(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream(((int) file.length()));
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            int bufSize = 1024;
            byte[] buffer = new byte[bufSize];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, bufSize))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }


    public static long getLineNumbers(File sourceFile){
        long total = 0l;
        if(sourceFile.exists()){
            try{
                FileReader fileReader = new FileReader(sourceFile);
                LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
                lineNumberReader.skip(Long.MAX_VALUE);
                total = lineNumberReader.getLineNumber();
                total = total+1;
                lineNumberReader.close();
                fileReader.close();

            }catch(Exception e){

                //donothing
            }
        }
        return total;
    }
}

