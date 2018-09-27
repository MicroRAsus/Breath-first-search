import java.util.ArrayList;

public class Puzzle {
	private final byte[][] puzzlePiecesInitialLocation;
	private final int possibleDirection;
	private final int stateEncodingByteSize;
	private final boolean[][] board;
	private final int totalPuzzlePieces;
	private final int possibleStateCount;
	private final int boardLength;
	private final GameState root;
	private byte[][] possibleNextStates;
	private byte[][] allPuzzlePieceLocation;
	private boolean[][] currentBoard;
	private ArrayList<GameState> validNextStates;
	
	Puzzle() {
		this.possibleDirection = 2;
		this.stateEncodingByteSize = 22;
		this.totalPuzzlePieces = 11;
		this.boardLength = 10;
		this.puzzlePiecesInitialLocation = new byte[][] {
			{1, 3, 2, 3, 1, 4, 2, 4},
			{1, 5, 1, 6, 2, 6},
			{2, 5, 3, 5, 3, 6},
			{3, 7, 3, 8, 4, 8},
			{4, 7, 5, 7, 5, 8},
			{6, 7, 7, 7, 6, 8},
			{5, 4, 5, 5, 5, 6, 4, 5},
			{6, 4, 6, 5, 6, 6, 7, 5},
			{8, 5, 8, 6, 7, 6},			
			{6, 2, 6, 3, 5, 3},
			{5, 1, 6, 1, 5, 2}
		};
		this.board = new boolean[][] {  //x and y axis is swapped
			{true, true, true, true, true, true, true, true, true, true},
			{true, true, true, false, false, false, false, true, true, true},
			{true, true, false, false, false, false, false, false, true, true},
			{true, false, false, false, true, false, false, false, false, true},
			{true, false, false, true, true, false, false, false, false, true},
			{true, false, false, false, false, false, false, false, false, true},
			{true, false, false, false, false, false, false, false, false, true},
			{true, true, false, false, false, false, false, false, true, true},
			{true, true, true, false, false, false, false, true, true, true},
			{true, true, true, true, true, true, true, true, true, true}
		};
		this.root = new GameState(null);
		this.root.setState(new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
		this.possibleStateCount = this.possibleDirection * this.stateEncodingByteSize;
		this.possibleNextStates = new byte[this.possibleStateCount][this.stateEncodingByteSize];
		this.allPuzzlePieceLocation = new byte[][] {
			{1, 3, 2, 3, 1, 4, 2, 4},
			{1, 5, 1, 6, 2, 6},
			{2, 5, 3, 5, 3, 6},
			{3, 7, 3, 8, 4, 8},
			{4, 7, 5, 7, 5, 8},
			{6, 7, 7, 7, 6, 8},
			{5, 4, 5, 5, 5, 6, 4, 5},
			{6, 4, 6, 5, 6, 6, 7, 5},
			{8, 5, 8, 6, 7, 6},			
			{6, 2, 6, 3, 5, 3},
			{5, 1, 6, 1, 5, 2}
		};
		this.currentBoard = new boolean[this.boardLength][this.boardLength];
		this.validNextStates = new ArrayList<GameState>();
	}
	
	public boolean isGoal(GameState node) {
		if((int)(node.getStateByIndex(1)) == -2) {
			return true;
		}
		return false;
	}
	
	public GameState getRoot() {
		return this.root;
	}
	
	public ArrayList<GameState> get_childrens(GameState subRoot) {
		byte[] parentState = subRoot.getState();
		this.validNextStates.clear();
		
		for(int i = 0; i < this.possibleStateCount; i++) { //deep copy and initialize all possible next state
			for(int j = 0; j < this.stateEncodingByteSize; j++) {
				possibleNextStates[i][j] = parentState[j];
			}
		}

		for(int i = 0; i < this.stateEncodingByteSize; i++) { //generate possible states
			this.possibleNextStates[i*2][i] += 1;
			this.possibleNextStates[i*2+1][i] -= 1;
		}
		
		for(int k = 0; k < this.possibleStateCount; k++) { //valid possible states
			//creating drawing data
			for(int i = 0; i < this.totalPuzzlePieces; i++) { //loop thru each piece
				for(int j = 0; j < this.allPuzzlePieceLocation[i].length; j++) { // block of pieces, eg each block that comprise this piece
					this.allPuzzlePieceLocation[i][j] = this.puzzlePiecesInitialLocation[i][j];
					
					if(j % 2 == 0) { //x axis
						this.allPuzzlePieceLocation[i][j] = (byte) (this.puzzlePiecesInitialLocation[i][j] + this.possibleNextStates[k][i*2]);
					} else { //y axis
						this.allPuzzlePieceLocation[i][j] = (byte) (this.puzzlePiecesInitialLocation[i][j] + this.possibleNextStates[k][i*2+1]);
					}
				}
			}
			
			boolean result = true; //checking the drawing data by drawing on the board
			for(int e = 0; e < this.boardLength; e++) { //reset the board
				for(int d = 0; d < this.boardLength; d++) {
					this.currentBoard[e][d] = this.board[e][d];
				}
			}
			for(int j = 0; j < this.totalPuzzlePieces && result; j++) { //iterate thru each piece's drawing data
				for(int a = 0; a < this.allPuzzlePieceLocation[j].length && result; a += 2) { //iterate thru each block
					if(currentBoard[this.allPuzzlePieceLocation[j][a]][this.allPuzzlePieceLocation[j][a+1]] != true) {
						currentBoard[this.allPuzzlePieceLocation[j][a]][this.allPuzzlePieceLocation[j][a+1]] = true;
					} else {
						result = false;
					}
				}
			}
			if (result) {
				GameState validState = new GameState(subRoot);
				validState.setState(this.possibleNextStates[k]);
				this.validNextStates.add(validState);
			}
		}
		
		return this.validNextStates;
	}
}
