
package com.mycompany.converter_pw_to_ex;

import java.util.Scanner;

public class App {
    
    public static int req;
    
    static public void main(String[] args){
        
        Scanner scanner = new Scanner(System.in);
        
        //Create basic objects
        Html html = new Html();
        Excel excel = new Excel();
        
        //The program was launched
        RunApp run = new RunApp();
        run.setObject(html, excel);
        
        System.out.print("Press 1 to continue or 0 to exit .. ");
        req = scanner.nextInt();
        while(req == 1){
            try{
                run.run();
            }catch(Exception e){
                System.out.println("Error : Unknown Error.\n-----------------------------------");
            }
            
            System.out.print("Press 1 to restart or 0 to exit .. ");
            req = scanner.nextInt();
        }
        
    }
        
}
