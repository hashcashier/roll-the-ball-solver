package expressions;

import java.util.Arrays;
import java.util.LinkedList;

import sentences.Predicate;
import terms.Constant;
import terms.Function;
import terms.Variable;

public class Unifier {
	
	public static Substitution unify(Expression E1, Expression E2) {
		return unify1(E1, E2, new Substitution());
	}
	
	private static LinkedList<Expression> listify(Expression e) {
		LinkedList<Expression> result = new LinkedList<>();
		if (e instanceof Constant || e instanceof Variable) {
			result.add(e);
		} else if(e instanceof Function) {
			Function f = (Function) e;
			result.add(new Constant(f.getName()));
			result.addAll(Arrays.asList(f.getChildren()));
		} else if(e instanceof Predicate) {
			Predicate p = (Predicate) e;
			result.add(new Constant(p.getName()));
			result.addAll(Arrays.asList(p.getChildren()));
		}
		return result;
	}
	
	private static Substitution unify1(Expression E1, Expression E2, Substitution mu) {
//		System.out.println("UNIFY: " + E1.toString() + " WITH " + E2.toString() + " USING " + mu.toString());
		if (mu == Substitution.FAILURE) {
			return Substitution.FAILURE;
		} else if (E1 == E2) {
			return mu;
		} else if(E1 instanceof Variable && E2 instanceof Term) {
			return unifyVar((Variable) E1, (Term) E2, mu);
		} else if(E2 instanceof Variable && E1 instanceof Term) {
			return unifyVar((Variable) E2, (Term) E1, mu);
		} else if(E1 instanceof Constant && E2 instanceof Constant) {
			return E1.equals(E2) ? mu : Substitution.FAILURE;
		} else if(E1 instanceof Variable && E2 instanceof Expression) {
			return Substitution.FAILURE;
		} else if(E2 instanceof Variable && E1 instanceof Expression) {
			return Substitution.FAILURE;
		}
		
		return unify1(listify(E1), listify(E2), mu);
	}
	
	private static Substitution unify1(LinkedList<Expression> E1, LinkedList<Expression> E2, Substitution mu) {
//		System.out.println("UNIFY: " + E1.toString() + " WITH " + E2.toString() + " USING " + mu.toString());
		if (mu == Substitution.FAILURE) {
			return Substitution.FAILURE;
		} else if(E1.size() != E2.size()) {
			return Substitution.FAILURE;
		} else if(E1.isEmpty()) {
			return mu;
		}
		
		return unify1(E1, E2, unify1(E1.removeFirst(), E2.removeFirst(), mu));
	}
	
	private static Substitution unifyVar(Variable v, Term e, Substitution mu) {
		Term t = mu.get(v);
		if (t != null && t != v) {
			return unify1(t, e, mu);
		}
		t = (Term) substitute(mu, e);
		if (v.occursIn(t)) {
			return Substitution.FAILURE;
		}
		return mu.set(v, t);
	}
	
	public static Expression substitute(Substitution mu, Expression e) {
		Expression[] children = e.getChildren();
		
		if (e instanceof Variable) {
			Variable v = (Variable) e;
			Term t = mu.get(v);
			
			return t == null || t instanceof Variable ? e : t;
		}
		
		for (int i = 0; children != null && i < children.length; i++) {
			Expression child = children[i];
			if (child instanceof Variable) {
				Term replacement = mu.get((Variable) child);
				if (replacement != null) {
					children[i] = replacement;
				}
			} else if (child.getArity() > 0) {
				substitute(mu, child);
			}
		}
		return e;
	}
}
