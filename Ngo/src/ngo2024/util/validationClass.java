/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024.util;

/**
 *
 * @author Fam van L
 */
// Klassen kontrollerar användarinmatning. 
public class validationClass {

    // Kontrollerar att strängen endast innehåller nummer.
    public static boolean isNumbers(String stringToCheck) {
        if (stringToCheck == null) {
            return false;
        }
        return stringToCheck.matches("\\d+");
    }

    // Kollar om strängen endast innehåller bokstäver och mellanslag.
    public static boolean isLetters(String stringToCheck) {
        if (stringToCheck == null) {
            return false;
        }
        return stringToCheck.matches("[a-zA-Z ]+"); 
    }

    // Kontrollerar om strängen är en giltig e-postadress.
    public static boolean isEmail(String stringToCheck) {
        if (stringToCheck == null) {
            return false;
        }

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return stringToCheck.matches(emailRegex);
    }

}
