package kz.stqa.prf.sandbox;

import kz.stqa.pft.sandbox.Primes;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by xeniya on 6/10/16.
 */
public class PrimeTests {

    @Test
    public void testPrimes(){
        Assert.assertTrue(Primes.isPrime(Integer.MAX_VALUE));
    }

    @Test(enabled = false)
    public void testPrimeLong(){
        long n = Integer.MAX_VALUE;
        Assert.assertTrue(Primes.isPrime(n));
    }

    @Test
    public void testNonPrime(){
        Assert.assertFalse(Primes.isPrime(Integer.MAX_VALUE-2));
    }

    @Test
    public void testPrimeFast(){
        Assert.assertTrue(Primes.isPrimeFast(Integer.MAX_VALUE));
    }
}
