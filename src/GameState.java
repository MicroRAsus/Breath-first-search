import java.util.Arrays;

public class GameState {
	private GameState prev;
	private byte[] state;

	GameState(GameState _prev)
	{
		this.prev = _prev;
		this.state = new byte[22];
	}
	
	public GameState getPrevState() {
		return this.prev;
	}
	
	public byte[] getState() {
		return this.state;
	}
	
	public byte getStateByIndex(int index) {
		return this.state[index];
	}
	
	public void setState(byte[] state) {
		this.state = Arrays.copyOf(state, state.length); //deep copy
	}
	
	public String stateToString()
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < this.state.length; i += 2) {
			sb.append(String.format("(%d,%d) ", this.state[i], this.state[i+1]));
		}
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof GameState) {
			byte[] stateA = ((GameState)(obj)).getState();
			for(int i = 0; i < stateA.length; i++) {
				if(stateA[i] != this.state[i]) {
					return false;
				}
			}
			return true;
		}
		throw new RuntimeException("Incorrect object type to compare against.");
	}
}
