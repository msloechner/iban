/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibanchecker;

import java.math.BigInteger;

/**
 *
 * @author Markus Loechner
 */
public class Iban {

    /**
     * Calculate the checksum to a given Iban
     *
     * @param iban Iban as String
     * @return Checksum (digits 3 and 4)
     * @throws Exception Iban Format wrong
     */
    public static int calculateChecksum(String iban) throws Exception {
        char[] ibanChars = orderCharArray(prepareIbanString(iban).toCharArray());
        ibanChars[ibanChars.length - 1] = 48;
        ibanChars[ibanChars.length - 2] = 48;
        return calculate(ibanChars);
    }

    /**
     * Checks the checksum to a given Iban
     *
     * @param iban Iban as String
     * @return Iban correct
     * @throws Exception Iban Format wrong
     */
    public static boolean checkIban(String iban) throws Exception {
        char[] ibanChars = orderCharArray(prepareIbanString(iban).toCharArray());
        return calculate(ibanChars) == 97;
    }

    /**
     * Puts iban characters into right order for calculation
     * @param ibanChars prepared char-array representation of iban 
     * @see IBAN#prepareIbanString(java.lang.String) 
     * @return ordered Array
     * 
     */
    public static char[] orderCharArray(char[] ibanChars) {
        char[] ibanLastFour = new char[4];
        System.arraycopy(ibanChars, ibanChars.length - 4, ibanLastFour, 0, 4);
        System.arraycopy(ibanChars, 0, ibanChars, ibanChars.length - 4, 4);
        System.arraycopy(ibanChars, 4, ibanChars, 0, ibanChars.length - 4);
        System.arraycopy(ibanLastFour, 0, ibanChars, ibanChars.length - 8, 4);
        return ibanChars;
    }

    /**
     * Calculate the checksum from an ordered iban array
     * @see IBAN#orderCharArray(char[]) 
     * @param ibanChars
     * @return
     * @throws Exception 
     */
    public static int calculate(char[] ibanChars) throws Exception {
        BigInteger checksum = new BigInteger("0");
        int potence = 0;
        for (int arrayPos = ibanChars.length - 1; arrayPos >= 0; arrayPos--) {
            byte potencediff = 1;
            char c = ibanChars[arrayPos];
            if (!Character.isLetterOrDigit(c)) {
                throw new Exception("Only Alphanumeric letters are accepted!");
            }
            if (Character.isAlphabetic(c)) {
                c = (char) (c - 55);
                potencediff++;
            }
            if (Character.isDigit(c)) {
                c = (char) (c - 48);
            }
            checksum = checksum.add(new BigInteger(String.valueOf((int) c)).multiply(new BigInteger(String.valueOf(10)).pow(potence)));
            potence += potencediff;
        }
        checksum = new BigInteger(String.valueOf(98)).subtract(checksum.mod(new BigInteger(String.valueOf(97))));
        return checksum.intValue();
    }

    /**
     * Prepares Iban String for parsing
     * @param iban
     * @return prepared string
     */
    public static String prepareIbanString(String iban) {
        iban = iban.toUpperCase();
        return iban.replaceAll(" ", "");
    }
}
