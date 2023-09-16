import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/***
 * Independent set in a tree: find the number of nodes in a given tree
 * for which no two are connected with an edge.
 *
 * This is a bottom-up, iterative approach to the same problem as IndSet1.
 * This also does not require object-oriented features: instead, the input tree
 * is given as an array of parent pointers. We do some linear time precomputation
 * in order to effectively in-line a topological sort on the bottom-up order
 * to process subtrees in. That is, we use a worklist algorithm to define how to
 * always process children before their parent.
 */
public class IndSet2 {
	public static List<Integer> largestIndSet(int[] parent){
		int[] cnts = countChildren(parent); //For each i, cnts[i] stores the number of children
											// i has that have yet to be considered by our algorithm
		int[] childSums = new int[parent.length]; //accumulated sum of the max ind set of
													// each child subtree of any given root
		int[] grandchildSums = new int[parent.length]; //Same as above, but for grandchildren
		int[] dp = new int[parent.length]; // Memoization table
		int root = findRoot(parent);
		
		Stack<Integer> worklist = new Stack<>(); //Worklist consists of i such that cnts[i] == 0 (topological ordering)
		for(int i =0; i<cnts.length; i++){
			if(cnts[i] == 0){
				worklist.push(i);
			}
		}
		while(!worklist.isEmpty()){
			int curr = worklist.pop();
			dp[curr] = Math.max(1+grandchildSums[curr], childSums[curr]);
			
			if(parent[curr] != curr){
				int p = parent[curr];
				cnts[p] --;
				if(cnts[p] == 0){
					worklist.push(p);
				}
				childSums[p] += dp[curr];
				if(parent[p] != p){
					grandchildSums[parent[p]] += dp[curr];
				}
			}
		}
		//Size of largest independent set is dp[root];
		//Backtracking to recover actual set:
		List<Integer> ret = new ArrayList<>();
		for(int i =0; i<dp.length; i++){
			if(dp[i] == childSums[i] +1){
				ret.add(i);
			}
		}
		return ret;
	}
	public static int findRoot(int[] parent){
		for(int i =0; i<parent.length; i++){
			if(parent[i] == i) return i;
		}
		return -1;
	}
	public static int[] countChildren(int[] parent){
		int[] cnt = new int[parent.length];
		for(int i =0; i<parent.length; i++){
			if(parent[i]!=i)
				cnt[parent[i]]++;
		}
		return cnt;
	}
	
}
