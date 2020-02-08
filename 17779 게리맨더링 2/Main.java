import java.util.Scanner;

public class Main {

	static int N;
	static int map[][];
	static int sepMap[][]; //선거구를 구분지를 맵
	static int min = 99999;
	static int dr[] = {-1,1,0,0};
	static int dc[] = {0,0,-1,1};
 
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		map = new int[N][N];
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				map[i][j] = sc.nextInt();
			}
		}
		sc.close();
		
		solution();
		System.out.println(min);
	}
	
	public static void solution() {
		//기준점과 경계의 길이 정하기
		//경계 길이가 모두 1일 때의 최대, 최소 범위를 생각
		for(int r = 0; r <= N-3; r++) {
			for(int c = 1; c <= N-2; c++) {
				for(int d1 = 1; r+d1 <= N-2 && c-d1 >= 0; d1++) {
					for(int d2 = 1; r+d2 <= N - 2 && c+d2 <= N-1; d2++) {
						if(!(r+d1+d2 <= N-1 && c-d1+d2 <= N-2)) {
							continue;
						}
						
						makeSepMap(r, c, d1, d2);
						calculate();
					}
				}
			}
		}
		
	}
	
	//각 구역의 인원수를 계산하여 가장 적은 서거구 수와 가장 많은 선거구 수의 차를 구한다.
	private static void calculate() {
		int secCnt[] = new int[5];
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				switch(sepMap[i][j]) {
				case 1:
					secCnt[0] += map[i][j];
					break;
				case 2:
					secCnt[1] += map[i][j];
					break;
				case 3:
					secCnt[2] += map[i][j];
					break;
				case 4:
					secCnt[3] += map[i][j];
					break;
				case 5:
				case 0:
					secCnt[4] += map[i][j];
					break;
				}
			}
		}
		
		int tempMin = 9999999;
		int tempMax = -9999999;
		for(int i = 0; i < 5; i++) {
			if(tempMin > secCnt[i]) {
				tempMin = secCnt[i];
			}
			
			if(tempMax < secCnt[i]) {
				tempMax = secCnt[i];
			}
		}
		
		int diff = tempMax - tempMin;
		
		if(min > diff) {
			min = diff;
		}
	}
	
	private static void printSepMap() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				System.out.print(sepMap[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void makeSepMap(int r, int c, int d1, int d2) {
		sepMap = new int[N][N];
		
		int cr = r;
		int cc = c;
		int nr, nc;
		int dr = 1;
		int dc = -1;
		
		//경계선 그리기
		while(true) {
			sepMap[cr][cc] = 5;
			
			nr = cr + dr;
			nc = cc + dc;
			
			//각 경계선을 만나면 방향을 바꾼다.
			if(nr == r+d1 && nc == c-d1) {
				dr = 1;
				dc = 1;
			}
			else if(nr == r+d1+d2 && nc == c-d1+d2) {
				dr = -1;
				dc = 1;
			}
			else if(nr == r+d2 && nc == c+d2) {
				dr = -1;
				dc = -1;
			}
			
			cr = nr;
			cc = nc;
			
			//처음으로 돌아오면 중단
			if(cr == r && cc == c) {
				break;
			}
		}
		
		//각 모서리의 끝 부분은 구역이 정해져있다.
		//이를 이용해서 구역을 만든다.
		makeSepMapSec(1, 0, 0, r+d1-1, c);
		makeSepMapSec(2, 0, N-1, r+d2, c+1);
		makeSepMapSec(3, N-1, 0, r+d1, c-d1+d2-1);
		makeSepMapSec(4, N-1, N-1, r+d2+1, c-d1+d2);
		
		//구역을 모두 배분하고 남은 수 중 0은 5구역으로 생각한다.
		//printSepMap();
	}
	
	static void makeSepMapSec(int sec, int cr, int cc, int br, int bc) {
		if(sepMap[cr][cc] != 0) {
			return;
		}
		else {
			sepMap[cr][cc] = sec;
		}
		
		int nr, nc;
		
		for(int i = 0; i < 4; i++) {
			nr = cr + dr[i];
			nc = cc + dc[i];
			
			if(!(nr >= 0 && nr < N && nc >= 0 && nc < N)) {
				continue;
			}
			
			if(sec == 1) {
				if(!(nr <= br && nc <= bc)) {
					continue;
				}
			}
			else if(sec == 2) {
				if(!(nr <= br && nc >= bc)) {
					continue;
				}
			}
			else if(sec == 3) {
				if(!(nr >= br && nc <= bc)) {
					continue;
				}
			}
			else if(sec == 4) {
				if(!(nr >= br && nc >= bc)) {
					continue;
				}
			}
			
			makeSepMapSec(sec, nr, nc, br, bc);
		}
	}
}
