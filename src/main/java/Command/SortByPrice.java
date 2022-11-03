package Command;

import DAO.AdmDAO;

public class SortByPrice implements Command{
    @Override
    public String getName() {
        return "Sort by price";
    }

    @Override
    public void execute(AdmDAO adm) {
        System.out.println("Сортування іграшок за ціною");
        System.out.println(adm.getPlayRoom().getToyList().sortToysByPrice());
    }
}
