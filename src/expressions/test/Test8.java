package expressions.test;

import expressions.ClauseFormer;
import expressions.Term;
import sentences.Connector;
import sentences.Predicate;
import sentences.Quantifier;
import terms.Variable;

public class Test8 {
	public static void main(String[] args) {
		Variable w = new Variable("w");
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		Variable z = new Variable("z");
		
		Predicate qw = new Predicate(1, new Term[]{w}, "Q");
		Predicate py = new Predicate(1, new Term[]{y}, "P");
		Predicate pz = new Predicate(1, new Term[]{z}, "P");
		Quantifier qE = new Quantifier(Quantifier.Type.EXISTENTIAL, w, qw);
		Quantifier yE = new Quantifier(Quantifier.Type.EXISTENTIAL, y, py);
		Quantifier zE = new Quantifier(Quantifier.Type.EXISTENTIAL, z, pz);
		
		Connector yz = new Connector(Connector.Type.CONJUNCTION, yE, zE);
		Quantifier xU = new Quantifier(Quantifier.Type.UNIVERSAL, x, yz);

		Connector qwxU = new Connector(Connector.Type.CONJUNCTION, qE, xU);

		System.out.println(qwxU);
		System.out.println(ClauseFormer.clauseForm(qwxU));
	}

}
