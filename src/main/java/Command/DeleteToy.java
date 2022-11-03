package Command;

import DAO.AdmDAO;

import java.util.Scanner;

public class DeleteToy implements Command{
    @Override
    public String getName() {
        return "Delete toy";
    }
    @Override
    public void execute(AdmDAO adm) {
        System.out.println("Введіть ID іграшки, яку видалити ");
        Scanner scan = new Scanner(System.in);
        int ID = scan.nextInt();
        adm.deleteToy(ID);
    }
}
