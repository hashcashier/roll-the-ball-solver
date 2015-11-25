package expressions.test;

import expressions.Substitution;
import expressions.Term;
import expressions.Unifier;
import terms.Constant;
import terms.Function;
import terms.Variable;

public class Test3 {
	public static void main(String[] args) {
		Variable u = new Variable("u");
		Variable v = new Variable("v");
		Variable w = new Variable("w");
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		Variable z = new Variable("z");
		
		Constant A = new Constant("A");
		Constant B = new Constant("B");
		Constant C = new Constant("C");
		
		Function fu = new Function(1, new Term[]{u}, "F");
		Function fa = new Function(1, new Term[]{A}, "F");
		Function gx = new Function(1, new Term[]{x}, "G");
		Function gfa = new Function(1, new Term[]{fa}, "G");
		
		Function P1 = new Function(3, new Term[]{x, gx, gfa}, "P");
		Function P2 = new Function(3, new Term[]{fu, v, v}, "P");
		
		System.out.println(P1);
		System.out.println(P2);
		Substitution s = Unifier.unify(P1, P2);
		System.out.println(s);
	}	
}
