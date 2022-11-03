package Command;

import DAO.AdmDAO;

import java.util.LinkedHashMap;
import java.util.Map;

public class ExecCommand {
    Map<String, Command> commandMap;
    public void menu(AdmDAO adm) {
        Menu menu = new Menu();
        commandMap = new LinkedHashMap<String, Command>();
        commandMap.put("info", new ProgramInfo());
        commandMap.put("reg",new RegisterChild());
        commandMap.put("chlist", new ChildernList());
        commandMap.put("sort", new SortToys());
        commandMap.put("tview", new ViewToys());
        commandMap.put("addt", new AddNewToy());
        commandMap.put("del", new DeleteToy());
        commandMap.put("price", new ViewPrice());
        commandMap.put("addage", new AddAgeGroups());
        commandMap.put("setprc", new SetPrice());
        commandMap.put("list", new CreateList());
        commandMap.put("start", new StartRoom());
        commandMap.put("end", new EndRoom());
        menu.setCommandMap(commandMap);
        menu.execMenu(adm);
    }
}
