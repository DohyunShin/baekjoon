import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	static final int dx[] = { 0, 0, -1, 1};
	static final int dy[] = {-1, 1, 0, 0};
	static int rx, ry, bx, by;
	static int orx, ory, obx, oby; //현재 좌표
	static int nrx, nry, nbx, nby; //이동할 좌표
	static int n, m;
	static int cnt;
	static char map[][] = new char[10][10];
	static boolean visit[][][][] = new boolean[10][10][10][10];
	
	static class Point {
		int rx, ry, bx, by;
		
		public Point(int rx, int ry, int bx, int by) {
			this.rx = rx;
			this.ry = ry;
			this.bx = bx;
			this.by = by;
		}
	}
	static Queue<Point> q = new LinkedList<Point>();
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		m = sc.nextInt();
		map = new char[n][m];
		sc.nextLine();
		for(int i = 0; i < n; i++) {
			map[i] = sc.nextLine().toCharArray();
			
			for(int j = 0; j < m; j++) {	
				if(map[i][j] == 'R') {
					rx = j;
					ry = i;
					map[i][j] = '.';
				}
				if(map[i][j] == 'B') {
					bx = j;
					by = i;
					map[i][j] = '.';
				}
			}
		}
		sc.close();
		
		visit[rx][ry][bx][by] = true;
		q.add(new Point(rx, ry, bx, by));
		bfs();
		System.out.println(cnt);
	}
	
	//네 방향을 보면서 진행해야한다. 주변을 살피면서 탐색하기 위해 bfs 사용
	private static void bfs() {		
		
		int crx, cry, cbx, cby; //이동하는 좌표
				
		//큐가 비어서 검사할 것이 없을 때 가지 진행
		while(!q.isEmpty()) {
			//큐에 저장된 가능성이 있는 상태를 한꺼번에 모두 검사한다.
			//네방향 검사 순서에 따라 불필요한 카운터 증가를 막기 위해
			//뻗어나가는 네방향 검사의 결과물을 단계별로 처리하면 한 단계에 한 카운터가 증가하고
			//연관된 모든 네방향 결과물을 검사할 수 있다.
			//에) 최초 검사 이후의 큐 [상, 하, 좌] 1단계
			//상, 하, 좌를 모두 검사 하고 카운터 하나 증가, 1단계 상,하,좌 우 상태의 모든 네방향 검사 후 큐의 상태
			//2단계 [1단계 상-상, 1단계 상-하, 1단계 하-좌, 1단계 좌-우] 2단계도 역시 한번에 처리한다.
			int qlen = q.size();
			while(qlen-- > 0) {				
				orx = q.peek().rx;
				ory = q.peek().ry;
				obx = q.peek().bx;
				oby = q.peek().by;
				q.poll();
				
				if(map[ory][orx] == 'O' && map[oby][obx] != 'O') {
					//정답이 나오면 바로 종료, 최소 횟수이기 때문에
					return;
				}
				
				//현재 좌표에서 네 방향으로 검사를 진행하여 진행 가능한 상태를 미리 보고
				//가능한 위치를 큐에 저장한다.
				for(int i = 0; i < 4; i++) {
					crx = orx;
					cry = ory;
					cbx = obx;
					cby = oby;
					
					//red
					while(true) {
						nrx = crx + dx[i];
						nry = cry + dy[i];
						//이동한 좌표가 목표지점이거나 그 다음 좌표가 벽인 경우  까지(벽 이전 까지) 이동 
						if(map[nry][nrx] == '#' || map[cry][crx] == 'O') {
							break;
						}
						//이동
						crx = nrx;
						cry = nry;
					}
					
					//blue
					while(true) {
						nbx = cbx + dx[i];
						nby = cby + dy[i];
						//이동한 좌표가 목표지점이거나 그 다음 좌표가 벽인 경우  까지(벽 이전 까지) 이동 
						if(map[nby][nbx] == '#' || map[cby][cbx] == 'O') {
							break;
						}
						//이동
						cbx = nbx;
						cby = nby;
					}
					
					//이동 후 blue가 목표지점에 들어갈 경우 볼 필요가 없다.
					//red와 겹치더라도 결국 목표지점에 도착하게 된다.
					if(map[cby][cbx] == 'O') {
						continue;
					}
					
					//red, blue 이동 후 겹치는 경우 위치 조정
					if(crx == cbx && cry == cby) {
						//red, blue 각각 원래 지점에서 얼마나 멀어졌는지 본다. 
						//이동 방향과 이동한 위치는 동일하기 때문에 이동한 지점으로 부터 원래 지점이 가까운 공이 우선이다.
						//이동 방향은 상, 하, 좌, 우 중 하나이기 때문에 y의 차거나 x의 차이다. 좌, 우로 이동하면 어차피 y의 차는 0
						if(Math.abs(orx - crx) + Math.abs(ory - cry) > Math.abs(obx - cbx) + Math.abs(oby - cby)) {
							//이동 지점으로 부터 원래 지점이 blue가 더 가깝다.
							//red를 이동 지점에서 왔던 방향으로 한 칸 back.
							crx -= dx[i];
							cry -= dy[i];
						}
						else {
							//이동 지점으로 부터 원래 지점이 red가 더 가깝다.
							//blue를 이동 지점에서 왔던 방향으로 한 칸 back.
							cbx -= dx[i];
							cby -= dy[i];
						}
					}
					
					//위치 조정 후 현재 상태를 (방문하지 않았다면) 방문하고 다음 검사를 위해 큐에 삽입
					//여기서 방문되고 큐에 삽입되는 상태는 원상태에서 네 방향을 검사하며 가능성이 있는 상태만 기록된다. (가지치기)
					//가능성이 있는 상태라면 blue 가 목표지점에 도착하지 않는것.
					if(visit[crx][cry][cbx][cby] == false) {
						visit[crx][cry][cbx][cby] = true;
						q.add(new Point(crx, cry, cbx, cby));
					}
				}
			}
			
			//큐에서 진행 가능한 상태를 꺼내어 다음 단계의 검사를 진행할 것이기 때문에 카운터를 증가 시킨다.
			//카운터가 이미 10회인 경우 -1로 종료한다.
			if(cnt == 10) {
				cnt = -1;
				return;
			}
			cnt++;
		}
			
			
		//큐가 비어서 검사할 것이 없는 경우 -1로 종료
		cnt = -1;
		return;
}
	
	
}
