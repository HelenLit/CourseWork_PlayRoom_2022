package Child;

public class Child {
    private String fname;
    private String lname;
    private AgeGroup ageGroup;
    private String contact;

    public Child(String fname,String lname, AgeGroup ageGroup, String contact) {
        this.fname = fname;
        this.lname = lname;
        this.ageGroup = ageGroup;
        this.contact = contact;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
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
        this.ageGroup = ageGroup;
    }
    public void setAgeGroup(int age) {
        if((this.ageGroup = AgeGroup.getGroup(age)) == null)
            throw new IllegalArgumentException();
    }
    public void setContact(String contact) {
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
}
