package roll_the_ball;

// This is an ADT, in our case it will be initialized to a 2D array to represent our grid
public class State <E> {
	private E currentState;
	public State(E currentState){
		this.setCurrentState(currentState);
	}
	public E getCurrentState() {
		return currentState;
	}
	public void setCurrentState(E currentState) {
		this.currentState = currentState;
	}

}
