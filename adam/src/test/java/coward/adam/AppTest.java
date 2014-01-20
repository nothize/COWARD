package coward.adam;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    /**
     * Rigourous Test :-)
     */
    @Test
    public void testApp() throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		System.setOut(ps);
		App.main(new String[0]);
		ps.close();
		String s = new String(baos.toByteArray(), "UTF-8");
		assertEquals("Hello World!\r\n", s);
    }

}
