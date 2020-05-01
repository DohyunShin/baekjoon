import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int N, M; //3~8
	static int[][] map = new int[8][8];
	static int max = Integer.MIN_VALUE;
	static int emptyCnt = 0;
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				int input = Integer.parseInt(st.nextToken());
				if(input == 0) emptyCnt++;
				map[i][j] = input;
			}
		}
		br.close();
		
		solution();
		System.out.println(max);
	}
	
	private static void solution() {
		recursive(0,0,0,emptyCnt);
	}
	
	private static void recursive(int sr, int sc, int wallCnt, int curEmptyCnt) {
		if(wallCnt == 3) {
			//바이러스 확장
			int[][] curMap = map.clone();
			for(int i = 0; i < N; i++) {
				curMap[i] = map[i].clone();
			}
			Queue<Point> q = new LinkedList<Point>();
			for(int r=0;r<N;r++) {
				for(int c=0;c<M;c++) {
					if(curMap[r][c] != 2) continue;
					q.add(new Point(r,c));
				}
			}
			bfs(curMap, q, curEmptyCnt);			
			return;
		}
		
		if(!(sr>=0&&sr<N&&sc>=0&&sc<M)) return;
		
		int nr = sr;
		int nc = sc+1;
		if(nc >= M) {
			nc = 0;
			nr++;
		}
		
		if(map[sr][sc] == 0) {
			//벽을 세우는 경우
			map[sr][sc] = 1;
			recursive(nr,nc,wallCnt+1, curEmptyCnt-1);
			map[sr][sc] = 0;
		}
		
		//벽을 세우지 않는 경우
		recursive(nr,nc,wallCnt, curEmptyCnt);
	}
	
	static class Point{
		int r,c;
		public Point(int r, int c) {this.r=r; this.c=c;}
	}
	
	private static void bfs(int[][] curMap, Queue<Point> q, int curEmptyCnt) {
		while(!q.isEmpty()) {
			Point v = q.poll();
			
			int nr, nc;
			for(int d=0;d<4;d++) {
				nr = v.r+dr[d];
				nc = v.c+dc[d];
				
				if(!(nr>=0&&nr<N&&nc>=0&&nc<M) || curMap[nr][nc] != 0) continue;
				
				curMap[nr][nc] = 2;
				curEmptyCnt--;
				q.add(new Point(nr,nc));
			}
		}
		
		if(curEmptyCnt > max) max = curEmptyCnt;
	}
}
