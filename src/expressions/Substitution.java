package expressions;

import java.util.HashMap;
import java.util.Map.Entry;

import terms.Variable;

public class Substitution {
	public static final Substitution FAILURE = new Substitution();
	
	private HashMap<Variable, Term> mMap = new HashMap<>();
	
	public Term get(Variable v) {
		return mMap.get(v);
	}
	
	public Substitution set(Variable v, Term t) {
		mMap.put(v, t);
		resolveChains();
		return this;
	}
	
	private void resolveChains() {
		for (Entry<Variable, Term> entry : mMap.entrySet()) {
			// expand graph
		}
	}
}
