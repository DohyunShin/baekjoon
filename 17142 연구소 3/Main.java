import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Main {
	static int N, M;
	static int map[][]; //0: 활성바이러스, -1: 비활성 바이러스, -2: 벽, -3: 빈칸, 1이상: 바이러스 걸린 시간
	final static int MIN_INIT = 999999999;
	static int min = MIN_INIT;
	final static int dr[] = {-1, 1, 0, 0};
	final static int dc[] = {0, 0, -1, 1};
	static int emptyCnt = 0;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		map = new int[N][N];
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {				
				switch(sc.nextInt()) {
				case 0:
					map[i][j] = -3;
					emptyCnt++;
					break;
				case 1:
					map[i][j] = -2;
					break;
				case 2:
					map[i][j] = -1;
					break;
				}
			}
		}
		
		sc.close();
	
		solution();
		System.out.println(min == MIN_INIT ? -1 : min);
	}
	
	private static void solution() {
		if(emptyCnt == 0) {
			min = 0;
			return;
		}
		
		findVirus(0, 0, 0, new int[M], new int[M]);
	}
	
	private static void findVirus(int mCnt, int cr, int cc, int[] vrs, int[] vcs) {
		//바이러스를 활성화 할 만큼 찾는다.
		if(mCnt == M) {
			diffuseVirus(vrs, vcs);
			return;
		}
		
		for(int i = cr; i < N; i++) {
			for(int j = cc; j < N; j++) {
				if(map[i][j] == -1) {
					map[i][j] = 0;
					vrs[mCnt] = i;
					vcs[mCnt] = j;
					
					findVirus(mCnt + 1, i, j + 1, vrs, vcs);
					
					map[i][j] = -1;
					vrs[mCnt] = 0;
					vcs[mCnt] = 0;
				}
			}
			
			cc = 0;
		}
	}
	
	private static void diffuseVirus(int[] vrs, int[] vcs) {
		int cnt = 0;
		
		int preEmptyCnt = 0; //직전 빈공간 개수
		int curEmptyCnt = emptyCnt; //현재 빈공간 개수
		
		int visit[][] = new int[N][N]; //퍼지는 순서 보려고 int로 했음, boolean으로 해도 상관없다.
		Deque<Integer> vrdq = new ArrayDeque<Integer>();
		Deque<Integer> vcdp = new ArrayDeque<Integer>();
		for(int i = 0; i < M; i++) {
			vrdq.add(vrs[i]);
			vcdp.add(vcs[i]);
			visit[vrs[i]][vcs[i]] = 1;
		}
		
		while(true) {
			
			//printVisit(visit);
			
			curEmptyCnt -= diffuseVirusEx(visit, vrdq, vcdp);
			cnt++;
			
			//빈공간이 없는 경우
			if(curEmptyCnt == 0) {
				if(min > cnt) {
					min = cnt;
				}
				break;
			}
			//빈공간은 있지만 직전 빈공간 개수가 같은 경우(변화가 없는 경우) 빈공간을 전부 퍼뜨릴 수 없다고 판단한다.
			else if(curEmptyCnt == preEmptyCnt) {
				//불가능한 경우 min의 값을 변경하지 않은다. 모든 검사가 끝난 후 min의 값이 그대로라면 모든 케이스가 불가능한 것으로 판단하고 -1 출력하게 한다.
				break;
			}
			else {
				preEmptyCnt = curEmptyCnt;
			}
		}
	}
	
	private static int diffuseVirusEx(int visit[][], Deque<Integer> vrs, Deque<Integer> vcs) {
		int vCnt = vrs.size();
		int changeEmptyCnt = 0; //감염된 공간의 개수 저장
		
		//활성화할 바이러스를 개수 만큼 꺼내서 활성화 시킨다.
		for(int i = 0; i < vCnt; i++) {
			int vr = vrs.pop();
			int vc = vcs.pop();
			
			int nr, nc;
			for(int d = 0; d < 4; d++) {
				nr = vr + dr[d];
				nc = vc + dc[d];
				if(!(nr >=0 && nr < N && nc >= 0 && nc < N)) {
					continue;
				}
				
				if(visit[nr][nc] == 0 && map[nr][nc] == -3) {
					visit[nr][nc] = visit[vr][vc] + 1; //퍼트리는 씨앗 바이러스의 값에 + 1
					vrs.add(nr);
					vcs.add(nc);
					
					changeEmptyCnt++;
				}
				//비활성 바이러스가 있는 곳은 바이러스가 활성화 되지만 빈칸이 감염되는 것은 아니다.(주으!!)
				else if(visit[nr][nc] == 0 && map[nr][nc] == -1) {
					visit[nr][nc] = visit[vr][vc] + 1; //퍼트리는 씨앗 바이러스의 값에 + 1
					vrs.add(nr);
					vcs.add(nc);
				}
			}
		}	
		
		return changeEmptyCnt;
	}
	
	private static void printVisit(int visit[][]) {
		for(int i = 0; i < N; i++) {
			for(int j =0;j<N;j++) {
				System.out.print(visit[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	private static void printMap(int map[][]) {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				switch(map[i][j]) {
				case 0:
					System.out.print("0 ");
					break;
				case -1:
					System.out.print("* ");
					break;
				case -2:
					System.out.print("- ");
					break;
				case -3:
					System.out.print("x ");
					break;
				default:
					System.out.print(map[i][j] + " ");
					break;
				}
			}
			
			System.out.println();
		}
		System.out.println();
	}
}
