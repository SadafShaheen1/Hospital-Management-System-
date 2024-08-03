/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sadaf
 */
public class Patient {
   private int id;
    private String name;
    private String gender; 
    private int age;
    private String contact;
    private String cnic;

    public Patient(int id, String name, String gender, int age, String contact, String cnic) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.contact = contact;
        this.cnic=cnic;
    }

    public Patient() {
         this.id = 0;
        this.name = "";
        this.gender = "";
        this.age = 0;
        this.contact = "";
        this.cnic="";
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

   
    
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Patient{" + "id=" + id + ", name=" + name + ", gender=" + gender + ", age=" + age + ", contact=" + contact + ", cnic=" + cnic + '}';
    }

    

    
    
    
    
}
