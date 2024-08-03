import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.SwingUtilities;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;




/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author sadaf
 */



public class Patientpage2 extends javax.swing.JFrame {

    patientlist allPatientList = new patientlist();
    /**
     * Creates new form Patientpage2
     */
    public Patientpage2() {
        initComponents();
         readAllData();
        writeAllData();
        addInputValidators();
    }

    
    private void addPatient() {
        if (checkIDC(allPatientList)) {
            return; // Exit if ID, contact, or CNIC already exists
        }

        String patientCnic = cnic.getText();
        if (!isValidCNIC(patientCnic)) {
            JOptionPane.showMessageDialog(this, "CNIC must be in the format XXXXX-XXXXXXX-X.");
            return;
        }

        try {
            int patientId = Integer.parseInt(id.getText());
            String patientName = name.getText();
            int patientAge = Integer.parseInt(age.getText());
            String patientGender = jRadioButton1.isSelected() ? "Male" : "Female";
            String patientContact = contact.getText();

            Patient2 patient = new Patient2(patientId, patientName, patientAge, patientGender, patientContact, patientCnic);
            allPatientList.insert(patient); // Insert at the beginning of the list
            writeAllData();
            saveAllData(); // Save data to file after adding a patient
            JOptionPane.showMessageDialog(this, "Patient added successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding patient: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void saveAllData() {
        try ( FileWriter fileWriter = new FileWriter("C:/Users/sadaf/OneDrive/Documents/NetBeansProjects/JHS2/Pdata.txt")) {

            patientlist.pNode temp = allPatientList.getHead();

            while (temp != null) {
                Patient2 patient = temp.patient;
                fileWriter.write(patient.getId() + ";"
                        + patient.getName() + ";"
                        + patient.getAge() + ";"
                        + patient.getGender() + ";"
                        + patient.getContact() + ";"
                        + patient.getCnic() + "\n");
                temp = temp.next;
            }
            JOptionPane.showMessageDialog(null, "Data saved successfully!");
            // Update JTextArea after saving
            writeAllData();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving data: " + e.getMessage());
        }
    }
    
        private void updateAllPdata() {
        SwingUtilities.invokeLater(() -> {
            try {
                File file = new File("C:/Users/sadaf/OneDrive/Documents/NetBeansProjects/JHS2/Pdata.txt");
                FileReader reader = new FileReader(file);
                StringBuilder data = new StringBuilder();
                int c;
                while ((c = reader.read()) != -1) {
                    data.append((char) c);
                }
                reader.close();
                jTextArea1.setText(data.toString()); // Update JTextArea with the data string
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error reading data for display: " + e.getMessage());
            }
        });
    }
        
        // Example method to save data and update JTextArea after saving
    private void saveDataAndUpdateTextArea() {
        try {
            // Save data to file logic here (similar to your save logic)
            // After saving, update JTextArea
            updateAllPdata();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving data: " + e.getMessage());
        }
    }
    
     //to update // text area got some restrictions so added EDT 
    private void writeAllData() {
        try ( FileReader fileReader = new FileReader("C:/Users/sadaf/OneDrive/Documents/NetBeansProjects/JHS2/Pdata.txt")) {
            StringBuilder data = new StringBuilder();
            int c;
            while ((c = fileReader.read()) != -1) {
                data.append((char) c);
            }
            jTextArea1.setText(data.toString()); // Update JTextArea with the read data
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error reading data: " + e.getMessage());
        }
    }
    
    
        private boolean isValidCNIC(String cnic) {
        // CNIC format: 12345-1234567-1 (where 12345 and 1234567 are numeric segments)
        if (cnic.length() != 15) {
            return false; // CNIC should be exactly 15 characters long
        }

        // Check specific positions for hyphens
        if (cnic.charAt(5) != '-' || cnic.charAt(13) != '-') {
            return false; // CNIC should have hyphens at positions 5 and 13
        }

        // Validate numeric segments
        try {
            // Extract numeric segments
            String[] parts = cnic.split("-");
            String part1 = parts[0]; // Should be 5 digits
            String part2 = parts[1]; // Should be 7 digits
            String part3 = parts[2]; // Should be 1 digit

            // Check segment lengths
            if (part1.length() != 5 || part2.length() != 7 || part3.length() != 1) {
                return false;
            }

            // Check if all parts are numeric
            Long.parseLong(part1); // Throws NumberFormatException if not numeric
            Long.parseLong(part2);
            Long.parseLong(part3);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return false; // If parsing fails or array index is out of bounds
        }

        return true; // Passed all checks
    }
        
         private void searchPatientById() {
        String searchId = JOptionPane.showInputDialog(this, "Enter Patient ID to search:");
        if (searchId != null && !searchId.isEmpty()) {
            Patient2 patient = allPatientList.searchById(searchId);
            if (patient != null) {
                JOptionPane.showMessageDialog(this, "Patient found: " + patient.toString());
            } else {
                JOptionPane.showMessageDialog(this, "Patient not found.");
            }
        }
    }
         
             private void clearFields() {
        id.setText("");
        name.setText("");
        age.setText("");
        contact.setText("");
        cnic.setText("");
    }
             
             
     private void exitApplication() {
        System.exit(0);
    }
     
    
     private void readAllData() {
        try {
            String filePath = "C:/Users/sadaf/OneDrive/Documents/NetBeansProjects/JHS2/Pdata.txt";
            System.out.println("Attempting to read file from path: " + filePath); // Debug statement

            File pfile = new File(filePath);

            // Checking for file, exist or not
            if (!pfile.exists()) {
                JOptionPane.showMessageDialog(this, "File not found at path: " + pfile.getAbsolutePath(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Scanner sc = new Scanner(pfile);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] curData = data.split(";");
                if (curData.length == 6) {
                    Patient2 patient = new Patient2();
                    patient.setId(Integer.parseInt(curData[0]));
                    patient.setName(curData[1]);
                    patient.setAge(Integer.parseInt(curData[2]));
                    patient.setGender(curData[3]);
                    patient.setContact(curData[4]);
                    patient.setCnic(curData[5]);

                    allPatientList.insert(patient);
                } else {
                    System.out.println("Invalid data format: " + data); // Debug: Print invalid format
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error reading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
     
       private void addInputValidators() {
        id.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                validateId();
            }

            public void removeUpdate(DocumentEvent e) {
                validateId();
            }

            public void changedUpdate(DocumentEvent e) {
                validateId();
            }

            private void validateId() {
                String text = id.getText();
                if (!text.matches("\\d*")) {
                    JOptionPane.showMessageDialog(null, "ID must be an integer.");
                    id.setText(text.replaceAll("[^\\d]", ""));
                }
            }
        });

        age.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                validateAge();
            }

            public void removeUpdate(DocumentEvent e) {
                validateAge();
            }

            public void changedUpdate(DocumentEvent e) {
                validateAge();
            }

            private void validateAge() {
                String text = age.getText();
                if (!text.matches("\\d*")) {
                    JOptionPane.showMessageDialog(null, "Age must be an integer.");
                    age.setText(text.replaceAll("[^\\d]", ""));
                }
            }
        });

        cnic.getDocument().addDocumentListener(new DocumentListener() {
            private boolean isUserInput = true;

            public void insertUpdate(DocumentEvent e) {
                if (isUserInput) {
                    validateCnic();
                }
            }

            public void removeUpdate(DocumentEvent e) {
                if (isUserInput) {
                    validateCnic();
                }
            }

            public void changedUpdate(DocumentEvent e) {
                if (isUserInput) {
                    validateCnic();
                }
            }

            private void validateCnic() {
                String text = cnic.getText();
                if (!text.matches("\\d{4}-\\d{7}-\\d")) {
                    JOptionPane.showMessageDialog(null, "CNIC must be in the format xxxx-xxxxxxx-x.");
                    isUserInput = false;
                    cnic.setText(text.replaceAll("[^\\d-]", ""));
                    isUserInput = true;
                }
            }
        });

        contact.getDocument().addDocumentListener(new DocumentListener() {
            private boolean isUserInput = true;

            public void insertUpdate(DocumentEvent e) {
                if (isUserInput) {
                    validateContact();
                }
            }

            public void removeUpdate(DocumentEvent e) {
                if (isUserInput) {
                    validateContact();
                }
            }

            public void changedUpdate(DocumentEvent e) {
                if (isUserInput) {
                    validateContact();
                }
            }

            private void validateContact() {
                String text = contact.getText();
                if (!text.matches("\\d{4}-\\d{7}")) {
                    JOptionPane.showMessageDialog(null, "Contact must be in the format xxxx-xxxxxxx.");
                    isUserInput = false;
                    contact.setText(text.replaceAll("[^\\d-]", ""));
                    isUserInput = true;
                }
            }
        });
    }
       
       private void validateCnic() {
        String inputCnic = cnic.getText();

        if (!isValidCNIC(inputCnic) && !inputCnic.isEmpty()) {
            JOptionPane.showMessageDialog(null, "CNIC must be in the format XXXXX-XXXXXXX-X.");
        }
    }
    private boolean isValidContact(String contact) {
    // Contact format: 1234-1234567
    return contact.matches("\\d{4}-\\d{7}");
}
     //To check for duplicate IDs, contacts, and CNICs
    
    private boolean checkIDC(patientlist allPatientList) {
    String inputId = id.getText();
    String inputContact = contact.getText();
    String inputCnic = cnic.getText();

    // Validate CNIC format
    if (!isValidCNIC(inputCnic)) {
        JOptionPane.showMessageDialog(null, "CNIC must be in the format XXXXX-XXXXXXX-X.");
        return true;
    }

    try {
        int patientId = Integer.parseInt(inputId);

        // Iterate through existing patient list
        patientlist.pNode temp = allPatientList.head;

        while (temp != null) {
            Patient2 patient = temp.getPatient();

            // Check if ID already exists
            if (patient.getId() == patientId) {
                JOptionPane.showMessageDialog(null, "ID already exists.");
                return true;
            }

            // Check if Contact already exists
            String patientContact = patient.getContact();
            if (inputContact.equals(patientContact)) {
                JOptionPane.showMessageDialog(null, "Contact already exists.");
                return true;
            }

            // Check if CNIC already exists
            String patientCnic = patient.getCnic();
            if (inputCnic.equals(patientCnic)) {
                JOptionPane.showMessageDialog(null, "CNIC already exists.");
                return true;
            }

            temp = temp.getNext(); // Move to the next node
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "ID must be an integer.");
    }

    return false; // Return false if no duplicates found
}

    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        contact = new javax.swing.JTextField();
        cnic = new javax.swing.JTextField();
        age = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jTextField1.setBackground(new java.awt.Color(0, 0, 0));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 102, 0));
        jTextField1.setText("Patient Record - JHS Hospital");

        jButton6.setBackground(new java.awt.Color(204, 204, 204));
        jButton6.setText("Home");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(183, 183, 183)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 102, 51));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("ID: ");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Name :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Age:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("CNIC :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Gender: ");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Contact: ");

        jRadioButton1.setText("Male");

        jRadioButton2.setText("Female");

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Reset");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Search");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Save ");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(age, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cnic, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(61, 61, 61)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(contact, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButton2)
                                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jRadioButton1)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(30, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(age, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(32, 32, 32)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(53, 53, 53)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cnic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(contact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addComponent(jRadioButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jRadioButton2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(12, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
         exitApplication();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        clearFields();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        searchPatientById();
        
    }//GEN-LAST:event_jButton3ActionPerformed

    
                                        

    
    
    
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        //save ka button 
        
                if (id.getText().equals("") || name.getText().equals("") || age.getText().equals("") || contact.getText().equals("") || cnic.getText().equals("")) {
            JOptionPane.showMessageDialog(null, " All fields are required");
        } else {

            try {
                int patientId = Integer.parseInt(id.getText());
                String patientName = name.getText();
                int patientAge = Integer.parseInt(age.getText());
                String patientGender = jRadioButton1.isSelected() ? "Male" : "Female";
                String patientContact = contact.getText();
                String patientCnic = cnic.getText();                //validation for cnic format
                if (!isValidCNIC(patientCnic)) {
                    JOptionPane.showMessageDialog(null, "CNIC must be in the format XXXXX-XXXXXXX-X.");
                    return;
                }
                if (!isValidContact(patientContact)) {
                    JOptionPane.showMessageDialog(null, "Contact must be in the format XXXX-XXXXXXX.");
                    return;
                }

                //for contact format
                /*  if (!patientContact.matches("\\d{4}-\\d{7}")) {
                    JOptionPane.showMessageDialog(null, "Contact must be in the format XXXX-XXXXXXX");
                    return;
                }*/
                if (checkIDC(allPatientList)) {
                     JOptionPane.showMessageDialog(null, "ID already exists.");
                    return;
                }

                //validation for id,contatcnt and cnic
                /* if (checkIDC()) {
                    return;
                } else {
                    Patient patient = new Patient();
                    patient.setId(Integer.parseInt(id.getText()));
                    patient.setName(name.getText());
                    patient.setAge(Integer.parseInt(age.getText()));
                    // patient.setGender(gender.getText());
                    patient.setContact(contact.getText());
                    patient.setCnic(cnic.getText());
                    //  allPatientList.insert(patient);

                    id.setText("");
                    name.setText("");
                    age.setText("");
                    //gender.setText("");
                    contact.setText("");
                    cnic.setText("");
                    writeAllData();
                    //for saving all dataa in txt file
                    saveAllData();

                }*/
                 
                Patient2 patient = new Patient2(patientId, patientName, patientAge, patientGender, patientContact, patientCnic);
                allPatientList.insert(patient);
                // Clear input fields after successful addition
                clearFields();
saveAllData();
                // Save data to file and update JTextArea
               
                JOptionPane.showMessageDialog(null, "Patient added successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ID and Age and Contact must be Integer");
            }
        
                
        
    }//GEN-LAST:event_jButton4ActionPerformed
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Patientpage2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Patientpage2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Patientpage2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Patientpage2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Patientpage2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField age;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JTextField cnic;
    private javax.swing.JTextField contact;
    private javax.swing.JTextField id;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField name;
    // End of variables declaration//GEN-END:variables

   






}
