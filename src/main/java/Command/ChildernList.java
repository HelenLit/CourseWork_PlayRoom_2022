package Command;

import DAO.AdmDAO;

public class ChildernList implements Command{
    @Override
    public String getName() {
        return "Children list";
    }
    @Override
    public void execute(AdmDAO adm) {
        System.out.println(adm.getPlayRoom().ChildrenList());
    }
}
