package Command;

import Child.AgeGroup;
import DAO.AdmDAO;
import Toy.Toy;

import java.util.List;

public class CreateList implements Command{
    @Override
    public String getName() {
        return "Creates optional list of toys";
    }

    @Override
    public void execute(AdmDAO adm) {
        System.out.println("Набір іграшок, яких підібрала програма: ");
        adm.getPlayRoom().getToyList().CreateToyMap(adm.allToysByAgeGroup(AgeGroup.TODDLER)
                ,adm.allToysByAgeGroup(AgeGroup.MIDDLECHILD)
                ,adm.allToysByAgeGroup(AgeGroup.TEENAGER));
        List<Toy> list = adm.allToysByAgeGroup(AgeGroup.TODDLER);
        list.addAll(adm.allToysByAgeGroup(AgeGroup.MIDDLECHILD));
        list.addAll(adm.allToysByAgeGroup(AgeGroup.TEENAGER));
        for(Toy t : adm.getPlayRoom().getToyList().listFromMap(list)){
            System.out.println(t);
        }
    }
}
