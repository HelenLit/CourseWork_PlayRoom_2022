package Command;

import DAO.AdmDAO;

import java.util.Scanner;

public class SetPrice implements Command{
    @Override
    public String getName() {
        return "Sets initial price for toys";
    }

    @Override
    public void execute(AdmDAO adm) {
        System.out.println("Введіть виділену суму грошей");
        Scanner scan = new Scanner(System.in);
        adm.getPlayRoom().getToyList().setInitialMoney(Integer.parseInt(scan.nextLine()));
    }
}
