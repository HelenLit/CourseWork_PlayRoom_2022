package Command;

import DAO.AdmDAO;

import java.util.LinkedHashMap;
import java.util.Map;

public class ViewToys implements Command{
    @Override
    public String getName() {
        return "View toys";
    }
    @Override
    public void execute(AdmDAO adm) {
        Menu subMenu = new Menu();
        System.out.println("View toys menu:");
        Map<String, Command> commandMap = new LinkedHashMap<String, Command>();
        commandMap.put("all", new AllToys());
        commandMap.put("inroom", new ToysInRoom());
        subMenu.setCommandMap(commandMap);
        subMenu.execMenu(adm);
    }
}
