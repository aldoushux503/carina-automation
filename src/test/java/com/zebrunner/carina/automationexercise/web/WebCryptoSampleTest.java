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
        String newPassword = "1234";
        String encryptedPassword = cryptoTool.encrypt(newPassword);

        System.out.println("Original password: " + newPassword);
        System.out.println("Encrypted password: " + encryptedPassword);
        System.out.println("For configuration use: {crypt:" + encryptedPassword + "}");

        String decryptedPassword = cryptoTool.decrypt(encryptedPassword);
        Assert.assertEquals(decryptedPassword, newPassword);
    }

    @Test
    public void testEncryptionToolUsage() {
        String decryptedManually = cryptoTool.decrypt("8O9iA4+f3nMzz85szmvKmQ==");
        Assert.assertEquals(decryptedManually, "EncryptMe");
    }
}