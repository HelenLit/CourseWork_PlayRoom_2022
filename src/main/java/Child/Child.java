package Child;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Logger;

public class Child {
    private String fname;
    private String lname;
    private AgeGroup ageGroup;
    private String contact;

    public Child() {}

    public Child(String fname, String lname, AgeGroup ageGroup, String contact) {
        this.fname = fname;
        this.lname = lname;
        this.ageGroup = ageGroup;
        this.contact = contact;
    }

    public void setFname(String fname) {
        check(fname);
        this.fname = fname;
    }

    public void setLname(String lname) {
        check(lname);
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public String getContact() {
        return contact;
    }


    public void setAgeGroup(AgeGroup ageGroup) {
        check(ageGroup);
        this.ageGroup = ageGroup;
    }
    public void setAgeGroup(int age) {
        if((this.ageGroup = AgeGroup.getGroup(age)) == null)
            throw new IllegalArgumentException();
    }
    private void check(Object obj){
        if(obj == null)
            throw new IllegalArgumentException("This argument cannot be null");
    }
    public void setContact(String contact) {
        check(contact);
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Child:" +
                "\nfname: " + fname +
                "\nlname: " + lname +
                "\nageGroup: " + ageGroup +
                "\ncontact: " + contact;
    }
    public static Child createChildObj(ResultSet result){
        String fname = null;
        String lname = null;
        AgeGroup ageGroup = null;
        String pcontact = null;
        try{
            if (result == null)
                throw new IllegalArgumentException();
            fname = result.getString("fname");
            lname = result.getString("lname");
            ageGroup = AgeGroup.getAgeGroupByOrd(result.getInt("ageGroupID"));
            pcontact = result.getString("parent_contact");
        }catch (SQLException e){
            Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            logger.severe("Не вдалось отримати дані за назвою колонки");
            System.err.println("Could not extract data from column name in ResultSet: " + Arrays.toString(e.getStackTrace()));
            System.exit(e.getErrorCode());
        }
        return new Child(fname,lname,ageGroup,pcontact);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Child)){
            return false;
        }
        return ((Child) obj).fname.equals(this.fname) && ((Child) obj).lname.equals(this.lname)
                && ((Child) obj).ageGroup.equals(ageGroup) && ((Child) obj).contact.equals(contact);

    }
}
