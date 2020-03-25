import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	//비활성 바이러스 중 M개를 고르는 조합
	//활성화시키는 로테이션을 돌때마다 시간을 증가 시킨다.
	//전체 빈공간에서 바이러스가 퍼지는 구간의 개수를 빼준다. 0이 될때까지 진행
	//비활성 바이러스 구간도 바이러스가 존재한다고 생각하면된다.
	
	static int N; //4~50
	static int M; //1~10
	static int[][] map = new int[50][50];
	static int emptyCnt = 0; //빈칸의 수
	static int vCnt = 0; //총 바이러스 개수
	static boolean[] visit = new boolean[10]; //바이러스 조합구하기 방문 표시
	static int[] vr = new int[10]; //바이러스 위치
	static int[] vc = new int[10];
	static int min = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		int input = 0;
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				input = Integer.parseInt(st.nextToken());
				if(input==0)emptyCnt++;
				if(input==2) {
					vr[vCnt] = i;
					vc[vCnt] = j;
					vCnt++;
				}
				map[i][j] = input - 2; //바이러스0,벽-1,빈곳-2
			}
		}
		br.close();
		
		if(emptyCnt == 0) {
			System.out.println(0);
		}
		else {
			solution();
			System.out.println(min == Integer.MAX_VALUE ? -1 : min);	
		}
	}
	
	private static void solution() {
		combination(0, 0);
	}
	
	private static void combination(int cnt, int index) {
		if(cnt==M) {
//			for(int i = 0; i < vCnt; i++) {
//				if(visit[i]) System.out.print(i + " ");
//			}
//			System.out.println();
			
			simulation();
			
			return;
		}
		
		if(cnt > M || index >= vCnt || visit[index]) return;
		
		visit[index] = true;
		combination(cnt+1, index+1);
		visit[index] = false;
		
		combination(cnt, index+1);
	}
	
	private static void simulation() {
		int[][] smap = map.clone();
		for(int i = 0; i < N; i++) {
			smap[i] = map[i].clone();
		}
		
		int semptyCnt = emptyCnt;
		
		int cnt = 0;
		
		//처음 선택된 바이러스 활성화
		cnt++;
		for(int i = 0; i < vCnt; i++) {
			if(visit[i]) smap[vr[i]][vc[i]] = cnt;
		}
		
		int[] dr = {-1,1,0,0};
		int[] dc = {0,0,-1,1};
		
		//변화가 있었는지 확인
		boolean isChange = false;
	
		do {
			isChange = false;
			
			int nextCnt = cnt+1;
			
			for(int r = 0; r < N; r++) {
				for(int c = 0; c < N; c++) {
					if(smap[r][c] == cnt) {
						for(int d=0; d<4; d++) {
							int nr = r+dr[d];
							int nc = c+dc[d];
							
							if(!(nr>=0 && nr <N && nc>=0 && nc <N)) continue;
							if(!(smap[nr][nc] == -2 || smap[nr][nc] == 0)) continue;
							
							if(smap[nr][nc] != 0) semptyCnt--;
							smap[nr][nc] = nextCnt;
							
							
							if(!isChange) isChange = true;
							
							//printMap(smap);
						}
					}
				}
			}
			if(semptyCnt == 0) break;
			
			cnt = nextCnt;
		}while(isChange);
		
		if(semptyCnt == 0) {
			if(min > cnt) min = cnt;
		}
	}
	
	private static void printMap(int[][] pmap) {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				char c;
				if(pmap[i][j]==-1) c = '*';
				else if(pmap[i][j]==-2) c = '_';
				else c = (char)('0'+pmap[i][j]);
				System.out.print(c + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

}
