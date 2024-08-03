import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class DoctorList {

   
    DNode head;
    DNode tail;

    public DoctorList() {
        this.head = null;
        this.tail = null;
        loadFromFile("C:/Users/sadaf/OneDrive/Documents/NetBeansProjects/JHS2/Ddata.txt");
    }

    public void insert(Doctor doctor) {
        // Check for duplicates based on ID, contact, or CNIC
        DNode temp = head;
        while (temp != null) {
            Doctor existingDoctor = temp.doctor;
            if (existingDoctor.getId() == doctor.getId()) {
                JOptionPane.showMessageDialog(null, "Doctor with ID " + doctor.getId() + " already exists.");
                return; // Exit method without inserting duplicate
            }
            if (existingDoctor.getContact().equals(doctor.getContact())) {
                JOptionPane.showMessageDialog(null, "Doctor with contact " + doctor.getContact() + " already exists.");
                return; // Exit method without inserting duplicate
            }
            if (existingDoctor.getCnic().equals(doctor.getCnic())) {
                JOptionPane.showMessageDialog(null, "Doctor with CNIC " + doctor.getCnic() + " already exists.");
                return; // Exit method without inserting duplicate
            }
            temp = temp.next;
        }
        
        

        // If no duplicates found, proceed to insert the doctor
        DNode node = new DNode(doctor);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head.previous = node;
            head = node;
        }
    }

    public Doctor searchById(String id) {
        DNode temp = head;
        while (temp != null) {
            if (String.valueOf(temp.doctor.getId()).equals(id)) {
                return temp.doctor;
            }
            temp = temp.next;
        }
        return null;
    }

    public int size() {
        DNode temp = head;
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    public Doctor getAtIndex(int index) {
        DNode temp = head;
        int count = 0;
        while (temp != null) {
            if (count == index) {
                return temp.doctor;
            }
            count++;
            temp = temp.next;
        }
        return null;
    }

    public void printData() {
        DNode temp = head;
        int count = 0;
        while (temp != null) {
            count++;
            System.out.println(count + ": " + temp.doctor.toString());
            temp = temp.next;
        }
    }

    public void saveToFile(String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            DNode temp = head;
            while (temp != null) {
                fileWriter.write(temp.doctor.getId() + ";" + temp.doctor.getName() + ";" +
                                 temp.doctor.getContact() + ";" + temp.doctor.getCnic() + ";" +
                                 temp.doctor.getGender() + ";" + temp.doctor.getDomain() + "\n");
                temp = temp.next;
            }
            System.out.println("Data saved successfully to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving data: " + e.getMessage());
        }
    } 
    
   
    private void loadFromFile(String filepath) {
        try (Scanner scanner = new Scanner(new File(filepath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                if (parts.length == 6) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String contact = parts[2];
                    String cnic = parts[3];
                    String gender = parts[4];
                    String domain = parts[5];

                    Doctor doctor = new Doctor(id, name, contact, cnic, gender, domain);
                    insert(doctor);
                } else {
                    System.out.println("Invalid data format: " + line);
                }
            }
            System.out.println("Data read successfully from doctor.txt");
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }

   public DNode getHead() {
        return head;
    }

    public class DNode {
        Doctor doctor;
        DNode next;
        DNode previous;

        public DNode(Doctor doctor) {
            this.doctor = doctor;
            this.next = null;
            this.previous = null;
        }

        public Doctor getDoctor() {
            return doctor;
        }

        public DNode getNext() {
            return next;
        }

        DNode getnext() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

}
