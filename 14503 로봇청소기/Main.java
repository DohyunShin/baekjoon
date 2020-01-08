import java.util.Scanner;

public class Main {

	static int N, M;
	static int map[][];
	static boolean check[][];
	static int r, c;
	static int d; //0북, 1동, 2남, 3서
	static int dx[] = {0, 1, 0, -1};
	static int dy[] = {-1, 0, 1, 0};
	static int cleanCnt = 0;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt() + 2;
		M = sc.nextInt() + 2;
		map = new int[N][M];
		check = new boolean[N][M];
		r = sc.nextInt() + 1; //맵 패딩처리 시 주의!!
		c = sc.nextInt() + 1;
		d = sc.nextInt();
		for(int i = 1; i < N - 1; i++) {
			for(int j = 1; j < M - 1; j++) {
				map[i][j] = sc.nextInt();
			}
		}
		sc.close();
		
		//맵 패딩처리
		for(int i = 0; i < N; i++) {
			if(i == 0 || i == N - 1) {
				for(int j = 0; j < M; j++) {
					map[i][j] = 1;
				}
			}			
			else {
				map[i][0] = 1;
				map[i][M - 1] = 1;
			}
		}
		
		solution();
		System.out.println(cleanCnt);
	}
	
	private static void printMap() {
		for(int i = 1; i < N -1; i++) {
			for(int j = 1; j < M -1; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private static void printCheck() {
		for(int i = 1; i < N -1; i++) {
			for(int j = 1; j < M -1; j++) {
				System.out.print(check[i][j] ? "1 " : "0 ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private static void solution() {
		//현재위치, 방향
		int cr = r;
		int cc = c;
		int cd = d;
				
		check[cr][cc] = true; //현재위치 청소
		int cnt = 0; //현재 자리에서 회전 수 
		while(true) {
			//한 자리에서 네 방향 모두 청소가 되있거나 벽인 경우 
			if(cnt == 4) {
				//현재 바라보는 방향에서 후진 가능한지 확인
				int br = cr - dy[cd];
				int bc = cc - dx[cd];
				
				if(map[br][bc] == 0) {
					//가능하면 후진
					cr = br;
					cc = bc;
					cnt = 0;	
				}
				else {
					//후진도 불가능한 경우 정지
					break;
				}
			}
			
			//바라보기 왼쪽으로
			//북 -> 서, 서 -> 남, 남 -> 동, 동 -> 북
			//0 -> 3, 3 -> 2, 2 - > 1, 1 -> 0
			//방향 값 - 1;
			int nd = cd - 1;
			if(nd < 0) {
				nd = 3;
			}
			//바라보는 위치
			int nr = cr + dy[nd];
			int nc = cc + dx[nd];
			
			//바라보는 위치가 청소가 가능한지 확인
			if(check[nr][nc] == false && map[nr][nc] == 0) {
				//청소 가능한 경우 바라보는 방향으로 전진 후 청소
				cr = nr;
				cc = nc;
				cd = nd;
				if(check[cr][cc] == false) {
					check[cr][cc] = true; //현재위치 청소
					//printCheck();
				}
				cnt = 0; //전진하여 이동하기 때문에 초기화
			}
			else {
				//이미 청소한 경우 바라보는 방향으로 회전만
				cd = nd;
				cnt++;
			}
		}
		
		//탐색 끝난 뒤 청소한 구역의 개수
		for(int i = 1; i < N - 1; i++) {
			for(int j = 1; j < M -1; j++) {
				if(check[i][j] == true) {
					cleanCnt++;
				}
			}
		}
	}
}
