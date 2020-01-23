import java.util.Scanner;

public class Main {

	static int N, L, R;
	static int map[][];
	static int unionMap[][]; //연합 정보를 표시
	static int unionCnt = 0; //연합 개수
	static int cnt = 0;
	static int dx[] = {0, 0, -1, 1};
	static int dy[] = {-1, 1, 0, 0};
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		L = sc.nextInt();
		R = sc.nextInt();
		
		map = new int[N][N];
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				map[i][j] = sc.nextInt();
			}
		}
		
		
		sc.close();
		
		//printMap(map);
		solution();
		System.out.println(cnt);
	}
	
	private static void solution() {
		boolean move = true;
		
		while(move) {
			makeUnion();
			
			//연합 개수가 1개 이상인 경우 인구를 이동 시킨다.
			if(unionCnt > 0) {
				//모든 연합의 인구를 이동 시킨다. 이동 후 연합개수는 0으로 초기화 된다.
				while(unionCnt > 0) {
					movePeople(unionCnt--);
				}
				
				//printMap(map);
				cnt++; //이동 횟수 증가시킨다.
			}
			else {
				move = false;
			}
		}
	}
	
	
	//인구 이동
	private static void movePeople(int unionNum) {
		//연합 국가의 인덱스를 저장
		int unionX[] = new int[N*N];
		int unionY[] = new int[N*N];
		int curUnionCnt = 0;
		
		int sum = 0;
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(unionMap[i][j] == unionNum) {
					sum += map[i][j];
					unionX[curUnionCnt] = j;
					unionY[curUnionCnt] = i;
					curUnionCnt++;
				}
			}
		}
		
		int aver = sum/curUnionCnt; //평균 인구
		
		for(int i = 0; i < curUnionCnt; i++) {
			map[unionY[i]][unionX[i]] = aver;
		}
	}
	
	//연합 만들기
	private static void makeUnion() {
		unionMap = new int[N][N]; //연합 맵 초기화
		
		int unionNum = 1;
		for(int r = 0; r < N; r ++) {
			for(int c = 0; c < N; c++) {
				//현재 국가가 이미 연합에 속해있으면 다음으로 건너뛴다.
				if(unionMap[r][c] != 0) {
					continue;
				}
				
				//연합이 하나라도 생성 되었으면 연합 번호를 증가 시키고 연합 개수로 반영한다.
				if(makeUnionDfs(r, c, unionNum)) {
					//printMap(unionMap);
					unionCnt = unionNum;
					unionNum++;
				}
			}
		}
	}
	
	//조건에 부합하는 경우 계속 연합을 만들어 나간다.
	private static boolean makeUnionDfs(int r, int c, int unionNum) {
		//맵을 벗어난 경우 종료
		if(!(r >= 0 && r < N && c >= 0 && c < N)) {
			return false;
		}
		
		boolean result = false;
		
		for(int i = 0; i < 4; i++) {
			int nr = r + dy[i];
			int nc = c + dx[i];
			
			if(!(nr >= 0 && nr < N && nc >= 0 && nc < N)) {
				continue;
			}
			
			//연합에 가입되어있지 않는 국가 중 연합 가능성을 확인
			//연합이 가능한 경우 연합 표시하고 dfs를 진행한다.
			if(unionMap[nr][nc] == 0 && check(r, c, nr, nc)) {				
				//연합 표시
				//현재 국가가 연합이 아닌 경우 연합 표시 (dfs 처음 시작 시 동작)
				if(unionMap[r][c] == 0) {
					unionMap[r][c] = unionNum;
				}
				unionMap[nr][nc] = unionNum;
				
				//연합을 이어서 만든다. (dfs 진행)
				makeUnionDfs(nr, nc, unionNum);
				
				result = true; //주변국가 중 하나라도 연합에 가입되면(연합 개수 증가를 위해)
			}
		}
		
		return result;
	}
	
	//현재 위치와 다음 위치의 인구차를 보고 연합이 가능한지 여부를 구한다.
	private static boolean check(int cr, int cc, int nr, int nc) {
		int cP = map[cr][cc];
		int nP = map[nr][nc];
		int pG = Math.abs(cP - nP);
		
		if(pG >= L && pG <= R) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private static void printMap(int printMap[][]) {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				System.out.print(printMap[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

}
