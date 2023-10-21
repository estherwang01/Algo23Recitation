import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PrettyPrint {
	/***
	 * Given a list of words and a character width W, return the indices of words which should be followed
	 * by a linebreak in the optimal pretty print of all words. Optimal is defined to minimize slack, which
	 * is the sum of squares of "extra" spaces at the end of each line. Your program will be run from function [main]
	 *
	 * Input is given through stdin as
	 * W
	 * w_1, ...w_n (words in a comma separated list)
	 *
	 * Output should be through stdout, in the form
	 * S (slack associated with optimal arrangement)
	 * i_1
	 * ...
	 * i_k (line break indices: line breaks after these words)
	 * (newline)
	 */
	
	public static void main(String[] args) {
		runAlgorithm(System.in, System.out);
	}
	
	/***
	 * Easily unit-testable by using a different input / output stream.
	 * Main simply passes in stdin and stdout as in the input/output stream to conform to spec.
	 *
	 * Unit tests are therefore able to pass in their own streams to capture output stream values.
	 * Therefore, we can test this method (and be able to test not only the DP logic, but also the
	 * input parsing / output printing).
	 */
	public static void runAlgorithm(InputStream i, OutputStream o) {
		try{
			//Parse IO using bufferedreader and stringtokenizer
			BufferedReader br = new BufferedReader(new InputStreamReader(i));
			BufferedWriter bw = new  BufferedWriter(new OutputStreamWriter(o));
			int W = Integer.parseInt(br.readLine());
			String next = br.readLine();
			
			if(next == null || next.equals("")){
				String res = 0 + "\n";
				bw.write(res);
				bw.flush();
				return;
			}
			
			StringTokenizer st = new StringTokenizer(next, ",");
			List<String> words = new ArrayList<>();
			while(st.hasMoreTokens()){
				words.add(st.nextToken());
			}
			
			//Set up data structures
			int[][] slack = precomputeSlack(W, words);
			int[] opt = new int[words.size()+1];
			int[] back = new int[words.size()+1];
			
			int sol = prettyPrintBottomUp(W, words, opt, back, slack);
			
			//backtrace to find set which achieves optimal value determined above
			List<Integer> breaks = backtrace(back, words.size());
			
			//print out solution in expected format using a bufferedwriter
			bw.write(sol + "\n");
			for(int b: breaks){
				bw.write(b + "\n");
			}
			bw.flush();
			br.close();
			bw.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/*
	* Separating out this logic from the main entrypoint function makes it easily unit-testable on its own
	* Modularity of code and individually testing methods is a powerful tool for isolating bugs to a specific portion
	* of your algorithm.
	* */
	public static int[][] precomputeSlack(int W, List<String> words){
		int[][] slack = new int[words.size()+1][words.size()+1]; //earlier word indexes first
		//slack[i][j] computes slack for including words i through j inclusive on the same line
		//-1 indicates infinity (i.e., invalid to place these words on the same line)
		for(int i =1; i<=words.size(); i++){
			for(int j =i; j <= words.size(); j++){
				int res = -1;
				if(i == j) res = W - words.get(i-1).length();
				else {
					int prev = (int)(Math.sqrt(slack[i][j-1]));
					int curr  = words.get(j-1).length();
					res = prev- curr;
				}
				slack[i][j] = res < 0 ? W*W+1 : res*res;
			}
		}
		return slack;
	}
	
	/*
	* This is a standard looking backtracing procedure of following parent pointers.
	* Again, isolating it to its own method means it is unit testable. Even before you write
	* out the core DP logic, you can manually construct the input and make sure it does what you'd expect.
	* */
	public static List<Integer> backtrace(int[] back, int start){
		ArrayList<Integer> solution = new ArrayList<>();
		int curr = start;
		solution.add(0, curr);
		while(back[curr] != curr){
			curr = back[curr];
			solution.add(0, curr);
		}
		return solution;
	}
	
	
	/*
	* Core DP logic for this problem. As it proceeds, it fills out its argument arrays which have been
	* passed by reference.
	* */
	public static int prettyPrintBottomUp(int W, List<String> words, int[] opt, int[] back, int[][] slack){
		opt[0] = 0;
		back[0] = 0;
		for(int i =1; i<=words.size(); i++){
			int minIndex = Integer.MAX_VALUE;
			int minValue = Integer.MAX_VALUE;
			for(int j = 1; j<= i; j++){
				int candidate = slack[j][i] + opt[j-1];
				if(candidate < minValue){
					minIndex = j;
					minValue = candidate;
				}
			}
			opt[i] = minValue;
			back[i] = minIndex;
			
		}
		return opt[words.size()];
	}
	
}
