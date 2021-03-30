package com.github.guifabrin.votes.rest.v1.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChyperUtils {
    public static String encrypt(String value) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(value.getBytes("utf8"));
        return String.format("%040x", new BigInteger(1, digest.digest()));
    }
}
