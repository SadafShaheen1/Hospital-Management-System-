import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class patientlist {

    pNode head;
    private pNode tail;

    public patientlist() {
        this.head = null;
        this.tail = null;
    }

    public void insert(Patient2 patient) {
    // Check for duplicates based on ID, contact, or CNIC
    pNode temp = head;
    while (temp != null) {
        Patient2 existingPatient = temp.patient;
        if (existingPatient.getId() == patient.getId()) {
            JOptionPane.showMessageDialog(null, "Patient with ID " + patient.getId() + " already exists.");
            return; // Exit method without inserting duplicate
        }
        if (existingPatient.getContact().equals(patient.getContact())) {
            JOptionPane.showMessageDialog(null, "Patient with contact " + patient.getContact() + " already exists.");
            return; // Exit method without inserting duplicate
        }
        if (existingPatient.getCnic().equals(patient.getCnic())) {
            JOptionPane.showMessageDialog(null, "Patient with CNIC " + patient.getCnic() + " already exists.");
            return; // Exit method without inserting duplicate
        }
        temp = temp.next;
    }

    // If no duplicates found, proceed to insert the patient
    pNode node = new pNode(patient);
    if (head == null) {
        head = node;
        tail = node;
        return;
    } 
    
    
    else {
        node.next = head;
        head.previous = node;
        head = node;
    }
}


    public Patient2 searchById(String id) {
        pNode temp = head;
        while (temp != null) {
            if (String.valueOf(temp.patient.getId()).equals(id)) {
                return temp.patient;
            }
            temp = temp.next;
        }
        return null;
    }

    public int size() {
        pNode temp = head;
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    public Patient2 getAtIndex(int index) {
        pNode temp = head;
        int count = 0;
        while (temp != null) {
            if (count == index) {
                return temp.patient;
            }
            count++;
            temp = temp.next;
        }
        return null;
    }

    public void printData() {
        pNode temp = head;
        int count = 0;
        while (temp != null) {
            count++;
            System.out.println(count + ": " + temp.patient.toString());
            temp = temp.next;
        }
    }

    public void writeAllData(JTextArea allPdata) {
        allPdata.setText("");
        pNode temp = head;
        while (temp != null) {
            allPdata.append(temp.patient.getId() + " " + temp.patient.getName() + " " + temp.patient.getAge() + " " +
                            temp.patient.getGender() + " " + temp.patient.getContact() + " " + temp.patient.getCnic() + "\n");
            temp = temp.next;
        }
    }

    public void saveToFile(String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            pNode temp = head;
            while (temp != null) {
                // Check if patient ID already exists before saving
                if (!idExists(temp.patient.getId())) {
                    fileWriter.write(temp.patient.getId() + ";" + temp.patient.getName() + ";" + temp.patient.getAge() + ";" +
                                     temp.patient.getGender() + ";" + temp.patient.getContact() + ";" + temp.patient.getCnic() + "\n");
              
                
                }
                temp = temp.next;
            }
            System.out.println("Data saved successfully to " + filePath); // Moved inside try block
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    private boolean idExists(int id) {
        pNode temp = head;
        while (temp != null) {
            if (temp.patient.getId() == id) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    public void readFromFile(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                if (parts.length == 6) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    String gender = parts[3];
                    String contact = parts[4];
                    String cnic = parts[5];

                    Patient2 patient = new Patient2(id, name, age, gender, contact, cnic);
                    insert(patient);
                } else {
                    System.out.println("Invalid data format: " + line);
                }
            }
            System.out.println("Data read successfully from " + filePath);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }

    public class pNode {
        Patient2 patient;
        pNode next;
        pNode previous;

        public pNode(Patient2 patient) {
            this.patient = patient;
            this.next = null;
            this.previous = null;
        }

        public Patient2 getPatient() {
            return patient;
        }

        public pNode getNext() {
            return next;
        }

        pNode getnext() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }

    public pNode getHead() {
        return head;
    }
}
