package Command;

import Child.AgeGroup;
import Child.Child;
import DAO.AdmDAO;

import java.util.Scanner;

public class RegisterChild implements Command{
    @Override
    public String getName() {
        return "Register child";
    }

    @Override
    public void execute(AdmDAO adm) {
        System.out.println("Реєстрація нової дитини\nВведіть\n\tім'я,\n\tпрізвище,\n\tвікову групу(1-TODDLER, 2-MIDDLECHILD, 3-TEENAGER),\n\tконтакт до батьків");
        Scanner scan = new Scanner(System.in);
        adm.getPlayRoom().registerChild(new Child(scan.nextLine(),scan.nextLine(), AgeGroup.getAgeGroupByOrd(Integer.parseInt(scan.nextLine())),scan.nextLine()));
    }
}
