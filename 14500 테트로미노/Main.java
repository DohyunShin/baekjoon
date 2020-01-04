import java.util.Scanner;

public class Main {

	static int N, M;
	static int map[][];
	static int dx[] = {0, 0, -1, 1};
	static int dy[] = {-1, 1, 0, 0};
	static int max = 0;
	static boolean visit[][];
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		map = new int[N][M];
		visit = new boolean[N][M];
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				map[i][j] = sc.nextInt();
			}
		}
		
		sc.close();
		
		play2();
		System.out.println(max);
		
	}
	
	private static void play2() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				visit [i][j] = true; //dfs들어갈때 체크
				dfs(j, i, 0, 0, visit);
				visit [i][j] = false; //dfs나올때 체크해제
				exceptionType(j, i);
			}
		}
	}
	
	//ㅜ 모양을 빼고는 깊이 4의 dfs의 네방향 검사로  모두 탐색할 수 있다.
	private static void dfs(int x, int y, int sum, int depth, boolean visit[][]) {		
		if(depth == 4) {
			if(max < sum) {
				max = sum;
			}
			return;
		}
		
		
		sum += map[y][x];
		depth++;
		
		for(int i = 0; i < 4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			
			if(!(nx >= M || ny >= N || ny < 0 || nx < 0) && visit[ny][nx] == false) {
				visit [ny][nx] = true; //dfs들어갈때 체크
				dfs(nx, ny, sum, depth, visit);
				visit [ny][nx] = false; //dfs나올때 체크해제
			}
		}
	}
	
	//ㅜ 모양
	private static void exceptionType(int x, int y) {
		final int tecs_dx[][] = {
				{1, 2, 1},
				{1, 1, 1},
				{1, 1, 2},
				{0, 1, 0}
		};
		
		final int tecs_dy[][] = {
				{0, 0, 1},
				{0, -1, 1},
				{0, -1, 0},
				{1, 1, 2}
		};
		
		for(int i = 0; i < 4; i++) {	
			boolean result = true;
			int sum = map[y][x];
			
			for(int j = 0; j < 3; j++) {
				int ny = y + tecs_dy[i][j];
				int nx = x + tecs_dx[i][j];
				
				if(!(ny >= N || nx >= M || nx < 0 || ny <0)) {
					sum += map[ny][nx];
				}
				else {
					result = false;
					break;
				}
			}
			
			if(result == true && max < sum) {
				max = sum;
			}
		}
	}
	
	private static void play() {
		final int tecsCnt = 19;
		final int tecs_dx[][] = {
				//ㅣ
				{1, 2, 3},
				 //ㅡ
				{0, 0, 0},
				//ㅁ
				{1, 0, 1},
				//L
				{0, 0, 1},
				{0, 1, 2},
				{1, 1, 1},
				{1, 2, 2},
				//L R
				{1, 1, 1},
				{0, 1, 2},
				{1, 0, 0},
				{1, 2, 2},
				//Z
				{0, 1, 1},
				{1, 1, 2},
				//Z R
				{1, 1, 0},
				{1, 1, 2},
				//T
				{1, 2, 1},
				{1, 1, 1},
				{1, 1, 2},
				{0, 1, 0}
		};
		
		final int tecs_dy[][] = {
				//ㅣ
				{0, 0, 0},
				//ㅡ
				{1, 2, 3},
				//ㅁ
				{0, 1, 1},
				//L
				{1, 2, 2},
				{1, 0, 0},
				{0, 1, 2},
				{0, 0, -1},
				//L R
				{0, -1, -2},
				{1, 1, 1},
				{0, 1, 2},
				{0, 0, 1},
				//Z
				{1, 1, 2},
				{0, -1, -1},
				//Z R
				{0, -1, 1},
				{0, 1, 1},
				//T
				{0, 0, 1},
				{0, -1, 1},
				{0, -1, 0},
				{1, 1, 2}
		};
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				for(int z = 0; z < tecsCnt; z++) {
					boolean result = true;
					int sum = map[i][j];
					
					for(int h = 0; h < 3; h++) {
						int ny = i + tecs_dy[z][h];
						int nx = j + tecs_dx[z][h];
						
						if(!(ny >= N || nx >= M || nx < 0 || ny <0)) {
							sum += map[ny][nx];
						}
						else {
							result = false;
							break;
						}
					}
					
					if(result == true && max < sum) {
						max = sum;
					}
				}
			}
		}
	}

}
