package Command;

import DAO.AdmDAO;

public interface Command {

    public String getName();
    public void execute(AdmDAO adm);
}
