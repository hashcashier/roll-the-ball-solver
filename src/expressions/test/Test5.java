package expressions.test;

import expressions.Substitution;
import expressions.Term;
import expressions.Unifier;
import terms.Constant;
import terms.Function;
import terms.Variable;

public class Test5 {
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
		
		Function gu = new Function(1, new Term[]{u}, "G");
		Function gz = new Function(1, new Term[]{z}, "G");
		Function ggz = new Function(1, new Term[]{gz}, "G");
		Function gx = new Function(1, new Term[]{x}, "G");
		
		Function P1 = new Function(3, new Term[]{x, gx, x}, "P");
		Function P2 = new Function(3, new Term[]{gu, ggz, z}, "P");
		
		System.out.println(P1);
		System.out.println(P2);
		Substitution s = Unifier.unify(P1, P2);
		System.out.println(s);
	}	

}
