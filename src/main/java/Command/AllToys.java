package Command;

import Child.AgeGroup;
import DAO.AdmDAO;

public class AllToys implements Command{
    @Override
    public String getName() {
        return "All Toys";
    }

    @Override
    public void execute(AdmDAO adm) {
        System.out.println(adm.allToysByAgeGroup(AgeGroup.TODDLER));
        System.out.println(adm.allToysByAgeGroup(AgeGroup.MIDDLECHILD));
        System.out.println(adm.allToysByAgeGroup(AgeGroup.TEENAGER));
    }
}
