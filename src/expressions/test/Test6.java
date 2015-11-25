package expressions.test;

import expressions.Term;
import sentences.Connector;
import sentences.Connector.Type;
import sentences.Negation;
import sentences.Predicate;
import sentences.Quantifier;
import terms.Variable;

public class Test6 {
	public static void main(String[] args) {
		Variable x1 = new Variable("x");
		Variable x2 = new Variable("x");
		Predicate px1 = new Predicate(1, new Term[]{x1}, "P");
		Predicate px2 = new Predicate(1, new Term[]{x2}, "P");
		Predicate qx2 = new Predicate(1, new Term[]{x2}, "Q");
		Negation npx2 = new Negation(px2);
		Connector qp = new Connector(Type.IMPLICATION, qx2, npx2);
		Quantifier xU = new Quantifier(Quantifier.Type.UNIVERSAL, x2, qp);
		Connector pxU = new Connector(Type.CONJUNCTION, px1, xU);
		Quantifier xE = new Quantifier(Quantifier.Type.EXISTENTIAL, x1, pxU);
		System.out.println(xE);
	}

}
