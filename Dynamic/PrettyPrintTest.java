import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class PrettyPrintTest {
	
	public void helper(String input, String expectedOutput){
		InputStream in = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		PrettyPrint.runAlgorithm(in, out);
		String outputReceived = out.toString();
		Assertions.assertEquals(expectedOutput.trim(), outputReceived.trim());
	}
	
	@Test
	public void singleWord(){
		String input = "10\nhello";
		String expectedOutput = "25\n1\n";
		helper(input, expectedOutput);
	}
	
	@Test
	public void noWords(){
		String input = "10\n";
		String expectedOutput = "100\n";
		helper(input, expectedOutput);
	}
	
	@Test
	public void multipleWords(){
		String input = "10\nhello,there,8letters";
		String expectedOutput = "4\n3\n";
	}
	
	@Test
	public void singleLineBest(){
		String input = "10\na,b,c,d,e";
		String expectedOutput = "25\n5\n";
	}
	
}
