package server;

/* Demonstrate RSA in Java using BigIntegers */

import java.math.BigInteger;
import java.util.Random;

/**
 *  RSA Algorithm from CLR
 * 
 * 1. Select at random two large prime numbers p and q.
 *  These numbers would normally be about 500 digits
 *  in length.
 * 2. Compute n by the equation n = p * q.
 * 3. Compute phi(n)=  (p - 1) * ( q - 1)
 * 4. Select a small odd integer e that is relatively prime to
 *  phi(n).
 * 5. Compute d as the multiplicative inverse of e
 *  modulo phi(n). A theorem in number theory asserts
 *  that d exists and is uniquely defined.
 * 6. Publish the pair P = (e,n) as the RSA public key.
 * 7. Keep secret the pair S = (d,n) as the RSA secret key.
 * 8. To encrypt a message M compute C = M^e (mod n)
 * 9. To decrypt a message C compute M = C^d (mod n)
 */
 
public class RSAExample {
  
  public static void main(String[] args) {
    // Each public and private key consists of an exponent and a modulus
    BigInteger n; // n is the modulus for both the private and public keys
    BigInteger e; // e is the exponent of the public key
    BigInteger d; // d is the exponent of the private key

    Random rnd = new Random();

    // Step 1: Generate two large random primes.
    // We use 400 bits here, but best practice for security is 2048 bits.
    // Change 400 to 2048, recompile, and run the program again and you will
    // notice it takes much longer to do the math with that many bits.
    BigInteger p = new BigInteger(32,100,rnd);
    BigInteger q = new BigInteger(32,100,rnd);

    p = new BigInteger("3351508813");
    q = new BigInteger("3939998873");
    // Step 2: Compute n by the equation n = p * q.
    n = p.multiply(q);

      System.out.println("n = " + n);
    // Step 3: Compute phi(n) = (p-1) * (q-1)
    BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
    
    // Step 4: Select a small odd integer e that is relatively prime to phi(n).
    // By convention the prime 65537 is used as the public exponent.
    e = new BigInteger ("32452867");

    // Step 5: Compute d as the multiplicative inverse of e modulo phi(n).
    d = e.modInverse(phi);
      System.out.println("d = " + d);
    
    System.out.println(" e = " + e);  // Step 6: (e,n) is the RSA public key
    System.out.println(" d = " + d);  // Step 7: (d,n) is the RSA private key
    System.out.println(" n = " + n);  // Modulus for both keys
    
    // Encode a simple message. For example the letter 'A' in UTF-8 is 65
    BigInteger m = new BigInteger("65");

    // Step 8: To encrypt a message M compute C = M^e (mod n)
    BigInteger c = m.modPow(e, n);
    
    // Step 9: To decrypt a message C compute M = C^d (mod n)
    BigInteger clear = c.modPow(d, n);
    System.out.println("Cypher text = " + c);
    System.out.println("Clear text = " + clear); // Should be "65"
    
    // Step 8 (reprise) Encrypt the string 'Hello'
    String s = "smartYi";
    m = new BigInteger(s.getBytes()); // m is the original clear text
    n = new BigInteger("10463462328113068009");
    c = m.modPow(e, n);     // Do the encryption, c is the cypher text
     System.out.println("c = " + c);

    // Step 9 (reprise) Decrypt...
    d = new BigInteger("12881358949647221227");
    n = new BigInteger("13204940946069567749");
    c = new BigInteger("5414930876168453956");
    clear = c.modPow(d, n); // Decrypt, clear is the resulting clear text
    String clearStr = new String(clear.toByteArray());  // Decode to a string

    System.out.println("Cypher text = " + c);
    System.out.println("Clear text = " + clearStr);
  }
}
