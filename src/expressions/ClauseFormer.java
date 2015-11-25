package expressions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import sentences.Connector;
import sentences.Negation;
import sentences.Quantifier;
import terms.Constant;
import terms.Function;
import terms.Variable;

public class ClauseFormer {
	public Sentence clauseForm(Sentence wellFormed) {
		wellFormed = removeBiImplication(wellFormed);
		wellFormed = removeImplication(wellFormed);
		wellFormed = pushNegation(wellFormed);
		wellFormed = standardize(wellFormed);
		wellFormed = skolemize(wellFormed);
		wellFormed = dropUniversals(wellFormed);
		wellFormed = distribute(wellFormed);
		wellFormed = flatten(wellFormed);
		return wellFormed;
	}
	
	private Sentence removeBiImplication(Sentence s) {
		if (s instanceof Connector) {
			Connector c = (Connector) s;
			if (c.getType() == Connector.Type.BIIMPLICATION) {
				Connector L2R = new Connector(Connector.Type.IMPLICATION, (Sentence) c.getChildren()[0], (Sentence) c.getChildren()[1]);
				Connector R2L = new Connector(Connector.Type.IMPLICATION, (Sentence) c.getChildren()[1], (Sentence) c.getChildren()[0]);
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

	private Sentence removeImplication(Sentence s) {
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

	private Sentence pushNegation(Sentence s) {
		if (s instanceof Negation) {
			Negation n = (Negation) s;
			Sentence c = (Sentence) s.getChildren()[0];
			
			if (c instanceof Quantifier) {
				Quantifier q = (Quantifier) c;
				Negation not = new Negation((Sentence) q.getChildren()[0]);
				Quantifier.Type neuType = q.getType() == Quantifier.Type.EXISTENTIAL ? Quantifier.Type.UNIVERSAL :Quantifier.Type.EXISTENTIAL;  
				s = new Quantifier(neuType, q.getVariable(), not);
			} else if(c instanceof Connector) {
				Connector con = (Connector) c;
				Connector.Type neuType = con.getType() == Connector.Type.CONJUNCTION ? Connector.Type.DISJUNCTION: Connector.Type.CONJUNCTION;
				s = new Connector(neuType, (Sentence) con.getChildren()[0], (Sentence) con.getChildren()[1]);
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

	private Sentence standardize(Sentence s) {
		Set<Variable> variables = new HashSet<>();
		getAllVars(s, variables);
		
		Map<Variable, Variable> map = new HashMap<>();
		for(Variable v : variables) {
			map.put(v, new Variable("x"+map.size()));
		}
		
		
		return (Sentence) standardize(s, map);
	}
	
	private Expression standardize(Expression e, Map<Variable, Variable> map) {
		if (e instanceof Quantifier) {
			Quantifier q = (Quantifier) e;
			e = new Quantifier(q.getType(), map.get(q.getVariable()), (Sentence) q.getChildren()[0]);
		} else if (e instanceof Variable) {
			e = map.get((Variable) e);
		}
		
		for (int i = 0; i < e.getArity(); i++) {
			Expression child = e.getChildren()[i];
			e.getChildren()[i] = standardize(child, map);
		}

		return e;
	}
	
	private Sentence skolemize(Sentence s) {
		Map<Variable, Term> quants = new HashMap<>();
		Stack<Variable> scope = new Stack<>();
		getAllEQ(s, quants, scope);
		return s;
	}

	private Sentence dropUniversals(Sentence s) {
		return s;
	}

	private Sentence distribute(Sentence s) {
		return s;
	}

	private Sentence flatten(Sentence s) {
		return s;
	}
	
	private void getAllVars(Expression e, Set<Variable> result) {
		if (e instanceof Variable) {
			result.add((Variable) e);
		}
		
		for (int i = 0; i < e.getArity(); i++) {
			getAllVars(e.getChildren()[i], result);
		}
	}

	private void getAllEQ(Sentence s, Map<Variable, Term> result, Stack<Variable> scope) {
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
				getAllEQ((Sentence) child, result, scope);
			}
		}
		
		if (pop) {
			scope.pop();
		}
	}
}
