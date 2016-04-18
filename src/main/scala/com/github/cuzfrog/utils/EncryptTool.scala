package com.github.cuzfrog.utils

import javax.crypto.spec.SecretKeySpec
import javax.crypto.Cipher

private[cuzfrog] object EncryptTool {
  def decrypt(encrypted: Array[Byte], key: Array[Byte]): String = {
    // Create key and cipher
    val aesKey = new SecretKeySpec(key, "AES");
    val cipher = Cipher.getInstance("AES");

    // decrypt the text
    cipher.init(Cipher.DECRYPT_MODE, aesKey);
    return new String(cipher.doFinal(encrypted), "utf8").replaceAll("(?:\\r\\n|\\n\\r|\\n|\\r)+",
      System.lineSeparator());
  }

  def encrypt(input: String, key: Array[Byte]): Array[Byte] = {
    // Create key and cipher
    val aesKey = new SecretKeySpec(key, "AES");

    val cipher = Cipher.getInstance("AES");
    // encrypt the text
    cipher.init(Cipher.ENCRYPT_MODE, aesKey);
    return cipher.doFinal(input.getBytes("utf8"));

  }
}