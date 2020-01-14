import java.util.Scanner;

public class Main {
	static int N, M;
	static int map[][];
	static int cctvType[] = new int[8];
	static int cctvX[] = new int[8];
	static int cctvY[] = new int[8];
	static int cctvCnt = 0;
	static int min = 999999;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt() + 2;
		M = sc.nextInt() + 2;
		
		map = new int[N][M];
		for(int i = 1; i < N - 1; i++) {
			for(int j = 1; j < M - 1; j++) {
				int input = sc.nextInt();
				if(input > 0 && input <= 5) {
					cctvType[cctvCnt] = input;
					cctvX[cctvCnt] = j;
					cctvY[cctvCnt] = i;
					cctvCnt++;
				}
				map[i][j] = input;
			}
		}
		
		//패딩처리
		for(int i = 0; i < N; i++) {
			if(i == 0 || i == N - 1) {
				for(int j = 0; j < M; j++) {
					map[i][j] = 6;
				}
			}
			else {
				map[i][0] = 6;
				map[i][M-1] = 6;
			}
		}
		sc.close();
		
		solution();
		System.out.println(min);
	}
	
	private static void solution() {
		int copyMap[][] = map.clone();
		for(int i = 0; i < map.length; i++) {
			copyMap[i] = map[i].clone();
		}
		
		if(cctvCnt == 0) {
			calculate(copyMap);
			return;
		}
		
		dp(copyMap, 0);
	}
	
	private static void dp(int dpMap[][], int cctvNum) {
		if(cctvNum >= cctvCnt) {
			//printMap(dpMap);
			calculate(dpMap);
			return;
		}
		
		for(int i = 0; i < 4; i++) {
			
			int copyMap[][] = dpMap.clone();
			for(int j = 0; j < dpMap.length; j++) {
				copyMap[j] = dpMap[j].clone();
			}
			
			watch(copyMap, cctvNum, i);
			dp(copyMap, cctvNum + 1);
		}
	}
	
	private static void watch(int watchMap[][], int cctvNum, int dir) {
		int type = cctvType[cctvNum];
		int x = cctvX[cctvNum];
		int y = cctvY[cctvNum];
		int nx, ny;
		
		if(type == 1) {
			int dx = 0;
			int dy = 0;
			
			if(dir == 0) {
				dx = 0;
				dy = -1;
			}
			else if(dir == 1) {
				dx = 1;
				dy = 0;
			}
			else if(dir == 2) {
				dx = 0;
				dy = 1;
			}
			else if(dir == 3) {
				dx = -1;
				dy = 0;
			}
			
			nx = x;
			ny = y;
			
			while(true) {
				nx += dx;
				ny += dy;
				
				if(watchMap[ny][nx] == 0) {
					watchMap[ny][nx] = 7; //# 대신
				}
				else if(watchMap[ny][nx] == 7) {
					continue;
				}
				else if(watchMap[ny][nx] == 6) {
					break;
				}
				else {
					//다른 카메라가 있는 경우 지나간다.
				}
			}
		}
		else if(type == 2) {
			int dx = 0;
			int dy = 0;
			
			if(dir == 0 || dir == 2) {
				//위 -> 아래 순서
				for(int i = 0; i < 2; i++) {
					dy = i == 0? 1 : -1;
					dx = 0;
					
					nx = x;
					ny = y;
					
					while(true) {
						nx += dx;
						ny += dy;
						
						if(watchMap[ny][nx] == 0) {
							watchMap[ny][nx] = 7; //# 대신
						}
						else if(watchMap[ny][nx] == 7) {
							continue;
						}
						else if(watchMap[ny][nx] == 6) {
							break;
						}
						else {
							//다른 카메라가 있는 경우 지나간다.
						}
					}
				}
			}
			else if(dir == 1 || dir == 3) {
				//오른쪽 -> 왼쪽 순서
				for(int i = 0; i < 2; i++) {
					dx = i == 0? 1 : -1;
					dy = 0;
					
					nx = x;
					ny = y;
					
					while(true) {
						nx += dx;
						ny += dy;
						
						if(watchMap[ny][nx] == 0) {
							watchMap[ny][nx] = 7; //# 대신
						}
						else if(watchMap[ny][nx] == 7) {
							continue;
						}
						else if(watchMap[ny][nx] == 6) {
							break;
						}
						else {
							//다른 카메라가 있는 경우 지나간다.
						}
					}
				}
			}
		}
		else if(type == 3) {
			int dx[] = new int[2];
			int dy[] = new int[2];
			
			if(dir == 0) {
				dx[0] = 0;
				dy[0] = -1;
				dx[1] = 1;
				dy[1] = 0;
			}
			else if(dir == 1) {
				dx[0] = 1;
				dy[0] = 0;
				dx[1] = 0;
				dy[1] = 1;
			}
			else if(dir == 2) {
				dx[0] = 0;
				dy[0] = 1;
				dx[1] = -1;
				dy[1] = 0;
			}
			else if(dir == 3) {
				dx[0] = -1;
				dy[0] = 0;
				dx[1] = 0;
				dy[1] = -1;
			}
			
			for(int i = 0; i < 2; i++) {
				nx = x;
				ny = y;
				
				while(true) {
					nx += dx[i];
					ny += dy[i];
					
					if(watchMap[ny][nx] == 0) {
						watchMap[ny][nx] = 7; //# 대신
					}
					else if(watchMap[ny][nx] == 7) {
						continue;
					}
					else if(watchMap[ny][nx] == 6) {
						break;
					}
					else {
						//다른 카메라가 있는 경우 지나간다.
					}
				}
			}
		}
		else if(type == 4) {
			int dx[] = new int[3];
			int dy[] = new int[3];
			
			if(dir == 0) {
				dx[0] = 0;
				dy[0] = -1;
				dx[1] = 1;
				dy[1] = 0;
				dx[2] = -1;
				dy[2] = 0;
			}
			else if(dir == 1) {
				dx[0] = 1;
				dy[0] = 0;
				dx[1] = 0;
				dy[1] = 1;
				dx[2] = 0;
				dy[2] = -1;
			}
			else if(dir == 2) {
				dx[0] = 0;
				dy[0] = 1;
				dx[1] = -1;
				dy[1] = 0;
				dx[2] = 1;
				dy[2] = 0;
			}
			else if(dir == 3) {
				dx[0] = -1;
				dy[0] = 0;
				dx[1] = 0;
				dy[1] = 1;
				dx[2] = 0;
				dy[2] = -1;
			}
			
			for(int i = 0; i < 3; i++) {
				nx = x;
				ny = y;
				
				while(true) {
					nx += dx[i];
					ny += dy[i];
					
					if(watchMap[ny][nx] == 0) {
						watchMap[ny][nx] = 7; //# 대신
					}
					else if(watchMap[ny][nx] == 7) {
						continue;
					}
					else if(watchMap[ny][nx] == 6) {
						break;
					}
					else {
						//다른 카메라가 있는 경우 지나간다.
					}
				}
			}
		}
		else if(type == 5) {
			int dx[] = {0, 0, -1, 1};
			int dy[] = {-1, 1, 0, 0};
			
			for(int i = 0; i < 4; i++) {
				nx = x;
				ny = y;
				
				while(true) {
					nx += dx[i];
					ny += dy[i];
					
					if(watchMap[ny][nx] == 0) {
						watchMap[ny][nx] = 7; //# 대신
					}
					else if(watchMap[ny][nx] == 7) {
						continue;
					}
					else if(watchMap[ny][nx] == 6) {
						break;
					}
					else {
						//다른 카메라가 있는 경우 지나간다.
					}
				}
			}
		}
	}
	
	private static void calculate(int calMap[][]) {
		int cnt = 0;
		for(int i = 1; i < N - 1; i++) {
			for(int j = 1; j < M - 1; j++) {
				if(calMap[i][j] == 0) {
					cnt++;
				}
			}
		}
		
		if(cnt < min) {
			//printMap(calMap);
			min = cnt;
		}
	}
	private static void printMap(int printMap[][]) {
		for(int i = 1; i < N - 1; i++) {
			for(int j = 1; j < M - 1; j++) {
				System.out.print(printMap[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
