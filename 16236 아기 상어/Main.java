import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Main {
	private static class BfsResult{
		public Point pt;
		public int moveCnt;
		public BfsResult(Point pt, int moveCnt) {
			this.pt = pt;
			this.moveCnt = moveCnt;
		}
	}
	private static class Point{
		public int r, c;
		public Point() {}
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	static int N;
	static int map[][];
	static int bs = 2; //아기 상어 크기
	static Point bpt; //아기 상어 위치
	static int eat = 0; //아기 상어가 먹은 물고기 수
	static int dr[] = {-1, 1, 0, 0};
	static int dc[] = {0, 0, -1, 1};
	static int cnt = 0; //최소 이동거리
	
	public static void main(String[] args) {	
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		map = new int[N][N];
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				int input = sc.nextInt();
				if(input >= 0 && input <= 6) {
					map[i][j] = input;
				}
				else if(input == 9) {
					bpt = new Point(i, j);
				}
			}
		}
		sc.close();
		
		solution();
		System.out.println(cnt);
	}
	
	private static void solution() {		
		while(true) {		
			
			//bfs 탐색
			//가장 가까운 물고기를 찾는다.
			BfsResult bfsResult = bfs();
			
			//더이상 먹을 수 있는 물고기가 없다면 종료한다.
			if(bfsResult == null) {
				break;
			}
			
			//아기 상어를 해당 위치로 옮기고, 물고기를 먹는다.
			bpt = bfsResult.pt;
			map[bpt.r][bpt.c] = 0;
			eat++;
			//아기 상어가 본인 크기 만큼 물고기를 먹었다면 먹은 물고기 수를 초기화 하고 성장한다.
			if(eat == bs) {
				eat = 0;
				bs++;
			}
			//움직인 거리를 저장
			cnt += bfsResult.moveCnt;
			
			/* print map
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					if(i == bpt.r && j == bpt.c) {
						System.out.print("x ");	
					}
					else {
						System.out.print(map[i][j] + " ");
					}
					
				}
				System.out.println();
			}
			System.out.println("size: " + bs + " move: " + cnt + " eat:" + eat);
			*/
		}
	}
	
	private static BfsResult bfs() {
		//현재 상어 위치
		int depth = 0; //이동거리가 된다.
		Point cpt = bpt;
		
		Deque<Point> dq = new ArrayDeque<Point>();
		boolean visit[][] = new boolean[N][N];
		
		dq.push(cpt);
		visit[cpt.r][cpt.c] = true;
		
		while(true) {
			depth++;
			Deque<Point> sdq = new ArrayDeque<Point>(); //메인 큐가 비워지는 동안 각각의 네방향을 담을 큐
			
			Point fpt = null;
			
			//큐가 비워질 때 까지 검사
			while(!dq.isEmpty()) {
				cpt = dq.pop();
				
				Point npt = null;
				
				for(int i = 0; i < 4; i++) {
					npt = new Point();
					
					npt.r = cpt.r + dr[i];
					npt.c = cpt.c + dc[i];
					
					//범위 내에 방문하지 않은 곳만 조사
					if(!(npt.r >= 0 && npt.r < N && npt.c >= 0 && npt.c < N) || visit[npt.r][npt.c] == true) {
						continue;
					}
					
					//위치에 물고기가 큰 경우
					if(map[npt.r][npt.c] > bs) {
						continue;
					}
					
					//지나가는 경우
					if(map[npt.r][npt.c] == 0 || map[npt.r][npt.c] == bs) {
						visit[npt.r][npt.c] = true;
						sdq.push(npt);
					}
					//먹이를 찾은 경우
					else if(map[npt.r][npt.c] < bs) {
						//여러개 찾을 수 있으니까 비교
						if(fpt == null) {
							fpt = npt;
						}
						else {
							if(fpt.r > npt.r) {
								fpt = npt;
							}
							else if(fpt.r == npt.r){
								if(fpt.c > fpt.c) {
									fpt = npt;
								}
							}
						}
						
						visit[npt.r][npt.c] = true;
						sdq.push(npt);
					}
				}
			}
			
			//먹이를 찾은 경우
			if(fpt != null) {
				return new BfsResult(fpt, depth);
			}
			else {
				//서브 큐의 아이템들을 메인큐로 옮겨 담는다. 다음 검사를 준비
				while(!sdq.isEmpty()) {
					dq.push(sdq.pop());
				}
			}
			
			//다음 검사할 아이템이 없는 경우 종료
			if(dq.isEmpty()) {
				break;
			}
		}
		
		return null;
	}
}
