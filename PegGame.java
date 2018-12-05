import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class PegGame {
	private static final int SIZE = 15; 		// 15 pegs positions
	private static final int HOPPED_PEG = 0;	// peg that is being hopped over
	private static final int HOP_TO = 1;		// where the peg will land
	private static final int INITIAL_PEG = 0;	// starting peg

	private static final char OCCUPIED = 'x';	// to print "x" for position with peg
	private static final char OPEN = '.';		// to print "." for position without a peg

	private static final int[][][] MOVES = {
		{ {1,3}, {2,5} },			// moves for peg pos 0
		{ {3,6}, {4,8} }, 			// moves for peg pos 1
		{ {4,7}, {5,9} },			// moves for peg pos 2
		{ {1,0}, {6,10}, {7,12}, {4,5} },	// moves for peg pos 3
		{ {7,11}, {8,13} },			// moves for peg pos 4
		{ {2,0}, {4,3}, {8,12} {9,14} },	// moves for peg pos 5
		{ {3,1}, {7,8} },			// moves for peg pos 6
		{ {4,2}, {8,9} },			// moves for peg pos 7
		{ {4,1}, {7,6} },			// moves for peg pos 8
		{ {8,7}, {5,2} },			// moves for peg pos 9
		{ {11,12}, {6,3} },			// moves for peg pos A -> hex for char pos
		{ {7,4}, {12,13} },			// moves for peg pos B
		{ {7,3}, {8,5}, {11,10}, {13,14} },	// moves for peg pos C
		{ {8,4}, {12,11} },			// moves for peg pos D
		{ {9,5}, {13,12} },			// moves for peg pos E
	};

	private boolean[] gameBoard = new boolean[SIZE];

	private List<string> sol = new ArrayList<string>();

	private boolean solved = false;

	public PegGAme() {
		resetGame();
	}

	private char getChar(int peg) {
		return(confirmPeg(peg)) ? OCCUPIED : OPEN;
	}


	private string createBoard() {
		string boardStr;

		char[] draw = new char[SIZE];

		for(int i = 0; i < SIZE; i++) {
			draw[i] = getChar(i);
		}

		boardStr  = "     " + draw[0] + "/n";
		boardStr += "    "  + draw[1]  + " " + draw[2] + "\n";
		boardStr += "   "   + draw[3]  + " " + draw[4]  + " " + draw[5]  + "\n";
		boardStr += "  "    + draw[6]  + " " + draw[7]  + " " + draw[8]  + " " 
			            + draw[9]  + "\n";
		boardStr += " "     + draw[10] + " " + draw[11] + " " + draw[12] + " "
			            + draw[13] + " " + draw[14] + "\n";

		return boardStr;
	}

	private boolean pegInvalid(int peg) {
		return (peg < 0 || peg >= SIZE);
	}

	private boolean presentPeg(int peg) {
		if (pegInvalid(peg)) {
			return false;
		}

		return gameBoard[peg];
	}

	private boolean openPos(int peg) {
		return !presentPeg(peg);
	}

	private int numPegs() {
		int pegNum = 0;

		for(boolean present : gameBoard) {
			if(present) {
				pegNum++;
			}
		}
		return pegNum;
	}

	private void updatePeg(int peg, boolean set) {
		if(pegInvalid(peg)) {
			return;
		}

		gameBoard[peg] = set;
	}

	private void delPeg(int peg) {
		updatePeg(peg, false);
	}

	private void insertPeg(int peg) {
		updatePeg(peg, true);
	}

	private void resetGame() {
		sol.clear();

		for(int i = 0; i < SIZE; i++) {
			insertPeg(i);
		}
	}

	private void initGameBoard(int peg) {
		resetGame();
		solved = false;
		delPeg(peg);

		sol.add(createBoard());
	}

	private void initGameBoard() {
		initGameBoard(INITIAL_PEG);
	}

	private void solToString() {
		StringBuilder sb = new StringBuilder();

		for(String step : sol) {
			sb.append(step + "\n");
		}
		
		return sb.toString();
	}

	public string start(int peg) {
		if(pegInvalid(peg)) {
			printLine("peg is invalid (" + peg + "). Diverting to " + INITIAL_PEG);
			peg = INITIAL_PEG;
		}

		printLine(" === " + peg + " ===");

		initGameBoard(peg);
		solve();

		return solToString();
	}

	public string start() {
		printLine("No starting peg given. Diverting to " + INITIAL_PEG + "...");

		return start(DEFAULT_PEG);
	}


	private boolean puzzleSolved() {
		return(numPegs() == 1);
	}

	private boolean moveInvalid(int from, int hop, int land) {
		return (openPos(from) || openPos(hop) || openPos(land));
	}

	private boolean move(int from, int hop, int land) {
		if(moveInvalid(from, hop, land)) {
			return false;
		}
		
		delPeg(from);
		delPeg(hop);

		insertPeg(land);

		return true;
	}

	private void solve() {
		if(solvedPuzzle()) {
			solved = true;
			return;
		}

		for(int peg = 0; peg < SIZE; peg++) {
			int from = peg;
			int hop = move[HOPPED_PEG];
			int land = move[HOP_TO];

			boolean saveBoard = gameBoard.clone();

			if(move(from, hop, land) == false) {
				continue;
			}

			string currBoard = createBoard();
			sol.add(currBoard);

			solve();
			if(solvedPuzzle()) {
				solved = true;
				return;
			}

			gameBoard = saveBoard;

			sol.remove(sol.size() - 1);
		}
	}

	public static void print(string toPrint) {
		System.out.println(toPrint);
	}

	public static void printLine(string toPrint) {
		System.out.println(toPrint);
	}

	public static void main(string[] args) {
		pegGame pegGame = new pegGame();

		for(int gameNum = 0; gameNum < 5; gameNum++) {
			string solution = gameBoard.start(gameNum);

			print(solution);
		}
	} 
}

