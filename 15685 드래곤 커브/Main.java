import java.util.Scanner;

public class Main {
	static int N;
	static int x[];
	static int y[];
	static int d[];
	static int g[];
	//0: 오른쪽, 1: 위쪽, 2: 왼쪽, 3: 아래쪽
	//90도씩 회전할때 흐름 0->1->2->3
	static int dx[] = {1, 0, -1, 0};
	static int dy[] = {0, -1, 0, 1};
	static boolean map[][];
	static int cnt = 0;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		x = new int[N];
		y = new int[N];
		d = new int[N];
		g = new int[N];
		
		for(int i = 0; i < N; i++) {
			x[i] = sc.nextInt();
			y[i] = sc.nextInt();
			d[i] = sc.nextInt();
			g[i] = sc.nextInt();
		}
		sc.close();
				
		map = new boolean[101][101]; //0~100
		solution();
		System.out.println(cnt);
	}
	
	private static void solution() {
		for(int i = 0; i < N; i++) {
			//printMap();
			extend(i);
		}
		
		//사각형 개수 세기
		for(int i = 0; i < 101; i++) {
			for(int j = 0; j < 101; j++) {
				if(map[i][j] == true) {
					if(i + 1 <= 100 && j + 1 <= 100) {
						if(map[i][j + 1] == true && map[i + 1][j + 1] == true && map[i + 1][j] == true) {
							cnt++;
						}
					}
				}
			}
		}
	}
	private static void printMap() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				System.out.print(map[i][j]? "1 ": "0 ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	//한개의 드래곤 커브를 세대 만큼 펼친다.
	private static void extend(int num) {
		int curX = x[num];
		int curY = y[num];
		int curD = d[num];
		int goalG = g[num];
		
		//이전 세대들의 방향을 저장해둔다.
		int preDs[] = new int[1024]; //최대 10세대 까지 확장되면 선분은 2의 10승인  1024개
		int preCnt = 0;
		
		//0세대 만들기
		
		int tailX = curX + dx[curD];
		int tailY = curY + dy[curD];
		
		map[curY][curX] = true;
		map[tailY][tailX] = true;
		
		preDs[preCnt++] = curD;

		curX = tailX;
		curY = tailY;
		//printMap();
		
		//1세대 이후 만들기
		for(int i = 1; i <= goalG; i++) {
			int tempCnt = preCnt;
			
			//거꾸로
			for(int j = tempCnt - 1; j >= 0; j--) {
				//확장
				curD = preDs[j] + 1 > 3 ? 0 : preDs[j] + 1;
				
				tailX = curX + dx[curD];
				tailY = curY + dy[curD];
				map[tailY][tailX] = true;
				
				preDs[preCnt++] = curD;
				
				curX = tailX;
				curY = tailY;
			}
			
			//printMap();
		}
	}
}
