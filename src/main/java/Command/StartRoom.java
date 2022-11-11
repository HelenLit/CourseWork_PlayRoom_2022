package Command;

import DAO.AdmDAO;
import Email.EmailSender;

public class StartRoom implements Command{
    @Override
    public String getName() {
        return "Starts room, makes it impossible to update information about room.";
    }

    @Override
    public void execute(AdmDAO adm) {
        System.out.println("Затвердження списку іграшок, відкриття кімнати");
        adm.getPlayRoom().startGroup();
        EmailSender.send("Playroom event","Playroom is opened");

    }
}
