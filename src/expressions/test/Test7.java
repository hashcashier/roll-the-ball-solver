package expressions.test;

import expressions.Term;
import sentences.Connector;
import sentences.Negation;
import sentences.Predicate;
import sentences.Quantifier;
import sentences.Connector.Type;
import terms.Variable;

public class Test7 {
	public static void main(String[] args) {
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		
		Predicate px = new Predicate(1, new Term[]{x}, "P");
		Predicate qx = new Predicate(1, new Term[]{x}, "Q");
		Predicate qy = new Predicate(1, new Term[]{y}, "Q");
		Predicate ryx = new Predicate(2, new Term[]{y, x}, "R");
		
		Connector qr = new Connector(Type.CONJUNCTION, qy, ryx);
		Quantifier yE = new Quantifier(Quantifier.Type.EXISTENTIAL, y, qr);
		Connector qyE = new Connector(Type.CONJUNCTION, qx, yE);
		Connector pqyE = new Connector(Type.BIIMPLICATION, px, qyE);
		Quantifier xU = new Quantifier(Quantifier.Type.UNIVERSAL, x, pqyE);
		
		System.out.println(xU);
	}

}
