package Command;

import DAO.AdmDAO;

import java.util.LinkedHashMap;
import java.util.Map;

public class SortToys implements Command{
    @Override
    public String getName() {
        return "Sort toys";
    }

    @Override
    public void execute(AdmDAO adm) {
        Menu subMenu = new Menu();
        System.out.println("Sort toys menu:");
        Map<String, Command> commandMap = new LinkedHashMap<String, Command>();
        commandMap.put("byprice", new SortByPrice());
        commandMap.put("byage", new SortByAge());
        commandMap.put("byamnt", new SortByAmount());
        subMenu.setCommandMap(commandMap);
        subMenu.execMenu(adm);
    }
}
