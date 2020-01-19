import java.util.Scanner;

public class Main {
	static int N, M, H; //(주의) N: 세로개수, H: 놓을수 있는 가로 개수, M: 가로개수
	static boolean ladder[][];
	static int min = 4;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		H = sc.nextInt();
		
		
		ladder = new boolean[H][N];
		
		for(int i = 0; i < M; i++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			
			ladder[a - 1][b - 1] = true;
		}
		
		sc.close();
		
		solution();
		System.out.println(min == 4 ? "-1" : min);
	}
	
	private static void solution() {		
		dfs(0, 0, 0);	
	}
	
	private static void dfs(int row, int col, int addCnt) {
		if(addCnt > 3) {
			return;
		}
		
		if(check(ladder)) {
			if(addCnt < min) {
				min = addCnt;
			}
			return;
		}

		//System.out.println(String.format("row: %d, col: %d, addCnt: %d", row, col, addCnt));
		//printLadder(ladder);
		
		//현재 위치에서 오른쪽으로 가로 사다리를 연결할 수 있는지 검사
		for(int c = col; c < N - 1; c++) {
			for(int r = row; r < H; r++) {
				if(c + 1 < N && ladder[r][c] == false && ladder[r][c + 1] == false) {
					ladder[r][c] = true;
					dfs(r, c, addCnt + 1);
					ladder[r][c] = false;
				}	
			}
			row = 0; //한 열을 다 보고 나면 다음 열 첫 행으로 이동
		}
	}
	
	private static boolean check(boolean ld[][]) {		
		for(int c = 0 ; c < N; c++) {
			int r = 0;
			int nextC = c;
			
			//탐색시에는 마지막 열 봐야하기 때문에
			while(r < H && nextC < N) {
				//오른쪽으로 이동할 수 있는 경우
				if(ld[r][nextC] == true) {
					r++;
					nextC++;
				}
				//왼쪽으로 이동할 수 있는 경우
				else if(nextC - 1 >= 0 && ld[r][nextC - 1] == true) {
					r++;
					nextC--;
				}
				else {
					r++;
				}
			}
			
			//한개라도 안되면 실패
			if(nextC != c) {
				return false;
			}
		}
		
		return true;
	}
	
	private static void printLadder(boolean ld[][]) {
		for(int i = 0; i < H; i++) {
			for(int j = 0; j < N; j++) {
				System.out.print(ld[i][j]? "1 " :"0 ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
