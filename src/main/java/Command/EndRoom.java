package Command;

import DAO.AdmDAO;

public class EndRoom implements Command{
    @Override
    public String getName() {
        return "Makes room empty";
    }
    @Override
    public void execute(AdmDAO adm) {
        System.out.println("Звільнення кімнати");
        adm.getPlayRoom().freeRoom();
    }
}
