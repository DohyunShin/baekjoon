import java.util.Scanner;

public class Main {
	static int N, M;
	static int map[][];
	static int dx[] = {0, 0, -1, 1};
	static int dy[] = {-1, 1, 0, 0};
	static int max = 0;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt() + 2;
		M = sc.nextInt() + 2;
		map = new int[N][M];
		
		for(int i = 1; i < N - 1; i++) {
			for(int j = 1; j < M - 1; j++) {
				map[i][j] = sc.nextInt();
			}
		}
		
		//맵 패딩처리
		for(int i = 0; i < N; i++) {
			if(i == 0 || i == N - 1) {
				for(int j = 0; j < M; j++) {
					map[i][j] = 1;
				}
			}
			map[i][0] = 1;
			map[i][M-1] = 1;
		}
		sc.close();
	
		solution();
		
		System.out.println(max);
	}
	
	private static void solution() {
		for(int i = 1; i < N - 1; i++) {
			for(int j = 1; j < M - 1; j++) {
				if(map[i][j] == 0) {
					int tempMap[][] = new int[N][M];
					for(int z = 0; z < N; z++) {
						tempMap[z] = map[z].clone();
					}
					build(tempMap, i, j, 0);
				}
			}
		}
	}
	
	private static void build(int tempMap[][], int r, int c, int cnt) {
		
		//기둥 만들기
		tempMap[r][c] = 1;
		cnt++;
		if(cnt == 3) {	
			int sum = explode(tempMap);
			if(sum > max) {
				max = sum;
			}
			return;
		}
		
		for(int i = 1; i < N - 1; i++) {
			for(int j = 1; j < M - 1; j++) {
				if(tempMap[i][j] == 0) {
					int tempMap2[][] = new int[N][M];
					for(int z = 0; z < N; z++) {
						tempMap2[z] = tempMap[z].clone();
					}
					build(tempMap2, i, j, cnt);
				}
			}
		}
	}
	
	private static void explodeEx(int tempMap[][], boolean visit[][], int r, int c) {
		for(int i = 0; i < 4; i++) {
			int nr = r + dy[i];
			int nc = c + dx[i];
			
			if(tempMap[nr][nc] != 1 && visit[nr][nc] == false) {
				visit[nr][nc] = true;
				tempMap[nr][nc] = 2;
				explodeEx(tempMap, visit, nr, nc);
			}
		}
	}
	
	//네방향 확산 및 계산
	private static int explode(int tempMap[][]) {
		boolean visit[][] = new boolean[N][M];
		
		for(int i = 1; i < N - 1; i++) {
			for(int j = 1; j < M - 1; j++) {
				if(tempMap[i][j] == 2) {
					visit[i][j] = true;
					
					explodeEx(tempMap, visit, i, j);
				}
			}
		}
		
		//printMap(tempMap);
		
		//계산
		int sum = 0;
		for(int i = 1; i < N - 1; i++) {
			for(int j = 1; j < M - 1; j++) {
				if(tempMap[i][j] == 0) {
					sum++;
				}
			}
		}
		
		return sum;
	}

	
	private static void printMap(int printMap[][]) {
		for(int i = 1; i < N - 1; i++) {
			for(int j = 1; j < M - 1; j++) {
				System.out.print(printMap[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

}
