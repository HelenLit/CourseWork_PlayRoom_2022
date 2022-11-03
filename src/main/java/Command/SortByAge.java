package Command;

import DAO.AdmDAO;

public class SortByAge implements Command{
    @Override
    public String getName() {
        return "Sort by age group";
    }

    @Override
    public void execute(AdmDAO adm) {
        System.out.println("Сортування іграшок за віковою групою");
        System.out.println(adm.getPlayRoom().getToyList().sortToysByAgeGroup());
    }
}
