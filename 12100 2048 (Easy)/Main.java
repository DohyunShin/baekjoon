import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N; // 1~20
	static int[][] map = new int[20][20];
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	static int res = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		for(int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for(int c = 0; c < N; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		br.close();
		
		solution();
		System.out.println(res);
	}
	
	public static void solution() {
		dfs(0);
	}
	
	public static void dfs(int cnt) {
		if(cnt == 5) {
			int max = 0;
			for(int r = 0; r < N; r++) {
				for(int c = 0; c < N; c++) {
					if(map[r][c] > max) max = map[r][c];
				}
			}
			if(res < max) res = max;
			return;
		}
		
		int[][] copy = map.clone();
		for(int r = 0; r < N; r++) {
			copy[r] = map[r].clone();
		}
		
		for(int d = 0; d < 4; d++) {
			move(d);
			
			dfs(cnt+1);
			
			map = copy.clone();
			for(int r = 0; r < N; r++) {
				map[r] = copy[r].clone();
			}
		}
	}
	
	public static void moveEx(int d, int r, int c, boolean[][] sum) {
		int cr = r;
		int cc = c;
		int nr, nc;
		
		boolean isSum = false;
		
		while(true) {
			nr = cr + dr[d];
			nc = cc + dc[d];
			
			if(!(nr >= 0 && nr < N && nc >= 0 && nc < N)) break;
			if(map[nr][nc] != 0) {
				if(map[nr][nc] == map[r][c] && sum[nr][nc] == false) {
					isSum = true;
				}
				break;
			}
			
			cr = nr;
			cc = nc;
		}
		
		if(isSum) {
			// 앞으로 합치기
			int temp = map[r][c];
			map[nr][nc] += temp;
			map[r][c] = 0;
			
			sum[nr][nc] = true;
		}
		else {
			// 이동
			int temp = map[r][c];
			map[r][c] = 0;
			map[cr][cc] = temp;
		}
		
	}
	
	public static void move(int d) {
		boolean[][] sum = new boolean[N][N]; // 합쳐진 위치 저장
		
		if(d == 0) {
			for(int r = 0; r < N; r++) {
				for(int c = 0; c < N; c++) {
					moveEx(d, r, c, sum);
				}
			}
		}
		else if(d == 1) {
			for(int r = N-1; r >= 0; r--) {
				for(int c = 0; c < N; c++) {
					moveEx(d, r, c, sum);
				}
			}
		}
		else if(d == 2) {
			for(int r = 0; r < N; r++) {
				for(int c = 0; c < N; c++) {
					moveEx(d, r, c, sum);
				}
			}
		}
		else if(d == 3) {
			for(int r = 0; r < N; r++) {
				for(int c = N-1; c >= 0; c--) {
					moveEx(d, r, c, sum);
				}
			}
		}
	}
	
	public static void printMap() {
		for(int r = 0; r < N; r++) {
			for(int c = 0; c < N; c++) {
				System.out.print(map[r][c] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
