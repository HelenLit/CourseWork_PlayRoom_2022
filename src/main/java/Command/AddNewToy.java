package Command;

import Child.AgeGroup;
import DAO.AdmDAO;
import Toy.Toy;
import Toy.ToySize;

import java.util.Scanner;

public class AddNewToy implements Command{
    @Override
    public String getName() {
        return "Add new toy";
    }
    @Override
    public void execute(AdmDAO adm) {
        System.out.println("Додавання нової іграшки у базу даних\nВведіть\n\tназву,\n\tціну,\n\tвікову групу(1-TODDLER, 2-MIDDLECHILD, 3-TEENAGER)\n\tта розмір (1-TINY, 2-SMALL, 3 -> MEDIUM, 4 -> BIG)");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
        int price = Integer.parseInt(scan.nextLine());
        AgeGroup age = AgeGroup.getAgeGroupByOrd(Integer.parseInt(scan.nextLine()));
        ToySize size = ToySize.getSizeByOrd(Integer.parseInt(scan.nextLine()));
        adm.getToyList().addToy(Toy.createToy(0,name,price,age,size));

    }
}
