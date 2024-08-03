
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author sadaf
 */
public class Appointment extends javax.swing.JFrame {
private Home HomeFrame;

    /**
     * Creates new form Appointment
     */
    public Appointment(int patientIdValue, String patientNameValue, String illnessValue, String doctorValue, String slotValue, double feeValue) {
        initComponents();
          String[] illnesses = {"Emergency", "General Physician", "pediatrician", "gynaecologist", "Dentist"};
        illness.setModel(new DefaultComboBoxModel<>(illnesses));
        //String[] timeSlots = {"11:00", "3:00", "5:00"};
        //slot.setModel(new DefaultComboBoxModel<>(timeSlots));
       HomeFrame = new Home();
        
    }

 
    
    
    
    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {                                          
  try {
        // Parse input values
        int patientIdValue = Integer.parseInt(patientId.getText().trim());
        String patientNameValue = patientName.getText().trim();
        String illnessValue = (String) illness.getSelectedItem();
        String doctorValue = (String) doctor.getSelectedItem();
        String slotValue = (String) slot.getSelectedItem();
        double feeValue = Double.parseDouble(fee.getText().trim());

        // Validate patient ID
        if (patientIdValue <= 0 || !isPatientIdUnique(patientIdValue)) {
            JOptionPane.showMessageDialog(null, "Invalid or duplicate ID. Please enter a unique positive integer.");
            return;
        }

        // Validate patient name
        if (!isPatientNameValid(patientNameValue)) {
            JOptionPane.showMessageDialog(null, "Invalid name. Please use only alphabetic characters and spaces.");
            return;
        }

        // Validate fee
        if (feeValue <= 0) {
            JOptionPane.showMessageDialog(null, "Fee should be greater than 0.");
            return;
        }

        // Create appointment object
        Appointment appointment = new Appointment(patientIdValue, patientNameValue, illnessValue, doctorValue, slotValue, feeValue);

        // Save to file 
        saveAppointmentToFile(appointment);

        JOptionPane.showMessageDialog(null, "Appointment saved successfully!");

        // Clear fields after saving
        clearAppointmentFields();

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers.");
    }
}
    //added buffer bcz we are not saving data here to the memory it is just being saved to
      public boolean isPatientIdUnique(int patientId) {
 try (BufferedReader reader = new BufferedReader(new FileReader("record.txt"))) {
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            String[] parts = currentLine.split(", ");
            if (parts.length >= 1) {
                try { //part[0] is actually taking the fisrt index value of line (inrecord.txt) and comparing it to the id 
                    if (Integer.parseInt(parts[0].trim()) == patientId) {
                        return false; // ID is not unique
                    }
                } catch (NumberFormatException e) {
                    // Handle case where the ID from file is not a valid integer
                    continue; // Skip this line and continue with the next line
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace(); // Handle or log the exception
    }
    return true; // ID is unique
}
          private boolean isPatientNameValid(String patientName) {
        return patientName.matches("[a-zA-Z\\s]+");
    }
          
          


      
// Method to save appointment to file

           private boolean isDoctorAvailable(String doctor, Date date, String slot) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(date);
        try (BufferedReader reader = new BufferedReader(new FileReader("record.txt"))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split(", ");
                if (parts.length >= 7) {
                    String recordedDoctor = parts[3].trim();
                    String recordedDate = parts[6].trim();
                    String recordedSlot = parts[4].trim();
                    if (recordedDoctor.equals(doctor) && recordedDate.equals(dateString) && recordedSlot.equals(slot)) {
                        return false; // Doctor is not available
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true; // Doctor is available
    }


// Method to clear appointment input fields
private void clearAppointmentFields() {
    patientId.setText("");
    patientName.setText("");
    fee.setText("");
    illness.setSelectedIndex(0);
    doctor.setSelectedIndex(0);
    slot.setSelectedIndex(0);
    jDateChooser1.setDate(null);
}


   private String[] getDoctorsByIllness(String selectedIllness) {
        
        // This is a placeholder implementation
        //"Emergency", "General Physician", "pediatrician", "gynaecologist", "Dentist"
        switch (selectedIllness) {
            case "Emergency":
                return new String[]{"Dr. Sadaf", "Dr. Shaheen","Dr.Habiba"};
            case "General Physician":
                return new String[]{"Dr.Mehmood", "Dr.Hammad " , "Dr. Agha"};
            case "pediatrician":
                return new String[]{"Dr.Javeria", "Dr.Asma"};
            case "gynaecologist":
                return new String[]{"Dr.kulsoom", "Dr.Zainab" , "Dr. Neelofer","Dr.Iqbal"};
            case "Dentist":
                return new String[]{"Dr. Khan", "Dr. Perez"};
            default:
                return new String[]{"No doctors available"};
        }
    }


 public Appointment(int patientIdValue, String patientNameValue, String illnessValue, String doctorValue, double feeValue, String slotValue) {
    initComponents();
    patientId.setText(String.valueOf(patientIdValue));
    patientName.setText(patientNameValue);
    illness.setSelectedItem(illnessValue);
    doctor.setSelectedItem(doctorValue);
    fee.setText(String.valueOf(feeValue));
    slot.setSelectedItem(slotValue);
}

  
    public String toString() {
        return patientId.getText() + ";" + patientName.getText() + ";" + illness.getSelectedItem() + ";" + doctor.getSelectedItem() + ";" + fee.getText();
    }

        private void saveAppointmentToFile(int patientId, String patientName, String illness, String doctor, String slot, double fee, Date date) {
    String filePath = "record.txt"; 
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 String dateString = dateFormat.format(date);
    try (FileWriter fileWriter = new FileWriter(filePath, true);
         BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
         PrintWriter writer = new PrintWriter(bufferedWriter)) {

        String appointmentData = patientId + ", " + patientName + ", " + illness + ", " + doctor + ", " + slot + ", " + fee+ ", " + dateString;

        writer.println(appointmentData);

        System.out.println("Appointment saved to file.");
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saving appointment: " + e.getMessage());
    }
}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        patientId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        patientName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        fee = new javax.swing.JTextField();
        illness = new javax.swing.JComboBox<>();
        doctor = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        slot = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Patient ID: ");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jTextField1.setBackground(new java.awt.Color(0, 0, 0));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 102, 0));
        jTextField1.setText("           Appointment Information");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(204, 204, 204));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setText("HOME");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jButton4)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 102, 0));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("illness : ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Availabe Doctor :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Patient Name: ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Fee: ");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Admit Date:");

        illness.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                illnessActionPerformed(evt);
            }
        });

        doctor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Slot : ");

        slot.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "11:00", "3:00", "5:00" }));

        jButton2.setText("Reset");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Exit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(39, 39, 39)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(slot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(illness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(77, 77, 77)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(doctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fee, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(91, 91, 91)
                                .addComponent(patientId, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(111, 111, 111)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(patientName, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 5, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2)
                                .addGap(80, 80, 80)
                                .addComponent(jButton3)
                                .addGap(1, 1, 1)))
                        .addGap(114, 114, 114))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(patientId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(patientName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(illness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(doctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(fee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(slot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(28, 28, 28))
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
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void illnessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_illnessActionPerformed
        // TODO add your handling code here:
           
        String selectedIllness = (String) illness.getSelectedItem();
        String[] doctors = getDoctorsByIllness(selectedIllness); // Implement this method
        doctor.setModel(new DefaultComboBoxModel<>(doctors));
    
        
    }//GEN-LAST:event_illnessActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
           try {
            String patientIdText = patientId.getText();
            if (!Pattern.matches("\\d+", patientIdText)) {
                JOptionPane.showMessageDialog(null, "Invalid Patient ID. Please enter a positive integer.");
                return;
            }
            int patientIdValue = Integer.parseInt(patientId.getText().trim());
            if (patientIdValue <= 0 || !isPatientIdUnique(patientIdValue)) {
                JOptionPane.showMessageDialog(null, "Invalid or duplicate Patient ID. Please enter a unique positive integer.");
                return;
            }

            String patientNameValue = patientName.getText();
            if (!isPatientNameValid(patientNameValue)) {
                JOptionPane.showMessageDialog(null, "Invalid Patient Name. Please use only alphabetic characters and spaces.");
                return;
            }

            String illnessValue = (String) illness.getSelectedItem();
            String doctorValue = (String) doctor.getSelectedItem();
             if (doctorValue == null || doctorValue.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a doctor.");
            return;
        }
            
            Date selectedDate = jDateChooser1.getDate();
            
            
            String slotValue = (String) slot.getSelectedItem();

            String feeText = fee.getText();
            if (!Pattern.matches("\\d+\\.?\\d*", feeText)) {
                JOptionPane.showMessageDialog(null, "Invalid Fee. Please enter a positive number.");
                return;
            }
            double feeValue = Double.parseDouble(feeText);
            if (feeValue <= 0) {
                JOptionPane.showMessageDialog(null, "Fee should be greater than 0.");
                return;
            }
         
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(null, "Please select a valid date.");
            return;
        }

        //doc availabe hai ya nhi
          if (!isDoctorAvailable(doctorValue, selectedDate, slotValue)) {
                JOptionPane.showMessageDialog(null, "Doctor is not available at the selected date and slot. Please choose a different time or doctor.");
                return;
            }
            // Save appointment to file
            saveAppointmentToFile(patientIdValue, patientNameValue, illnessValue, doctorValue, slotValue, feeValue, selectedDate);
            JOptionPane.showMessageDialog(null, "Appointment saved successfully!");

            // Clear fields after saving
            clearAppointmentFields();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers.");
        ex.printStackTrace();
        }
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
     clearAppointmentFields();
        
      
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        System.exit(0);
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
       
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
        
         this.setVisible(false);
        
      Home home = new Home();

        home.setVisible(true);
    }//GEN-LAST:event_jButton4MouseClicked

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
            java.util.logging.Logger.getLogger(Appointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Appointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Appointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Appointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               // new Appointment(int patientIdValue, String patientNameValue, String illnessValue, String doctorValue, double feeValue ,String slotValue).setVisible(true);
            //parametarized consrtructor call huwa hau upar tu yahn values deni lazmi hogi like for ex null hi sahi
                Appointment appointment = new Appointment(0, "", "", "", 0.0, "");
            // Set any necessary fields or call methods on appointment here
            appointment.setVisible(true);
            
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> doctor;
    private javax.swing.JTextField fee;
    private javax.swing.JComboBox<String> illness;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField patientId;
    private javax.swing.JTextField patientName;
    private javax.swing.JComboBox<String> slot;
    // End of variables declaration//GEN-END:variables

    private void saveAppointmentToFile(Appointment appointment) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
