package expressions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import sentences.Connector;
import sentences.Equality;
import sentences.Negation;
import sentences.Predicate;
import sentences.Quantifier;
import terms.Constant;
import terms.Function;
import terms.Variable;

public class ClauseFormer {
	public static Sentence clauseForm(Sentence wellFormed) {
		return clauseForm(wellFormed, false);
	}
	
	public static Sentence clauseForm(Sentence wellFormed, boolean trace) {
		wellFormed = removeBiImplication(wellFormed);
		if (trace) {
			System.out.println("Bi-implication:");
			System.out.println(wellFormed);
		}
		wellFormed = removeImplication(wellFormed);
		if (trace) {
			System.out.println("Implication:");
			System.out.println(wellFormed);
		}
		wellFormed = pushNegation(wellFormed);
		if (trace) {
			System.out.println("Negation:");
			System.out.println(wellFormed);
		}
		wellFormed = standardize(wellFormed);
		if (trace) {
			System.out.println("Std:");
			System.out.println(wellFormed);
		}
		wellFormed = skolemize(wellFormed);
		if (trace) {
			System.out.println("Skolem:");
			System.out.println(wellFormed);
		}
		wellFormed = dropUniversals(wellFormed);
		if (trace) {
			System.out.println("Universal:");
			System.out.println(wellFormed);
		}
		wellFormed = distribute(wellFormed);
		if (trace) {
			System.out.println("Distrib:");
			System.out.println(wellFormed);
		}
		wellFormed = flatten(wellFormed);
		if (trace) {
			System.out.println("Flat:");
			System.out.println(wellFormed);
		}
		outputClauseForm(wellFormed);
		
		return wellFormed;
	}
	
	public static void outputClauseForm(Sentence conjNF) {
		System.out.println("Clause Form:");

		boolean a = false, b;
		System.out.print("{");
		int seen = 0;
		for (Sentence disj : (Sentence[]) conjNF.getChildren()) {

			HashSet<Variable> seenHere = new HashSet<>();
			disj = standardize(disj, seen, seenHere);
			seen += seenHere.size();
			
			if (a) System.out.println(",");
			System.out.print("{");
			if (disj instanceof Connector) {
				b = false;
				for (Sentence pred : (Sentence[]) disj.getChildren()) {
					if (b) System.out.print(", ");
					System.out.print(pred);
					b = true;
				}
			} else {
				System.out.println(disj);
			}
			System.out.print("}");
			a = true;
		}
		System.out.println("}");
		
		
	}
	
	private static Sentence removeBiImplication(Sentence s) {
		if (s instanceof Connector) {
			Connector c = (Connector) s;
			if (c.getType() == Connector.Type.BIIMPLICATION) {
				Connector L2R = new Connector(Connector.Type.IMPLICATION, (Sentence) c.getChildren()[0], (Sentence) c.getChildren()[1]);
				HashMap<Variable, Variable> mutatedVars = new HashMap<>();
				Connector R2L = new Connector(
						Connector.Type.IMPLICATION,
						(Sentence) mutateQuantifiers(c.getChildren()[1], mutatedVars), 
						(Sentence) mutateQuantifiers(c.getChildren()[0], mutatedVars));
				s = new Connector(Connector.Type.CONJUNCTION, L2R, R2L);
			}
		}
		
		for (int i = 0; i < s.getArity(); i++) {
			Expression child = s.getChildren()[i];
			if (child instanceof Sentence) {
				s.getChildren()[i] = removeBiImplication((Sentence) child);
			}
		}
		
		return s;
	}
	
	private static Expression mutateQuantifiers(Expression e, HashMap<Variable, Variable> map) {
		Expression ne = e;
		int arity = e.getArity();
		if (e instanceof Quantifier) {
			Quantifier q = (Quantifier) e;
			Variable v = q.getVariable();
			Variable neu = new Variable(v.getName());
			map.put(v, neu);
			ne = new Quantifier(q.getType(), neu, (Sentence) q.getChildren()[0]);
		} else if (e instanceof Variable) {
			Variable v = (Variable) e;
			Variable neu = map.get(v);
			return neu == null ? v : neu;
		} else if (e instanceof Connector) {
			Connector c = (Connector) e;
			ne = new Connector(c.getType(), c.getArity(), new Sentence[arity]);
		} else if (e instanceof Equality) {
			Equality E = (Equality) e;
			ne = new Equality((Term) E.getChildren()[0], (Term) E.getChildren()[1]);
		} else if (e instanceof Negation) {
			Negation n = (Negation) e;
			ne = new Negation((Sentence) n.getChildren()[0]);
		} else if (e instanceof Predicate) {
			Predicate p = (Predicate) e;
			ne = new Predicate(p.getArity(), new Term[arity], p.getName());
		} else if (e instanceof Function) {
			Function f = (Function) e;
			ne = new Function(f.getArity(), new Term[arity], f.getName());
		}
		
		for (int i = 0; i < e.getArity(); i++) {
			ne.getChildren()[i] = mutateQuantifiers(e.getChildren()[i], map);
		}
		return ne;
	}

	private static Sentence removeImplication(Sentence s) {
		if (s instanceof Connector) {
			Connector c = (Connector) s;
			if (c.getType() == Connector.Type.IMPLICATION) {
				Negation notA = new Negation((Sentence) c.getChildren()[0]);
				s = new Connector(Connector.Type.DISJUNCTION, notA, (Sentence) c.getChildren()[1]);
			}
		}
		
		for (int i = 0; i < s.getArity(); i++) {
			Expression child = s.getChildren()[i];
			if (child instanceof Sentence) {
				s.getChildren()[i] = removeImplication((Sentence) child);
			}
		}
		
		return s;
	}

	private static Sentence pushNegation(Sentence s) {
		if (s instanceof Negation) {
			Sentence c = (Sentence) s.getChildren()[0];
			
			if (c instanceof Quantifier) {
				Quantifier q = (Quantifier) c;
				Negation not = new Negation((Sentence) q.getChildren()[0]);
				Quantifier.Type neuType = q.getType() == Quantifier.Type.EXISTENTIAL ? Quantifier.Type.UNIVERSAL :Quantifier.Type.EXISTENTIAL;  
				s = new Quantifier(neuType, q.getVariable(), not);
			} else if(c instanceof Connector) {
				Connector con = (Connector) c;
				Connector.Type neuType = con.getType() == Connector.Type.CONJUNCTION ? Connector.Type.DISJUNCTION: Connector.Type.CONJUNCTION;
				s = new Connector(neuType, new Negation((Sentence) con.getChildren()[0]), new Negation((Sentence) con.getChildren()[1]));
			} else if(c instanceof Negation){
				return pushNegation((Sentence) c.getChildren()[0]);
			}
		}
		
		for (int i = 0; i < s.getArity(); i++) {
			Expression child = s.getChildren()[i];
			if (child instanceof Sentence) {
				s.getChildren()[i] = pushNegation((Sentence) child);
			}
		}

		return s;
	}

	private static Sentence standardize(Sentence s) {
		Set<Variable> variables = new HashSet<>();
		getAllVars(s, variables);
		
		Map<Variable, Variable> map = new HashMap<>();
		for(Variable v : variables) {
			map.put(v, new Variable("x"+map.size()));
		}
		
		return (Sentence) standardize(s, map, new HashSet<Expression>());
	}

	private static Sentence standardize(Sentence s, int seen, Set<Variable> variables) {
		getAllVars(s, variables);
		
		Map<Variable, Variable> map = new HashMap<>();
		for(Variable v : variables) {
			map.put(v, new Variable("x" + seen++));
		}

		return replace(s, map);
	}
	
	private static Sentence replace(Sentence s, Map<Variable, Variable> map) {
		if (s instanceof Negation) {
			return new Negation(replace((Sentence)s.getChildren()[0], map));
		} else if(s instanceof Predicate) {
			Sentence cpy = (Sentence) emptyCopy(s);
			for (int i = 0; i < s.getArity(); i++) {
				cpy.getChildren()[i] = replace((Term) s.getChildren()[i], map);
			}
			return cpy;
		} else if(s instanceof Connector) {
			Sentence cpy = (Sentence) emptyCopy(s);
			for (int i = 0; i < s.getArity(); i++) {
				cpy.getChildren()[i] = replace((Sentence) s.getChildren()[i], map);
			}
			return cpy;
		}
		return null;
	}
	
	private static Term replace(Term t, Map<Variable, Variable> map) {
		if (t instanceof Variable) {
			return map.get(t);
		} else if(t instanceof Function) {
			Term cpy = (Term) emptyCopy(t);
			for (int i = 0; i < t.getArity(); i++) {
				cpy.getChildren()[i] = replace((Term) t.getChildren()[i], map);
			}
			return cpy;
		}
		return null;
	}

	private static Expression standardize(Expression e, Map<Variable, Variable> map, Set<Expression> done) {
		if (done.contains(e)) {
			return e;
		}
		
		if (e instanceof Quantifier) {
			Quantifier q = (Quantifier) e;
			e = new Quantifier(q.getType(), map.get(q.getVariable()), (Sentence) q.getChildren()[0]);
		} else if (e instanceof Variable) {
			e = map.get((Variable) e);
		}

		for (int i = 0; i < e.getArity(); i++) {
			Expression child = e.getChildren()[i];
			e.getChildren()[i] = standardize(child, map, done);
		}
		
		done.add(e);

		return e;
	}
	
	private static Expression emptyCopy(Expression e) {
		int arity = e.getArity();
		if (e instanceof Quantifier) {
			Quantifier q = (Quantifier) e;
			return new Quantifier(q.getType(), (Variable) emptyCopy(q.getVariable()), (Sentence) q.getChildren()[0]);
		} else if (e instanceof Variable) {
			Variable v = (Variable) e;
			return new Variable(v.getName());
		} else if (e instanceof Connector) {
			Connector c = (Connector) e;
			return new Connector(c.getType(), c.getArity(), new Sentence[arity]);
		} else if (e instanceof Equality) {
			Equality E = (Equality) e;
			return new Equality((Term) E.getChildren()[0], (Term) E.getChildren()[1]);
		} else if (e instanceof Negation) {
			Negation n = (Negation) e;
			return new Negation((Sentence) n.getChildren()[0]);
		} else if (e instanceof Predicate) {
			Predicate p = (Predicate) e;
			return new Predicate(p.getArity(), new Term[arity], p.getName());
		} else if (e instanceof Function) {
			Function f = (Function) e;
			return new Function(f.getArity(), new Term[arity], f.getName());
		}		
		return null;
	}
	
	private static Sentence skolemize(Sentence s) {
		Map<Variable, Term> quants = new HashMap<>();
		Stack<Variable> scope = new Stack<>();
		generateSkolemSymbols(s, quants, scope);
		
		return (Sentence) skolemize(s, quants);
	}
	
	private static Expression skolemize(Expression e, Map<Variable, Term> map) {
		if (e instanceof Variable) {
			Variable v = (Variable) e;
			if (map.containsKey(v)) {
				e = map.get(v);
			}
		} else if (e instanceof Quantifier) {
			Quantifier q = (Quantifier) e;
			if(q.getType() == Quantifier.Type.EXISTENTIAL) {
				e = skolemize(q.getChildren()[0], map);
			} else {
				q.getChildren()[0] = skolemize(q.getChildren()[0], map);
			}
		} else {
			for (int i = 0; i < e.getArity(); i++) {
				Expression child = e.getChildren()[i];
				e.getChildren()[i] = skolemize(child, map);
			}
		}
		
		return e;
	}

	private static Sentence dropUniversals(Sentence s) {
		if (s instanceof Quantifier) {
			Quantifier q = (Quantifier) s;
			return dropUniversals((Sentence) q.getChildren()[0]);
		}
		
		for (int i = 0; i < s.getArity(); i++) {
			Expression child = s.getChildren()[i];
			if (child instanceof Sentence) {
				Sentence childS = (Sentence) child;
				s.getChildren()[i] = dropUniversals(childS);
			}
		}
		return s;
	}

	private static Sentence distribute(Sentence s) {
		if (s instanceof Connector) {
			Connector c = (Connector) s;
			if (c.getType() == Connector.Type.CONJUNCTION) {
				for (int i = 0; i < c.getArity(); i++) {
					c.getChildren()[i] = distribute((Sentence) c.getChildren()[i]);
				}
			} else  {
				Connector disjunction = findDisjunctWithConjuction(c);
				if (disjunction != null) {
					if (disjunction == c) {
						s = (Sentence) s.getChildren()[0];
					}

					Connector conjuction = (Connector) disjunction.getChildren()[1];
					Sentence psi = (Sentence) conjuction.getChildren()[0];
					Sentence eta = (Sentence) conjuction.getChildren()[1];
					Sentence PHI1 = copySubtree(s);
					Sentence PHI2 = copySubtree(s);
					Connector D1 = (Connector) distribute(new Connector(Connector.Type.DISJUNCTION, psi, PHI1));
					Connector D2 = (Connector) distribute(new Connector(Connector.Type.DISJUNCTION, eta, PHI2));
					Connector C = new Connector(Connector.Type.CONJUNCTION, D1, D2);
					
					return C;
				}
			}
		}
		return s;
	}
	
	private static Sentence copySubtree(Sentence s) {
		if (s instanceof Connector) {
			Connector c = (Connector) s;
			Connector copy = new Connector(c.getType(), c.getArity(), new Sentence[c.getArity()]);
			for (int i = 0; i < c.getArity(); i++) {
				copy.getChildren()[i] = copySubtree((Sentence) c.getChildren()[i]);
			}
			return copy;
		}
		return s;
	}
	
	private static Connector findDisjunctWithConjuction(Connector c) {
		Sentence LHS = (Sentence) c.getChildren()[0];
		Sentence RHS = (Sentence) c.getChildren()[1];
		
		if (isConjunction(LHS)) {
			c.getChildren()[1] = LHS;
			c.getChildren()[0] = RHS;
		}

		LHS = (Sentence) c.getChildren()[0];
		RHS = (Sentence) c.getChildren()[1];
		
		if (isConjunction(RHS)) {
			return c;
		} else {
			for (int i = 0; i < c.getArity(); i++) {
				Expression child = c.getChildren()[i];
				Connector result = child instanceof Connector ? findDisjunctWithConjuction((Connector) child) : null;
				if (result != null) {
					if (result == c.getChildren()[i]) {
						c.getChildren()[i] = result.getChildren()[0];
					}
					return result;
				}
			}
		}

		return null;
	}
	
	private static boolean isConjunction(Sentence s) {
		return s instanceof Connector && ((Connector) s).getType() == Connector.Type.CONJUNCTION;
	}

	private static Sentence flatten(Sentence s) {
		if (s instanceof Connector) {
			Connector c = (Connector) s;
			for (int i = 0; i < c.getArity(); i++) {
				c.getChildren()[i] = flatten((Sentence) c.getChildren()[i]);
			}
			
			List<Expression> neuChildren = new LinkedList<>();
			for (int i = 0; i < c.getArity(); i++) {
				Expression e = c.getChildren()[i];
				if (e instanceof Connector) {
					Connector child = (Connector) e;
					if (child.getType() == c.getType()) {
						neuChildren.addAll(Arrays.asList(child.getChildren()));
						continue;
					}
				}
				neuChildren.add(e);
			}
			
			s = new Connector(c.getType(), neuChildren.size(), neuChildren.toArray(new Sentence[neuChildren.size()]));
		}
		return s;
	}
	
	private static void getAllVars(Expression e, Set<Variable> result) {
		if (e instanceof Variable) {
			result.add((Variable) e);
		} else if(e instanceof Quantifier) {
			result.add(((Quantifier) e).getVariable());
		}
		
		for (int i = 0; i < e.getArity(); i++) {
			getAllVars(e.getChildren()[i], result);
		}
	}

	private static void generateSkolemSymbols(Sentence s, Map<Variable, Term> result, Stack<Variable> scope) {
		boolean pop = false;
		if (s instanceof Quantifier) {
			Quantifier q = (Quantifier) s;
			if (q.getType() == Quantifier.Type.EXISTENTIAL) {
				Term skolem;
				if (scope.empty()) {
					skolem = new Constant("c" + result.size());
				} else {
					skolem = new Function(scope.size(), scope.toArray(new Term[scope.size()]), "f" + result.size());
				}
				result.put(q.getVariable(), skolem);
			} else {
				scope.push(q.getVariable());
				pop = true;
			}
		}
		
		for (int i = 0; i < s.getArity(); i++) {
			Expression child = s.getChildren()[i];
			if (child instanceof Sentence) {
				generateSkolemSymbols((Sentence) child, result, scope);
			}
		}
		
		if (pop) {
			scope.pop();
		}
	}
}
