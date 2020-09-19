import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
	static int N, M; // 1~8
	static int[][] map = new int[8][8];
	static ArrayList<Cctv> cctvs = new ArrayList<Main.Cctv>();
	static int res = Integer.MAX_VALUE;
	static final int[] dirCnt = {0,4,2,4,4,1};
	static final int[][] dir1 = {
			{0},
			{1},
			{2},
			{3}
	};
	static final int[][] dir2 = {
			{0,1},
			{2,3},
	};
	static final int[][] dir3 = {
			{0,3},
			{3,1},
			{1,2},
			{2,0},
	};
	static final int[][] dir4 = {
			{2,0,3},
			{0,3,1},
			{2,1,3},
			{0,1,2},
	};
	static final int[][] dir5 = {
			{0,1,2,3}
	};
	
	static final int[] dr = {-1,1,0,0};
	static final int[] dc = {0,0,-1,1};
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		for(int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for(int c = 0; c < M; c++) {
				int v = Integer.parseInt(st.nextToken());
				if(v >= 1 && v <= 5) {
					cctvs.add(new Cctv(new Point(r, c), v, 0));
				}
				else {
					map[r][c] = v;
				}
			}
		}
		br.close();
		
		solution();
		System.out.println(res);
	}
	
	private static void solution() {
		recursive(0);
	}
	
	private static void recursive(int cctvIdx) {
		if(cctvIdx == cctvs.size()) {
			// 사각지대 계산
			check();
			return;
		}
		
		Cctv cctv = cctvs.get(cctvIdx);
		
		for(int d = 0; d < dirCnt[cctv.type]; d++) {
			cctv.d = d;
			recursive(cctvIdx+1);
		}
	}
	
	private static void check() {
		int[][] temp = map.clone();
		for(int i = 0; i < N; i++)
			temp[i] = map[i].clone();
		
		for(Cctv cctv : cctvs) {
			int[][] dir = null;
			switch(cctv.type) {
			case 1: dir = dir1; break;
			case 2: dir = dir2; break;
			case 3: dir = dir3; break;
			case 4: dir = dir4; break;
			case 5: dir = dir5; break;
			}
			
			for(int i = 0; i < dir[cctv.d].length; i++) {
				int d = dir[cctv.d][i];
				
				temp[cctv.pt.r][cctv.pt.c] = 7;
				
				int nr = cctv.pt.r;
				int nc = cctv.pt.c;
				
				while(true) {
					nr += dr[d];
					nc += dc[d];
					
					if(!(nr >= 0 && nr < N && nc >= 0 && nc < M) || temp[nr][nc] == 6) break;
					temp[nr][nc] = 7;
				}
			}
		}
		
		calculate(temp);
		//printMap(temp);
	}
	private static void calculate(int[][] map) {
		int cnt = 0;
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(map[i][j] == 0) cnt++;
			}
		}
		if(res > cnt) res = cnt;
	}
	private static void printMap(int[][] map) {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	static class Cctv{
		public Point pt;
		public int type;
		public int d;
		public Cctv() {
			
		}
		public Cctv(Point pt, int type, int d) {
			this.pt = pt;
			this.type = type;
			this.d = d;
		}
	}
	static class Point{
		public int r, c;
		public Point() {
			
		}
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
}
