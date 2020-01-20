import java.util.Scanner;

public class Main {
	static int N, M;
	static int map[][];
	static int cCnt = 0; //치킨집 개수
	static int min = 99999;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		map = new int[N][N];
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				int input = sc.nextInt();
				
				if(input == 2) {
					cCnt++;
				}
				
				map[i][j] = input;
			}
		}
		sc.close();
		
		solution();
		System.out.println(min);
	}
	
	private static void solution() {
		dfs(0, 0, 0);
	}
	
	//한개씩 폐업시키면서 최소 도시 치킨 거리를 찾는다.
	private static void dfs(int delCnt, int x, int y) {
		//cCnt - M = 폐업하는 치킨 집 개수
		if(delCnt == cCnt - M) {
			//최소 도시 치킨 거리 구하기
			int sum = 0;
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					//집일 때
					if(map[i][j] == 1) {
						//각 치킨집까지의 거리를 비교하여 최소를 찾는다.
						int tmp_min = 99999;
						for(int a = 0; a < N; a++) {
							for(int b = 0; b < N; b++) {
								if(map[a][b] == 2) {
									int hX = j;
									int hY = i;
									int cX = b;
									int cY = a;
									
									int d = Math.abs(hX - cX) + Math.abs(hY - cY);
									if(tmp_min > d) {
										tmp_min = d;
									}
								}
							}
						}
						//최소를 더한다.
						sum += tmp_min;
					}
				}
			}
			
			if(min > sum) {
				min = sum;
			}
			
			return;
		}
		
		//주의) 매개변수로 넘어온 y, x 로 시작하지 않으면 시간 초과!!
		for(int i = y; i < N; i++) {
			for(int j = x; j < N; j++) {
				//치킨집인 경우 
				if(map[i][j] == 2) {
					//폐업시켜본다.
					map[i][j] = 0;
					
					dfs(delCnt + 1, j, i);
					
					//복구시킨다.
					map[i][j] = 2;
				}
			}
			x = 0; //주의) 항상 주의!!
		}
	}
	
	private static void printMap() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
