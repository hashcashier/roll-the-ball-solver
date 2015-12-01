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
	
	public Substitution set(Variable v, Term t, boolean trace) {
		if (trace) {
			System.out.println("SET " + v.toString() + " TO " + t.toString() + " IN " + toString());
		}
		if (!(t instanceof Variable) || (mMap.get(t) == null || mMap.get(mMap.get(t)) != v)) {
			mMap.put(v, t);
		}
		
		boolean change = true;
		while(change) {
			change = false;
			for (Variable U : mMap.keySet()) {
				for (Entry<Variable, Term> entry : mMap.entrySet()) {
					Variable V = entry.getKey();
					Term T = entry.getValue();
					String mem = T.toString();
					if (U.occursIn(T)) {
						Term TT = (Term) Unifier.substitute(this, T, trace);
						mMap.put(V,  TT);
						if (!TT.toString().equals(mem)) {
							change = true;
						}
					}
				}
			}
		}
		return this;
	}
	
	public String toString() {
		String res = this == FAILURE ? "FAIL!" : "";
		for(Entry<Variable, Term> entry : mMap.entrySet()) {
			res += "{" + entry.getKey().toString() + " ==> " + entry.getValue().toString() + " } ";
		}
		return res;
	}
}
