package expressions.test;

import expressions.Substitution;
import expressions.Term;
import expressions.Unifier;
import terms.Constant;
import terms.Function;
import terms.Variable;

public class Test1 {

	public static void main(String[] args) {
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		Variable z = new Variable("z");
		
		Constant A = new Constant("A");
		Constant B = new Constant("B");
		Constant C = new Constant("C");
		
		Function f1 = new Function(1, new Term[]{x}, "F");
		Function f2 = new Function(1, new Term[]{y}, "F");
		
		Function g1 = new Function(4, new Term[]{x, y, x, y}, "G");
		Function g2 = new Function(4, new Term[]{y, z, x, B}, "G");
		
		// UNIFY: G(x, y, x, y) WITH G(y, z, x, B) USING 
		Substitution s = Unifier.unify(g1, g2);
		System.out.println(s);
	}

}
