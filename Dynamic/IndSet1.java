import java.util.ArrayList;
import java.util.List;

/***
 * Independent set in a tree: find the number of nodes in a given tree
 * for which no two are connected with an edge.
 *
 * This is a top down, object-oriented approach to the problem, which stores DP
 * results within tree nodes themselves. The subproblem is on the subtrees
 * rooted at the children of any given node.
 */
public class IndSet1 {
	public static class Node {
		int id, dp, inc, exc; //unique identifier, along with implicit dp table entry and backtracking information
		List<Node> children;
		public Node(int id) {
			this.id = id;
			dp = 0;
			children = new ArrayList<>();
		}
	}
	public static int largestIndSubsetSize(Node root){
		if(root == null) return 0;
		if(root.dp != 0) return root.dp;
		//Leaf: has no children so always include
		if(root.children == null || root.children.size() == 0){
			root.dp = 1;
			root.inc = 1;
			return 1;
		}
		//Case 1: include this node, and exclude all children
		int sumInclude = 1;
		for(Node child: root.children){
			if(child.children == null) continue;
			for(Node grandChild: child.children){
				sumInclude += largestIndSubsetSize(grandChild);
			}
		}
		//Case 2: Exclude this node
		int sumExclude = 0;
		for(Node child: root.children){
			sumExclude += largestIndSubsetSize(child);
		}
		root.dp = Math.max(sumInclude, sumExclude);
		root.inc = sumInclude;
		root.exc = sumExclude;
		return root.dp;
	}
	
	public static List<Integer> largestIndSubset(Node root){
		largestIndSubsetSize(root);
		List<Integer> set = new ArrayList<>();
		getIncluded(root, set);
		return set;
	}
	
	//Backtrack to determine the set itself.
	public static void getIncluded(Node root, List<Integer> acc){
		int sum = 0;
		for(Node child: root.children){
			sum += child.dp;
			getIncluded(child, acc);
		}
		if(sum != root.dp){
			acc.add(root.id);
		}
		
	}
}
