package Command;

import DAO.AdmDAO;

public class ToysInRoom implements Command{
    @Override
    public String getName() {
        return "Toys in room";
    }
    @Override
    public void execute(AdmDAO adm) {

        System.out.println("Перелік іграшок, які на даний момент у кімнаті");
        System.out.println(adm.getPlayRoom().getToyList().toysInRoom());

    }
}
