package Command;

import DAO.AdmDAO;

public class SortByAmount implements Command{
    @Override
    public String getName() {
        return "Sort by amount";
    }

    @Override
    public void execute(AdmDAO adm) {
        System.out.println("Сортування іграшок за кількістю");
        System.out.println(adm.getPlayRoom().getToyList().sortToysByAmount());
    }
}
