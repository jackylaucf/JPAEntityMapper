package com.jackylaucf.plainolddumbjavaobject;

import com.jackylaucf.plainolddumbjavaobject.config.ApplicationConfig;
import com.jackylaucf.plainolddumbjavaobject.processor.ProcessManager;

public class Application {
    public static void main(String[] args){
        if(args.length==0){
            throw new RuntimeException("Missing argument - File path of the configuration properties");
        }else{
            try {
                ProcessManager processManager = new ProcessManager(new ApplicationConfig(args[0]));
                processManager.start();
                System.out.println("Service Completed");
            }catch(Exception e){
                System.err.print("Error encountered: ");
                System.err.println(e.toString());
                System.out.println("The program will exit now");
            }
        }
    }
}
