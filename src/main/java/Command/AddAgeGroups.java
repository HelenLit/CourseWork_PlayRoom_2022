package Command;

import Child.AgeGroup;
import DAO.AdmDAO;

import java.util.Scanner;

public class AddAgeGroups implements Command{
    @Override
    public String getName() {
        return "Add age groups to your playroom";
    }

    @Override
    public void execute(AdmDAO adm) {
        System.out.println("Введіть, діти яких вікових груп будуть гратись у кімнаті?(1-TODDLER, 2-MIDDLECHILD, 3-TEENAGER)");
        Scanner scan = new Scanner(System.in);
        String line;
        while (!(line=scan.nextLine()).isBlank())
            adm.getPlayRoom().addAgeGroup(AgeGroup.getAgeGroupByOrd(Integer.parseInt(line)));
    }
}
