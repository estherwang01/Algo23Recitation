import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class PrettyPrintTest {
	
	/*
	* This is an example helper method which creates an inputstream from a string, as well
	* as an output stream, so that it can capture any 'output' from the algorithm.
	*
	* Testing functions which read from standardin and write to standardout can be tricky: you may
	* be tempted to just manually input test cases. However, if you imagine a more complex problem
	* with say 100 tests you have in mind you'd like to have pass, if you make a single change,
	* you now need to manually run the program 100 times. If you take just a little extra time to
	* set up a little testing infrastructure, you can run all 100 inputs with the click of a single button.
	* */
	public void helper(String input, String expectedOutput){
		InputStream in = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		PrettyPrint.runAlgorithm(in, out);
		String outputReceived = out.toString();
		Assertions.assertEquals(expectedOutput.trim(), outputReceived.trim());
	}
	
	/*
	* The following is by no means a comprehensive test suite. It should however give you a brief idea
	* of some example small test cases / edge cases, and what junit testing may look like.
	*
	* With this structure, you can also easily copy over public test cases from the autograder, and not have
	* to reupload your code to gradescope every time you want to try out the public test cases on your code.
	* */
	@Test
	public void singleWord(){
		String input = "10\nhello";
		String expectedOutput = "25\n1\n";
		helper(input, expectedOutput);
	}
	
	@Test
	public void noWords(){
		String input = "10\n";
		String expectedOutput = "0\n";
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
