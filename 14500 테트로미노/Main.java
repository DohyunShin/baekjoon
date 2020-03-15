import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, M;
	static int[][] map;
	static final Tec[] tecs = {new Tec1(), new Tec2(), new Tec3(), new Tec4(), new Tec5()};
	static int max = 0;
	
	static class Tec{
		int rotationCnt;
		int[][] dr;
		int[][] dc;
	}
	//ㅡ
	static class Tec1 extends Tec{
		public Tec1() {
			rotationCnt = 2;
			int[][] temp_dr = {
					{0,0,0,0},
					{0,1,1,1}
			};
			int[][] temp_dc = {
					{0,1,1,1},
					{0,0,0,0}
			};
			dr = temp_dr;
			dc = temp_dc;
		};
	}
	//ㅁ
	static class Tec2 extends Tec{
		public Tec2() {
			rotationCnt = 1;
			int[][] temp_dr = {
					{0,0,1,0}
			};
			int[][] temp_dc = {
					{0,1,0,-1}
			};
			dr = temp_dr;
			dc = temp_dc;
		};
	}
	//ㄱ
	static class Tec3 extends Tec{
		public Tec3() {
			rotationCnt = 8;
			int[][] temp_dr = {
					{0,1,1,0},
					{0,-1,0,0},
					{0,0,1,1},
					{0,0,0,-1},
					{0,-1,-1,0},
					{0,1,0,0},
					{0,0,-1,-1},
					{0,0,0,1}
			};
			int[][] temp_dc = {
					{0,0,0,1},
					{0,0,1,1},
					{0,1,0,0},
					{0,1,1,0},
					{0,0,0,1},
					{0,0,1,1},
					{0,1,0,0},
					{0,1,1,0}
			};
			dr = temp_dr;
			dc = temp_dc;
		};
	}
	//ㄹ
	static class Tec4 extends Tec{
		public Tec4() {
			rotationCnt = 4;
			int[][] temp_dr = {
					{0,1,0,1},
					{0,1,0,1},
					{0,0,1,0},
					{0,0,1,0}
			};
			int[][] temp_dc = {
					{0,0,1,0},
					{0,0,-1,0},
					{0,-1,0,-1},
					{0,1,0,1}
			};
			dr = temp_dr;
			dc = temp_dc;
		};
	}
	//ㅜ
	static class Tec5 extends Tec{
		public Tec5() {
			rotationCnt = 4;
			int[][] temp_dr = {
					{0,0,1,-1},
					{0,0,-1,2},
					{0,1,0,1},
					{0,0,-1,1}
			};
			int[][] temp_dc = {
					{0,1,0,1},
					{0,1,0,0},
					{0,0,1,-1},
					{0,1,0,1}
			};
			dr = temp_dr;
			dc = temp_dc;
		};
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		for(int i = 0; i < N; ++i) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; ++j) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		br.close();
		
		solution();
		System.out.println(max);
	}
	
	private static void solution() {
		for(int r=0;r<N;r++) {
			for(int c=0;c<M;c++) {
				tecExecute(r,c);
			}
		}
	}
	
	private static void tecExecute(int r, int c) {
		for(int i = 0; i < tecs.length; i++) {
			Tec tec = tecs[i];
			for(int j = 0; j < tec.rotationCnt; j++) {
				int[] dr = tec.dr[j];
				int[] dc = tec.dc[j];
				
				int nr =r, nc=c;
				int sum=0;
				boolean success = true;
				for(int d = 0; d < 4; d++) {
					nr += dr[d];
					nc += dc[d];
					if(!(nr >=0 && nr < N && nc >=0 && nc < M)) {
						success = false;
						break;
					}
					sum+=map[nr][nc];
				}
				
				if(success) {
					if(max < sum) max = sum;
				}
			}
		}
	}
}
