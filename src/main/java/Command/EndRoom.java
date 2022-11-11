package Command;

import DAO.AdmDAO;
import Email.EmailSender;

public class EndRoom implements Command{
    @Override
    public String getName() {
        return "Makes room empty";
    }
    @Override
    public void execute(AdmDAO adm) {
        System.out.println("Звільнення кімнати");
        adm.getPlayRoom().freeRoom();
        EmailSender.send("Playroom event","Playroom is closed");

    }
}
