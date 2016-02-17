package server;
/*
 * A simple factoring example. Modified from an example by
 * Robert Sedgwick.
 */

import java.math.BigInteger;
import java.util.ArrayList;

public class Factoring {

    public static void main(String[] args) {
        
        BigInteger x = new BigInteger("13204940946069567749"); // 289 = 17 * 17
        ArrayList f = factor(x);
        for (int i = 0; i < f.size(); i++) {
            System.out.println(f.get(i));
        }
    }
    
    // factor n into primes
    public static ArrayList factor(BigInteger n) {

        ArrayList factors = new ArrayList();
       
        BigInteger two = new BigInteger("2");
        // for each potential factor i
        for ( BigInteger i = new BigInteger("3"); i.pow(2).compareTo(n) <= 0; i = i.add(two)) {
            // if i is a factor of N, repeatedly divide it out
            while (n.mod(i).compareTo(BigInteger.ZERO) == 0) {     
                factors.add(i);
                n = n.divide(i);
            }
        }
        // if biggest factor occurs only once, n > 1
        if (n.compareTo(BigInteger.ONE) > 0){
            System.out.println(n);
            factors.add(n);
        }
        else       
            System.out.println();
        return factors;
    }
}
