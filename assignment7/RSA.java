package assignment7;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.util.Random;

public class RSA {
    private BigInteger P;
    private BigInteger Q;
    private BigInteger N;
    private BigInteger PHI;
    private BigInteger e;
    private BigInteger d;
    private int maxLength = 1024;
    private Random R;

    public RSA() {
        R = new Random();
        P = BigInteger.probablePrime(maxLength, R);
        Q = BigInteger.probablePrime(maxLength, R);
        N = P.multiply(Q);
        PHI = P.subtract(BigInteger.ONE).multiply(Q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(maxLength / 2, R);
        
        // Ensure that 1 < e < PHI and gcd(e, PHI) = 1
        while (PHI.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(PHI) < 0) {
            e = e.add(BigInteger.ONE);
        }
        d = e.modInverse(PHI);
    }

    // public key {e, N} private key {d, N}
    public static void main(String[] arguments) throws IOException {
        RSA rsa = new RSA();
        DataInputStream input = new DataInputStream(System.in);
        String inputString;

        // Print the public and private keys
        System.out.println("Public Key (e, N): (" + rsa.e + ", " + rsa.N + ")");
        System.out.println("Private Key (d, N): (" + rsa.d + ", " + rsa.N + ")");

        System.out.println("Enter the message you wish to send.");
        inputString = input.readLine();

        System.out.println("Encrypting the message: " + inputString);
        System.out.println("The message in bytes is: " + bToS(inputString.getBytes()));

        // encryption
        byte[] cipher = rsa.encryptMessage(inputString.getBytes());
        System.out.println("Encrypted Message: " + bToS(cipher));

        // decryption
        byte[] plain = rsa.decryptMessage(cipher);
        System.out.println("Decrypting Bytes: " + bToS(plain));
        System.out.println("Plain message is: " + new String(plain));
    }

    private static String bToS(byte[] cipher) {
        StringBuilder temp = new StringBuilder();
        for (byte b : cipher) {
            temp.append(Byte.toString(b));
        }
        return temp.toString();
    }

    // Encrypting the message
    public byte[] encryptMessage(byte[] message) {
        return (new BigInteger(message)).modPow(e, N).toByteArray();
    }

    // Decrypting the message
    public byte[] decryptMessage(byte[] message) {
        return (new BigInteger(message)).modPow(d, N).toByteArray();
    }
}
