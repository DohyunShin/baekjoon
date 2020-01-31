import java.util.Scanner;

public class Main {
	static int R, C, T;
	static int map[][];
	static final int dr[] = {-1, 1, 0, 0};
	static final int dc[] = {0, 0, -1, 1};
	static int hr = -1; //공기청정기 상단 위치(인덱스 위치는 더 작다)
	static int lr = -1; //공기청정기 하단 위치(인덱스 위치는 더 크다)
	static int sum = 0;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		R = sc.nextInt();
		C = sc.nextInt();
		T = sc.nextInt();
		
		map = new int[R][C];
				
		for(int i = 0; i < R; i++) {
			for(int j = 0; j < C; j++) {
				int input = sc.nextInt();
				if(input == -1) {
					//어차피 순서대로 넣기 때문에 자동으로 작은 수가 hr에 들어간다.
					if(hr == -1) {
						hr = i;
					}
					else {
						lr = i;
					}
				}
				else {
					map[i][j] = input;
				}
				
				
			}
		}
		sc.close();
		solution();
		System.out.println(sum);
	}
	
	private static void printMap() {
		for(int r = 0; r < R; r++) {
			for(int c = 0; c < C; c++) {
				System.out.print(map[r][c] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private static void calculate() {
		for(int i = 0; i < R; i++) {
			for(int j = 0; j < C; j++) {
				sum += map[i][j];
			}
		}
	}
	private static void solution() {
		while(T-- > 0) {
			diffusion();
			//printMap();
			cleaningH();
			cleaningL();
			//printMap();
		}
		
		calculate();
	}
	
	private static void diffusion() {
		int temp[][] = new int[R][C]; //확산된 먼지의 양을 축적할 맵
		
		for(int r = 0; r < R; r++) {
			for(int c = 0; c < C; c++) {
				//미세먼지가 있는 경우 확산 시킨다.
				if(map[r][c] >= 1 && map[r][c] <= 1000) {
					int nr, nc;
					int cnt = 0; //확산되는 구간 수
					int d = map[r][c] / 5; //확산되는 먼지양
					
					for(int dir = 0; dir < 4; dir++) {
						nr = r + dr[dir];
						nc = c + dc[dir];
						
						if(!(nr >= 0 && nr < R && nc >= 0 && nc < C) || (nr == hr && nc == 0) || (nr == lr && nc == 0)) {
							continue;
						}
						
						cnt++;
						temp[nr][nc] += d;
					}
					
					//확산된 먼지양 만큼 빼준다.
					map[r][c] -= (d*cnt);
				}
			}
		}
		
		//확산된 먼지와 원래 먼지를 합친다.
		for(int r = 0; r < R; r++) {
			for(int c = 0; c < C; c++) {
				map[r][c] += temp[r][c];
			}
		}
	}
	
	private static void cleaningH() {
		int R_RANGE_START = 0;
		int R_RANGE_STOP = hr;
		
		//row+, col+, row-, col-
		int tdr[] = {1, 0, -1, 0};
		int tdc[] = {0, 1, 0, -1};
		int tdir = 0;
		
		int cr = 0;
		int cc = 0;
		int pd = map[cr][cc]; //이전 위치의 먼지, 처음 시작 위치의 먼지를 저장하고 시작
		
		while(tdir < 4) {
			int nr = cr + tdr[tdir];
			int nc = cc + tdc[tdir];
			
			if(!(nr >= R_RANGE_START && nr <= R_RANGE_STOP && nc >= 0 && nc < C)) {
				tdir++;
				continue;
			}
			
			//위치가 공기청정기 인 경우
			if(nr == hr && nc == 0) {
				pd = 0; //이전 위치의 먼지를 0으로 바꾼다.
			}
			
			//먼지를 한칸씩 이동
			int temp = map[nr][nc];
			map[nr][nc] = pd;
			pd = temp;
			
			cr = nr;
			cc = nc;
		}
	}
	
	private static void cleaningL() {
		int R_RANGE_START = lr;
		int R_RANGE_STOP = R - 1;
		
		//col+, row+, col-, row-
		int tdr[] = {0, 1, 0, -1};
		int tdc[] = {1, 0, -1, 0};
		int tdir = 0;
		
		int cr = lr;
		int cc = 0;
		int pd = map[cr][cc]; //이전 위치의 먼지, 처음 시작 위치의 먼지를 저장하고 시작
		
		while(tdir < 4) {
			int nr = cr + tdr[tdir];
			int nc = cc + tdc[tdir];
			
			if(!(nr >= R_RANGE_START && nr <= R_RANGE_STOP && nc >= 0 && nc < C)) {
				tdir++;
				continue;
			}
			
			//위치가 공기청정기 인 경우
			if(nr == lr && nc == 0) {
				pd = 0; //이전 위치의 먼지를 0으로 바꾼다.
			}
			
			//먼지를 한칸씩 이동
			int temp = map[nr][nc];
			map[nr][nc] = pd;
			pd = temp;
			
			cr = nr;
			cc = nc;
		}
	}
}
