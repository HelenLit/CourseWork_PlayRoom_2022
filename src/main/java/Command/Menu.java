package Command;

import DAO.AdmDAO;
import Email.EmailSender;

import java.util.Map;
import java.util.Scanner;

public class Menu {
    private Map<String, Command> commandMap;

    public void setCommandMap(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }
    void printInfo(){
        commandMap.forEach((key, value) -> System.out.println("\t" + key + "\t-\t" + value.getName()));
        System.out.println("\tret - Return from this menu");
    }
    public void execMenu(AdmDAO adm){
        Scanner scan = new Scanner(System.in);
        while(true){
            printInfo();
            System.out.println("\nВведіть команду: ");
            String command = scan.nextLine().trim();
            if(command.contentEquals("ret")){
                System.out.println("Return from this menu");
                return;
            }
            try {
                commandMap.get(command).execute(adm);
            }
            catch (NullPointerException e){
                EmailSender.send("Command error","The command \""+command + "\" is not found");
            }
        }
    }
}
