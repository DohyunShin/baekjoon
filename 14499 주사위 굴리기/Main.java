import java.util.Scanner;

public class Main {

	static int N; //맵 세로
	static int M; //맵 가로
	static int x, y; //주사위 좌표
	//주의 : x y(0 ≤ x ≤ N-1, 0 ≤ y ≤ M-1) 문제에서 제시됨
	//x가 가로(열)가 아니다.
	static int K; //명령 개수
	static int dice[]; //주사위 , index: 주사위의 넘버링, value: 주사우의 값
	static int diceState[]; //주사위 상태, 면의 위치에 따른 주사위 넘버링 상태, index: 면의 위치, value: 주사위 넘버링
	//면의 위치
	//  2
	//4 1 3 
	//  5
	//  6
	//1: 위, 6: 아래, 4: 왼, 3: 오른쪽, 2: 뒤, 5: 앞
	//현재 윗면을 가리키는 주사위의 넘버를 찾는 경우 활용
	final static int BOTTOM_INDEX = 0;
	final static int TOP_INDEX = 5;
	static int map[][];
	static int cmd[]; //1: 동, 2: 서, 3: 북, 4: 남 
	static int dy[] = {1, -1, 0, 0};
	static int dx[] = {0, 0, -1, 1};
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		x = sc.nextInt();
		y = sc.nextInt();
		K = sc.nextInt();
		
		map = new int[N][M];
		dice = new int[6];
		diceState = new int[6];
		for(int i = 0; i < 6; i++) {
			diceState[i] = i;
		}
		cmd = new int[K];
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				map[i][j] = sc.nextInt();
			}
		}
		
		for(int i = 0; i < K; i++) {
			cmd[i] = sc.nextInt();
		}
		
		sc.close();
		
		play();
	}

	
	private static void play() {
		for(int i = 0; i < K; i++) {
			if(changeDicePoint(cmd[i])) {
				changeDiceState(cmd[i]);
				
				//주사위의 값을 맵으로 옮기거나, 맵의 값을 주사위로 옮기거나
				if(map[x][y] != 0) {
					dice[diceState[BOTTOM_INDEX]] = map[x][y];
					map[x][y] = 0;
				}
				else {
					map[x][y] = dice[diceState[BOTTOM_INDEX]];
				}
				
				//현재 주사위 위의 값을 출력
				System.out.println(dice[diceState[TOP_INDEX]]);
			}
		}
	}
	
	//원본
	//  2
	//4 1 3
	//  5
	//  6
	
	// 동
	//  2
	//6 4 1
	//  5
	//  3
	
	
	//서 
	//  2
	//1 3 6
	//  5
	//  4
	
	//북
	//  1
	//4 5 3
	//  6 
	//  2
	
	//남
	//  6
	//4 2 3
	//  1 
	//  5
	
	//주사위 이동 명령의 방향에 따라 주사위의 상태를 변경한다.
	private static void changeDiceState(int d) {
		int n1, n2, n3, n4, n5, n6;
		switch(d) {
		case 1: //동
			//1, 2, 3, 4, 5, 6 -> 4, 2, 1, 6, 5, 3
			//1 -> 4
			//3 -> 1
			//4 -> 6
			//6 -> 3
			n1 = diceState[0];
			n3 = diceState[2];
			n4 = diceState[3];
			n6 = diceState[5];
			diceState[3] = n1;
			diceState[0] = n3;
			diceState[5] = n4;
			diceState[2] = n6;
			
			break;
		case 2: //서
			// 1 -> 3
			// 3 -> 6
			// 4 -> 1
			// 6 -> 4
			n1 = diceState[0];
			n3 = diceState[2];
			n4 = diceState[3];
			n6 = diceState[5];
			diceState[2] = n1;
			diceState[5] = n3;
			diceState[0] = n4;
			diceState[3] = n6;
			
			break;
		case 3: //북
			// 1 -> 2
			// 2 -> 6
			// 5 -> 1
			// 6 -> 5
			n1 = diceState[0];
			n2 = diceState[1];
			n5 = diceState[4];
			n6 = diceState[5];
			diceState[1] = n1;
			diceState[5] = n2;
			diceState[0] = n5;
			diceState[4] = n6;
			break;
		case 4: //남
			// 1 -> 5
			// 2 -> 1
			// 5 -> 6
			// 6 -> 2
			n1 = diceState[0];
			n2 = diceState[1];
			n5 = diceState[4];
			n6 = diceState[5];
			diceState[4] = n1;
			diceState[0] = n2;
			diceState[5] = n5;
			diceState[1] = n6;
			break;
		}
	}
	
	private static boolean changeDicePoint(int d) {
		int nx = x + dx[d-1];
		int ny = y + dy[d-1];
		
		if(nx >= N || ny >= M || ny < 0 || nx < 0) {
			return false;
		}
		else {
			x = nx;
			y = ny;
			return true;
		}
	}
	
	private static void printDiceState() {
		for(int i = 0; i < 6; i++) {
			System.out.print(diceState[i] + " ");
		}
		System.out.println();
		for(int i = 0; i < 6; i++) {
			System.out.print(dice[i] + " ");
		}
		System.out.println();
	}
}
