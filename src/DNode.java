/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sadaf
 */
public class DNode {
    
Doctor doctor;
 DNode next;

    public DNode(Doctor doctor) {
        this.doctor = doctor;
        this.next = null;
    }

    // Getters and Setters for doctor and next
    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public DNode getNext() {
        return next;
    }

    public void setNext(DNode next) {
        this.next = next;
    }
    
    
}
