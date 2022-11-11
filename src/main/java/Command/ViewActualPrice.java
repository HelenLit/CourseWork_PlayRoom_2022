package Command;

import DAO.AdmDAO;

public class ViewActualPrice implements Command{
    @Override
    public String getName() {
        return "View actual price";
    }
    @Override
    public void execute(AdmDAO adm) {
        System.out.println("�������� ���� ��� �������:");
        System.out.println(adm.getPlayRoom().getToyList().getActualMoney());
    }
}