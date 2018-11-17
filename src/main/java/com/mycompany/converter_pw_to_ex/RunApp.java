
package com.mycompany.converter_pw_to_ex;

import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.filechooser.FileSystemView;
import org.jsoup.nodes.Element;


public class RunApp{
    
    Scanner scanner = new Scanner(System.in);
    
    Html html = null;
    Excel excel = null;
    
    /**
     * 
     * @param html
     * @param excel 
     */
    public void setObject(Html html, Excel excel){
        this.html = html;
        this.excel = excel;
    }
    public static String ValidateRegex(String theRegex, String str2Check,boolean GetList) {
String result="";
        Pattern checkRegex = Pattern.compile(theRegex);
        Matcher regexMatcher = checkRegex.matcher(str2Check);

        while (regexMatcher.find()) {
            if (regexMatcher.group().length() != 0) {
                
                if(GetList == false){
                    return regexMatcher.group().trim();
                }else{
                    
                result= result+ regexMatcher.group()+"\n"; // catch the first group
}

                }
            
            }
                        return result;
        }
public static boolean validateLink(String RegexPattern, String Link) {

        Pattern checkRegex = Pattern.compile(RegexPattern);
        Matcher regexMatcher = checkRegex.matcher(Link);

        return regexMatcher.find();
    }
    public void run(){
        String DesktopPath = System.getProperty("user.home") + "\\Desktop\\";
        String DocumentsPath =FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\";
        String ApplicationPath = System.getProperty("user.dir") + "\\";
        String SavePath = null;
        String pageLink;
        int Choice;
        boolean checkLink;
do{
    System.out.print("Please Enter Wikipedia Link : ");
    pageLink = scanner.nextLine();
    checkLink = validateLink("(https:\\/\\/[a-zA-Z]{2}\\.wikipedia\\.org\\/wiki\\/.+)",pageLink);
    if(checkLink == false){
        System.out.println("Error : The link is invalid, please check your link and try again.");
    }
}while(checkLink == false);
        String Title = ValidateRegex("(?<=wiki\\/).+[^\\/]",pageLink,false);
        
        System.out.println("Started ...");
        System.out.println("The page is scanned ...");
        if(html.checkPage(pageLink) == 200){
            System.out.println("The page was checked and loaded.");
            Element table = html.getTable();
            if(table != null){
do{
                System.out.println("Where do you want to save " + Title +".xls ?");
                System.out.println("1.Desktop");
                System.out.println("2.My Documents");
                System.out.println("3.Application Folder");
                System.out.print("Please Enter your option : ");
                Choice = scanner.nextInt();
    switch (Choice) {
        case 1:
            SavePath = DesktopPath;
            break;
        case 2:
            SavePath = DocumentsPath;
            break;
        case 3:
            SavePath = ApplicationPath;
            break;
        default:
            System.out.println("Error : Invalid option, please check your input and try again.");
            break;
    }
                
}while(Choice >3);
         
                FileOutputStream out = excel.createNewFileExcel(SavePath + Title +".xls");
                System.out.println("Create an Excel file ...");
                excel.setContent(out, table);
                System.out.println("Conversion successful.");
                System.out.println("Success : The Excel file was saved in the following path: "+"'"+ SavePath + Title +".xls'\n-----------------------------------");
            }
            else{
                System.out.println("Error : An unknown error occurred during execution.\n-----------------------------------");
            }
        }
        else{
            System.out.println("Error : The request failed.\n-----------------------------------");
        }
        
    }
}
