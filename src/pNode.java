/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sadaf
 */
public class pNode {


    Patient2 patient;
    pNode next;
    pNode previous;

    public pNode(Patient2 patient) {
        this.patient = patient;
        this.next = null;
        this.previous = null;
    }

    // Getters and setters for patient, next, and previous
    public Patient2 getPatient() {
        return patient;
    }

    public void setPatient(Patient2 patient) {
        this.patient = patient;
    }

    public pNode getNext() {
        return next;
    }

    public void setNext(pNode next) {
        this.next = next;
    }

    public pNode getPrevious() {
        return previous;
    }

    public void setPrevious(pNode previous) {
        this.previous = previous;
    }
}

    
    

