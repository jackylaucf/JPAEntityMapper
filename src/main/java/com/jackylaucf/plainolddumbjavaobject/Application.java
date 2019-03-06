package com.jackylaucf.plainolddumbjavaobject;

import com.jackylaucf.plainolddumbjavaobject.config.ApplicationConfigParser;
import com.jackylaucf.plainolddumbjavaobject.processor.ProcessManager;

public class Application {
    public static void main(String[] args){
        if(args.length==0){
            throw new RuntimeException("Missing argument - File path of the configuration properties");
        }else{
            try {
                ApplicationConfigParser parser = new ApplicationConfigParser(args[0]);
                ProcessManager processManager = new ProcessManager(parser.parse());
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
