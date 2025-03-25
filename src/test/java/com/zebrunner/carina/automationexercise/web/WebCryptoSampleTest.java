package com.zebrunner.carina.automationexercise.web;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.crypto.Algorithm;
import com.zebrunner.carina.crypto.CryptoTool;
import com.zebrunner.carina.crypto.CryptoToolBuilder;
import com.zebrunner.carina.utils.R;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 * This test demonstrates how to use Carina's crypto functionality.
 */
public class WebCryptoSampleTest implements IAbstractTest {

    private CryptoTool cryptoTool;
    @BeforeSuite
    public void cryptoToolBuild() {
        cryptoTool = CryptoToolBuilder.builder()
                .setKey(R.CONFIG.get("crypto_key_value"))
                .chooseAlgorithm(Algorithm.AES_ECB_PKCS5_PADDING)
                .build();
    }


    @Test
    public void testEncryptNewValue() {
        String originalPassword = "1234";
        String encryptedPassword = cryptoTool.encrypt(originalPassword);

        String decryptedPassword = cryptoTool.decrypt(encryptedPassword);
        Assert.assertEquals(decryptedPassword, originalPassword,
                "Decrypted password should match the original password");
    }

    @Test
    public void testDecryptPredefinedValue() {
        String expectedValue = "EncryptMe";
        String encryptedValue = "8O9iA4+f3nMzz85szmvKmQ==";

        String decryptedValue = cryptoTool.decrypt(encryptedValue);
        Assert.assertEquals(decryptedValue, expectedValue,
                "Decrypted value should match the expected value");
    }

    @Test
    public void testEncryptWithSpecialCharacters() {
        String originalText = "Password!@#$%^&*()";
        String encryptedText = cryptoTool.encrypt(originalText);

        String decryptedText = cryptoTool.decrypt(encryptedText);
        Assert.assertEquals(decryptedText, originalText,
                "Decrypted text with special characters should match the original");
    }

}