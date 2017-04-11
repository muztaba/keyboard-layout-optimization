package com.seal;

import com.seal.util.Key;
import com.seal.util.StaticUtil;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        Key key = Key.build(new String[]{"f", "2", "3", "l", "f"});
        double row = StaticUtil.getRow(key.getPosition().x) / 100.0;
        double col = StaticUtil.getCol(key.getPosition().y) / 100.0;


        System.out.println(30 * row * col * 0.50);
    }
}
