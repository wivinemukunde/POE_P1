package com.myproject.poepart1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PoePart1Test {

    @Test
    public void testUsernameFormatting() {
        String validUser = "kyl_1";
        String invalidUser = "kyle_smith";

        assertTrue(validUser.contains("_") && validUser.length() <= 5, "Username should be valid");
        assertFalse(invalidUser.contains("_") && invalidUser.length() <= 5, "Username should be invalid");
    }

    @Test
    public void testPasswordComplexity() {
        String strongPass = "Ch@nn3l21";
        String weakPass = "password";

        assertTrue(isPasswordStrong(strongPass), "Password should meet complexity requirements");
        assertFalse(isPasswordStrong(weakPass), "Password should fail complexity requirements");
    }

    private boolean isPasswordStrong(String password) {
        boolean hasCapital = false, hasNumber = false, hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasCapital = true;
            if (Character.isDigit(c)) hasNumber = true;
            if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return password.length() >= 8 && hasCapital && hasNumber && hasSpecial;
    }
    
    @Test
public void testUserRegistrationSuccess() {
    java.util.HashMap<String, Object> testUsers = new java.util.HashMap<>();
    
    String firstName = "John";
    String surname = "Doe";
    String username = "j_doe";
    String password = "Password123!";
    String phone = "0712345678";

    com.myproject.poepart1.PoePart1.User newUser = 
        new com.myproject.poepart1.PoePart1.User(username, password, phone, firstName, surname);
    
    testUsers.put(username, newUser);

    assertNotNull(testUsers.get("j_doe"), "User should be found in the map");
    assertEquals("John", ((com.myproject.poepart1.PoePart1.User)testUsers.get("j_doe")).firstName);
    assertEquals("0712345678", ((com.myproject.poepart1.PoePart1.User)testUsers.get("j_doe")).phoneNumber);
}
}