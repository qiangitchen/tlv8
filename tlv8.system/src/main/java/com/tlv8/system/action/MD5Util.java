package com.tlv8.system.action;

import java.security.MessageDigest;
import java.util.Random;

public class MD5Util
{
  private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

  public static final Random random = new Random(System.currentTimeMillis());

  public static String encode(String origin)
  {
    String resultString = null;
    try
    {
      resultString = new String(origin);
      MessageDigest md = MessageDigest.getInstance("MD5");
      resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
    }
    catch (Exception ex)
    {
    }

    return resultString;
  }

  private static String byteArrayToHexString(byte[] b) {
    StringBuffer resultSb = new StringBuffer();
    for (int i = 0; i < b.length; i++) {
      resultSb.append(byteToHexString(b[i]));
    }
    return resultSb.toString().toUpperCase();
  }

  private static String byteToHexString(byte b)
  {
    int n = b;
    if (n < 0)
      n = 256 + n;
    int d1 = n / 16;
    int d2 = n % 16;
    return hexDigits[d1] + hexDigits[d2];
  }

  public static void main(String[] args) {
    encode("demo" + System.currentTimeMillis());
  }
}