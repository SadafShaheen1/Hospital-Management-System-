public class Doctor {

    int id;
    String name;
    String contact;
     String cnic;
     String gender;
  String domain;

    public Doctor() {
        this.id = 0;
        this.name = "";
        this.contact = "";
        this.cnic = "";
        this.gender = "";
        this.domain = "";
    }

    public Doctor(int id, String name, String contact, String cnic, String gender, String domain) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.cnic = cnic;
        this.gender = gender;
        this.domain = domain;
    }

    // Getters and Setters for all fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + contact + ", " + cnic + ", " + gender + ", " + domain;
    }
}
