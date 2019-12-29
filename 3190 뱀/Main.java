import java.util.HashMap;
import java.util.Scanner;

public class Main {

	static int N; //보드 크기
	static int K; //사과 개수
	static int L; //뱀의 방향 변환 횟수
	static HashMap<Integer, String> XC = new HashMap<>(); //X초 끝난 뒤 방향(L:왼, D:오)
	static int map[][];
	static int cnt; //시간이면서 횟수 (1초에 1번 움직임)
	static int d; //상하좌우 = 0, 1, 2, 3
	static int dx[] = { 0, 0, -1, 1};
	static int dy[] = {-1 , 1, 0, 0};
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt() + 2; //패딩처리 벽 = -1
		map = new int[N][N];
		for(int i = 0; i < N; i++) {
			if(i == 0 || i == N -1) {
				for(int j = 0; j < N; j++) {
					map[i][j] = -1;
				}
			}
			else {
				map[i][N - 1] = -1;
				map[i][0] = -1;
			}
		}
		
		map[1][1] = 1; //뱀 > 0;
		
		K = sc.nextInt();
		for(int i = 0; i < K; i++) {
			map[sc.nextInt()][sc.nextInt()] = -2; //사과 = -2
		}
		
		L = sc.nextInt();
		for(int i = 0; i < L; i++) {
			XC.put(sc.nextInt(), sc.next());
		}
		
		sc.close();
		
		cnt = 0;
		d = 3;
		play(1, 1, 1, 1);
		
		System.out.println(cnt);
	}
	
	static void play(int hx, int hy, int tx, int ty) {	
		cnt++;
		
		//hx: head x, hy: head y, tx: tail x, ty: tail y
		int chx = hx;
		int chy = hy;
		int ctx = tx;
		int cty = ty;
		
		int nhx = chx + dx[d];
		int nhy = chy + dy[d];
		int ntx = ctx;
		int nty = cty;
				
		//다음 진행할 곳이 벽이거나 자신의 몸이면 종료
		if(map[nhy][nhx] == -1 || map[nhy][nhx] > 0) {
			return;
		}
		
		//뱀의 이동 예
		// tail 1 2 3 head
		// tail 1 2 3 4 head
		// tail 0 2 3 4 5 head
		// tail 0 0 3 4 5 6 head
		
		boolean getApple = false;
		
		//사과를 먹은 경우
		if(map[nhy][nhx] == -2) {
			getApple = true;
		}
			
		//사과를 먹든 먹지 않든 머리는 앞으로 이동
		map[nhy][nhx] = map[chy][chx] + 1; //현재 head 보다 1 증가 시킨다. 꼬리 위치를 옮기게 될 경우 위치를 찾기위해 (이동할 꼬리의 위치는 항상 현재 꼬리의 +1일것)
		
		//사과를 먹지 않은 경우, 머리 앞으로, 꼬리는 줄어든다. (바로 앞의 꼬리 위치를 따라간다.)
		if(!getApple) {
			int ctv = map[cty][ctx]; //현재 꼬리 위치의 값
			map[cty][ctx] = 0;
			Pt ntpt = findNextTail(ctx, cty, ctv);
			//꼬리 위치 변경
			if(ntpt != null) {
				ntx = ntpt.x;
				nty = ntpt.y;
			}
		}
		
		//printMap();

		//다음 진행에 방향을 바꾼다. 
		if(XC.containsKey(cnt)) {
			changeDir(XC.get(cnt));
		}
		
		play(nhx, nhy, ntx, nty);
	}
	
	static void changeDir(String LD) {
		switch(d) {
		case 0:
			if(LD.equals("L")){
				d = 2;
			}
			else {
				d = 3;
			}
			break;
		case 1: 
			if(LD.equals("L")){
				d = 3;
			}
			else {
				d = 2;
			}
			break;
		case 2:
			if(LD.equals("L")){
				d = 1;
			}
			else {
				d = 0;
			}
			break;
		case 3:
			if(LD.equals("L")){
				d = 0;
			}
			else {
				d = 1;
			}
			break;
		}
	}

	static Pt findNextTail(int cx, int cy, int cv) {
		//현재 꼬리 위치에서 상하좌우를 보며 다음 꼬리 위치를 찾는다.
		//현재 꼬리 위치의 값 보다 1 큰 위치가 다음 꼬리 위치
		for(int i = 0; i < 4; i++) {
			int nx = cx + dx[i];
			int ny = cy + dy[i];
			
			if(map[ny][nx] == cv + 1) {
				return new Pt(nx, ny);
			}
		}
		
		return null;
	}
	
	static void printMap() {
		for(int i = 1; i < N - 1; i++) {
			for(int j = 1; j < N - 1; j++) {
				if(map[i][j] > 0) {
					System.out.print("1 ");
				}
				else if(map[i][j] == -2) {
					System.out.print("2 ");
				}
				else {
					System.out.print("0 ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	static class Pt{
		public int x, y;
		public Pt(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
