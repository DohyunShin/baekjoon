import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static class Point{
		public int r, c; 
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	static int N, M; //2~50
	static int T; //1~50
	static int[][] map = new int[50][50]; //1~1000
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	static int res = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int x, d, k;
		for(int i = 0; i < T; i++) {
			st = new StringTokenizer(br.readLine());
			x = Integer.parseInt(st.nextToken());
			d = Integer.parseInt(st.nextToken());
			k = Integer.parseInt(st.nextToken());
			solution(x,d,k);
		}
		br.close();
		
		calculate();
		System.out.println(res);
	}
	
	//x: 0~N, d: 0/1, k: 1~M
	public static void solution(int x, int d, int k) {
		//돌릴 원판 찾아서 돌리기
		for(int n = 0; n < N; n++) {
			if((n+1) % x == 0) {
				int[] newRow = new int[50];
				
				int idx;
				if(d == 0) {
					for(int m = 0; m < M; m++) {
						idx = (m+k) >= M ? m+k-M : m+k;
						newRow[idx] = map[n][m];
					}
				}
				else {
					for(int m = M-1; m >= 0; m--) {
						idx = (m-k) < 0 ? m-k+M : m-k;
						newRow[idx] = map[n][m];
					}
				}
				
				map[n] = newRow;
				
				//printMap();
			}
		}
		
		//원판 갱신
		refresh();
		//printMap();
	}
	
	public static void calculate() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(map[i][j] == 0) continue;
				res+=map[i][j];
			}
		}
	}
	
	public static void refresh() {
		boolean found = false;
		
		int sum = 0;
		int cnt = 0;
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(map[i][j] == 0) continue;
				
				//제거할 대상을 찾을때까지는 평균으로 값을 갱신할 것을 대비하여 수를 누적시킨다.
				if(!found) {
					sum += map[i][j];
					cnt++;
				}
				
				//한번이라도 찾았다면 found 는 true가 된다.
				if(bfs(i, j) && found == false) found = true;
			}
		}
		
		//수를 한번도 지우지 못한 경우 원판 평균 값으로 갱신
		if(!found && cnt > 0) {
			int avr = sum/cnt;
			
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < M; j++) {
					if(map[i][j] == 0) continue;
					
					if(map[i][j] > avr) map[i][j]--;
					else if(map[i][j] < avr) map[i][j]++;
					else {
						if(sum%cnt > 0) {
							map[i][j]++;
						}
					}
				}
			}
		}
	}
	
	//인접하면서 같은 모든 수를 찾은 후 한번에 제거해야하기 때문에 BFS를 사용
	public static boolean bfs(int r, int c) {
		boolean res = false;
		
		int value = map[r][c];
		
		Queue<Point> q = new LinkedList<Point>();
		q.add(new Point(r, c));
		
		Point cur;
		int nr, nc;
		while(!q.isEmpty()) {
			cur = q.poll();
			
			for(int d = 0; d < 4; d++) {
				nr = cur.r + dr[d];
				nc = cur.c + dc[d];
								
				//순환 관계이기 때문에
				if(nc == -1) nc = M-1;
				else if(nc == M) nc = 0;
				
				if(!(nr >= 0 && nr < N)) continue;
				
				if(map[nr][nc] == value && !(nr == r && nc == c)) {
					map[nr][nc] = 0;
					res = true;
					
					q.add(new Point(nr, nc));
				}
			}
		}
		
		//같은 값이 있었다면 시작 지점도 제거한다.
		if(res) map[r][c] = 0;
		return res;
	}
	
	public static void printMap() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
