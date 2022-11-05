package Command;

import DAO.AdmDAO;

public class ProgramInfo implements Command{
    @Override
    public String getName() {
        return "Program information";
    }
    @Override
    public void execute(AdmDAO adm) {
        System.out.println("Це програма для підготовки ігрової кімнати для дітей різних вікових груп");
    }
}
