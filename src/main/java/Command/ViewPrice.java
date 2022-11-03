package Command;

import DAO.AdmDAO;

public class ViewPrice implements Command{
    @Override
    public String getName() {
        return "View initial price";
    }
    @Override
    public void execute(AdmDAO adm) {
        System.out.println("Перегляд виділеної суми грошей");
        System.out.println(adm.getPlayRoom().getToyList().getInitialMoney());
    }

}
