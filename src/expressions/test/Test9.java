package expressions.test;

import expressions.ClauseFormer;
import expressions.Term;
import sentences.Connector;
import sentences.Connector.Type;
import sentences.Predicate;
import sentences.Quantifier;
import terms.Variable;

public class Test9 {
	public static void main(String[] args) {
		Variable x = new Variable("x");
		
		Predicate px = new Predicate(1, new Term[]{x}, "P");
		Predicate qx = new Predicate(1, new Term[]{x}, "Q");
		
		Connector qr = new Connector(Type.BIIMPLICATION, px, qx);
		Quantifier xU = new Quantifier(Quantifier.Type.UNIVERSAL, x, qr);
		
		System.out.println(xU);
		System.out.println(ClauseFormer.clauseForm(xU));
	}

}
