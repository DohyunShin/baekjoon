import java.util.Scanner;

public class Main {
	static int cases[] = new int[10];
	static int max = 0;
	static final int FINAL = 100;
	static final int FINAL_POS = 21;
	static final int NODE_CNT = 33;
	static final int BLUE = 1;
	static final int NEXT = 0;
	static int map[][] = new int[NODE_CNT][2];
	static int scoreMap[] = new int[NODE_CNT];
	static boolean blueStart[] = new boolean[NODE_CNT];
	static int unitPos[] = new int[4];
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		for(int i = 0; i < 10; i++) {
			cases[i] = sc.nextInt();
		}
		
		//경로 세팅(화살표 방향으로)
		for(int i = 0; i < NODE_CNT; i++) {
			map[i][NEXT] = i + 1;
		}
		map[28][NEXT] = 27;
		map[27][NEXT] = 26;
		map[26][NEXT] = 25;
		map[30][NEXT] = 25;
		map[25][NEXT] = 31;
		map[32][NEXT] = 20;
		//파란색 경로로 나눠지는 구간 세팅
		map[5][BLUE] = 22;
		map[10][BLUE] = 29;
		map[15][BLUE] = 28;
		
		//도착 지점
		map[FINAL_POS][0] = FINAL_POS;
				
		//파란색 지점 표시
		blueStart[5] = true;
		blueStart[10] = true;
		blueStart[15] = true;
		
		//점수 세팅
		for(int i = 1; i <= 20; i++) {
			scoreMap[i] = i * 2;
		}
		scoreMap[22] = 13;
		scoreMap[23] = 16;
		scoreMap[24] = 19;
		scoreMap[25] = 25;
		scoreMap[26] = 26;
		scoreMap[27] = 27;
		scoreMap[28] = 28;
		scoreMap[29] = 22;
		scoreMap[30] = 24;
		scoreMap[31] = 30;
		scoreMap[32] = 35;
		
		sc.close();
		
		solution();
		System.out.println(max);
	}
	
	private static void solution() {
		dfs(0, 0);
	}
	
	private static void dfs(int cnt, int score) {
		if(cnt == 10) {
			if(max < score) {
				max = score;
			}
			return;
		}
		
		
		
		for(int i = 0; i < 4; i++) {
			int moveCnt = cases[cnt]; //이동해야할 횟수
			final int curPos = unitPos[i];
			int targetPos = curPos; //이동할 위치
			
			//시작 위치가 파란색인지 확인
			if(blueStart[curPos] == true) {
				//시작 위치가 파란색인 경우 파란색 경로의 시작 지점으로 이동할 위치를 옮긴다.
				targetPos = map[curPos][BLUE];
				moveCnt--;
			}
			
			//최종적으로 이동할 위치를 찾는다.
			while(moveCnt-- > 0) {
				targetPos = map[targetPos][NEXT];
			}
			
			//이동하려는 위치에 말이 있는 경우 해당 말을 이동하지 않는다.
			boolean already = false;
			for(int j = 0; j < 4; j++) {
				if(unitPos[j] == targetPos && targetPos != FINAL_POS) {
					already = true;
					break;
				}
			}
			if(already) {
				continue;
			}
			
			//해당 말을 이동하려는 위치로 변경한다.
			unitPos[i] = targetPos;
			
			//이동한 위치의 점수를 더해주고 다음을 케이스를 진행한다.
			dfs(cnt + 1, score + scoreMap[targetPos]);
			
			//해당 말을 원래 위치로 돌려놓는다.
			unitPos[i] = curPos;
		}
	}
}
