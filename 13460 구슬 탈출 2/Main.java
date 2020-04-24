import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static class Point{
		int rr, rc, br, bc;
		public Point(int rr, int rc, int br, int bc){
			this.rr = rr;
			this.rc = rc;
			this.br = br;
			this.bc = bc;
		}
		
		public Point() {}
	}
	
	static int N; //3~10
	static int M; //3~10
	static char[][] map = new char[10][10];
	static int min = Integer.MAX_VALUE;
	
	static Point pt = new Point();
	static final int[] dr = {-1,1,0,0};
	static final int[] dc = {0,0,-1,1};
	static boolean[][][][] visit = new boolean[10][10][10][10];
	static Queue<Point> q = new LinkedList<Point>();
	
	//. 빈칸, # 장애물 또는 벽, o 구멍 위치, R 빨구, B 파구
	//10번 초과하면 -1 출력
	
	public static void main(String[] args) throws IOException{
		BufferedReader bre = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(bre.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		char[] temp;
		for(int i = 0; i < N; i++) {
			temp = bre.readLine().toCharArray();
			for(int j = 0; j < M; j++) {
				if(temp[j] == 'R') {
					pt.rr = i;
					pt.rc = j;
					map[i][j] = '.';
				}
				else if(temp[j] == 'B') {
					pt.br = i;
					pt.bc = j;
					map[i][j] = '.';
				}
				else {
					map[i][j] = temp[j];
				}
			}
		}
		bre.close();
		
		solution();
		System.out.println(min == Integer.MAX_VALUE ? -1 : min);
	}
	
	private static void solution() {
		bfs();
	}
	
	private static void bfs() {
		int cnt = 0;
		
		Point npt = new Point();

		visit[pt.rr][pt.rc][pt.br][pt.bc] = true;
		q.add(pt);
		
		while(!q.isEmpty()) {
			
			boolean found = false;
			
			int qLen = q.size();
			
			while(qLen-- > 0) {
				Point cpt = q.poll();
				
				//검사
				if(map[cpt.rr][cpt.rc] == 'O' && map[cpt.br][cpt.bc] != 'O') {
					found = true;
					break;
				}
				
				//빨간공, 파란공 각각의 네 방향 검사
				for(int d = 0; d < 4; d++) {
					Point tpt = new Point();
					tpt.rr = cpt.rr;
					tpt.rc = cpt.rc;
					tpt.br = cpt.br;
					tpt.bc = cpt.bc;
					
					//빨간공 검사
					while(true) {
						npt.rr = tpt.rr + dr[d];
						npt.rc = tpt.rc + dc[d];
						
						if(map[npt.rr][npt.rc] == '#' ||
							map[tpt.rr][tpt.rc] == 'O') break;
						
						tpt.rr = npt.rr;
						tpt.rc = npt.rc;
					}
					
					//파란공 검사
					while(true) {
						npt.br = tpt.br + dr[d];
						npt.bc = tpt.bc + dc[d];
						
						if(map[npt.br][npt.bc] == '#' ||
							map[tpt.br][tpt.bc] == 'O') break;
						
						tpt.br = npt.br;
						tpt.bc = npt.bc;
					}
					
					//파란공이 구멍에 들어간 경우
					if(map[tpt.br][tpt.bc] == 'O') continue;
					
					//공 위치가 겹치는 경우
					if(tpt.rr == tpt.br && tpt.rc == tpt.bc) {
						//red, blue 각각 원래 지점에서 얼마나 멀어졌는지 본다. 
						//이동 방향과 이동한 위치는 동일하기 때문에 이동한 지점으로 부터 원래 지점이 가까운 공이 우선이다.
						//이동 방향은 상, 하, 좌, 우 중 하나이기 때문에 y의 차거나 x의 차이다. 좌, 우로 이동하면 어차피 y의 차는 0
						if(Math.abs(tpt.rr - cpt.rr) + Math.abs(tpt.rc - cpt.rc) > Math.abs(tpt.br - cpt.br) + Math.abs(tpt.bc - cpt.bc)) {
							//이동 지점으로 부터 원래 지점이 blue가 더 가깝다.
							//red를 이동 지점에서 왔던 방향으로 한 칸 back.
							tpt.rr -= dr[d];
							tpt.rc -= dc[d];
						}
						else {
							//이동 지점으로 부터 원래 지점이 red가 더 가깝다.
							//blue를 이동 지점에서 왔던 방향으로 한 칸 back.
							tpt.br -= dr[d];
							tpt.bc -= dc[d];
						}
					}
					
					//현재 상태 저장
					if(visit[tpt.rr][tpt.rc][tpt.br][tpt.bc] == false) {
						visit[tpt.rr][tpt.rc][tpt.br][tpt.bc] = true;
						q.add(new Point(tpt.rr, tpt.rc, tpt.br, tpt.bc));
					}
				}
			}
			
			if(found) {
				if(cnt < min) {
					min = cnt;
				}
				return;
			}
			
			cnt++;
			if(cnt > 10) {
				min = -1;
				break;
			}
		}
		
		min = -1;
	}
}
