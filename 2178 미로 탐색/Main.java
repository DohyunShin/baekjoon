import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static class Point {
		public int r, c;
		public Point() {}
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	static int N, M; //2~100
	static int[][] map = new int[100][100];
	static Queue<Point> q = new LinkedList<Point>();
	static boolean[][] visit = new boolean[100][100];
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	static int min = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		String row;
		for(int i=0; i<N; i++) {
			row = br.readLine();
			for(int j=0; j<M; j++) {
				map[i][j] = row.charAt(j)-'0';
			}
		}
		br.close();
		
		solution();
		System.out.println(min);
	}
	
	private static void solution() {
		bfs();
	}
	
	private static void bfs() {
		q.add(new Point(0,0));
		
		Point curPt;
		Point nextPt = new Point();
		int depth = 1;
		
		while(q.size() > 0) {
			int length = q.size();
			depth++;
			
			for(int i=0; i<length; i++) {
				curPt = q.poll();
				visit[curPt.r][curPt.c] = true;
			
				for(int d=0; d<4; d++) {
					nextPt.r = curPt.r+dr[d];
					nextPt.c = curPt.c+dc[d];
					
					if(!(nextPt.r>=0 && nextPt.r<N && nextPt.c>=0 && nextPt.c<M) || map[nextPt.r][nextPt.c] == 0|| visit[nextPt.r][nextPt.c]) continue;
					
					if(nextPt.r == N-1 && nextPt.c == M-1) {
						min = depth;
						return;
					}
					visit[nextPt.r][nextPt.c] = true;
					q.add(new Point(nextPt.r, nextPt.c));
				}
			}
		}
	}
}
