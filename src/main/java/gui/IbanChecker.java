/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import ibanchecker.Iban;
import javax.swing.JOptionPane;

/**
 *
 * @author Markus Loechner
 */
public class IbanChecker {
    public static void main(String[] args) {
        new IbanChecker();
    }

    public IbanChecker() {
        String iban = JOptionPane.showInputDialog(null, "If you want to know the Checksum, type any letter in for both digits 3 and 4.\nEnter the IBAN:", "IBAN-Checker", JOptionPane.QUESTION_MESSAGE);
        try {
            JOptionPane.showMessageDialog(null, "Correct: "+ Iban.checkIban(iban)+"\nChecksum: "+Iban.calculateChecksum(iban), "IBAN-Checker", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: "+ ex.getMessage(),"IBAN-Checker",  JOptionPane.ERROR_MESSAGE);
        }
    }
}
