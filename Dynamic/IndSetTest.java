import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IndSetTest {
	@Test
	public void test2(){
		int[] parent = new int[]{0, 0, 0, 1, 1, 2, 4, 4};
		List<Integer> res = IndSet2.largestIndSet(parent);
		int[] maxIndSet = new int[] {0,3,5,6,7};
		for(int i: maxIndSet){
			assertEquals(true, res.contains(i));
		}
	}
	@Test
	public void test1(){
		IndSet1.Node root = new IndSet1.Node(1);
		IndSet1.Node n2 = new IndSet1.Node(2);
		IndSet1.Node n3 = new IndSet1.Node(3);
		IndSet1.Node n4 = new IndSet1.Node(4);
		IndSet1.Node n5 = new IndSet1.Node(5);
		IndSet1.Node n6 = new IndSet1.Node(6);
		IndSet1.Node n7 = new IndSet1.Node(7);
		IndSet1.Node n8 = new IndSet1.Node(8);
		
		n5.children.add(n7);
		n5.children.add(n8);
		
		n2.children.add(n4);
		n2.children.add(n5);
		
		n3.children.add(n6);
		root.children.add(n2);
		root.children.add(n3);
		
		List<Integer> res = IndSet1.largestIndSubset(root);
		assertEquals(5, res.size());
		int[] maxIndSet = new int[] {1,4,6,7,8};
		for(int i: maxIndSet){
			assertEquals(true, res.contains(i));
		}
	}
	
}
