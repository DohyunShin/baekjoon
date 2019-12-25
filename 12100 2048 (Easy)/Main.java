import java.util.Scanner;

public class Main {

	static int n;
	static int map[][];
	final static int dx[] = {0, 0, -1, 1};
	final static int dy[] = {-1, 1, 0, 0};
	static int max;
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt() + 2; //벽 패딩 처리
		map = new int[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				map[i][j] = -1; //패딩
			}
		}
		
		for(int i = 1; i < n - 1; i++) {
			for(int j = 1; j < n -1 ; j++) {
				map[i][j] = sc.nextInt();
			}
		}
		sc.close();
		
		recur(map, 0);
		System.out.println(max);
	}
	
	private static void recur(int map[][], int cnt) {
		//5회를 넘어가거면 종료
		if(cnt == 5) {
			return;
		}
		
		cnt++;
		
		for(int i = 0; i < 4; i++) {
			int copy[][] = new int[map.length][map.length];
			for(int j = 0; j < map.length; j++) {
				copy[j] = map[j].clone();	
			}
			
			
			move(copy, i);
			//
			//System.out.println("dir: " + i + ", cnt: " + cnt);
			//printMap(copy);
			//
			int findMax = findMax(copy);
			if(max < findMax) {
				max = findMax;
			}
			
			recur(copy, cnt);	
		}
	}
	
	private static void moveEx(int map[][], boolean merged[][], int cy, int cx, int d) {
		if(map[cy][cx] == 0) {
			return;
		}
		
		int nx, ny;
		
		//자리 이동
		while(true) {
			nx = cx + dx[d];
			ny = cy + dy[d];
			
			//벽을 만나거나 다른 수를 만날 때 멈춘다.
			if(map[ny][nx] == -1 || map[ny][nx] != 0) {
				break;
			}
			
			//빈자리 이동
			if(map[ny][nx] == 0) {
				map[ny][nx] = map[cy][cx];
				map[cy][cx] = 0;
				cy = ny;
				cx = nx;
			}
		}
		
		//앞에 같은 수 있으면 앞으로 합치기
		if(map[ny][nx] == map[cy][cx] && merged[ny][nx] == false) {
			map[cy][cx] = 0;
			map[ny][nx] *= 2;
			merged[ny][nx] = true;
		}
	}

	private static void move(int map[][], int d) {
		//움직이는 방향쪽으로의 가장 앞에 있는 숫자 부터 움직인다.
		//예) 상: 맨 윗 줄 부터 움직인다. 좌: 맨 왼쪽 부터 움직인다.
		
		boolean merged[][] = new boolean[map.length][map.length];//이미 합쳐진 수 체크
		
		switch(d) {
		case 0:
			//상
			for(int i = 1; i < n - 1; i++) {
				for(int j = 1; j < n - 1; j++) {
					moveEx(map, merged, i, j, d);
				}
			}
			break;
		case 1:
			//하
			for(int i = n - 2; i >= 1; i--) {
				for(int j = 1; j < n - 1; j++) {
					moveEx(map, merged, i, j, d);
				}
			}
			break;
		case 2:
			//좌
			for(int j = 1; j < n - 1; j++) {
				for(int i = 1; i < n - 1; i++) {
					moveEx(map, merged, i, j, d);
				}
			}
			break;
		case 3:
			//우
			for(int j = n - 2; j >= 1; j--) {
				for(int i = 1; i < n - 1; i++) {
					moveEx(map, merged, i, j, d);
				}
			}

			break;
		}
	}
	
	private static int findMax(int map[][]) {
		int max = 0;
		
		for(int i = 1; i < n - 1; i++) {
			for(int j = 1; j < n - 1; j++) {
				if(max < map[i][j]) {
					max = map[i][j];
				}
			}
		}
		
		return max;
	}
	
	private static void printMap(int map[][]) {
		for(int i = 1; i < n - 1; i++) {
			for(int j = 1; j < n - 1; j++) {
				System.out.print(map[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}

}
