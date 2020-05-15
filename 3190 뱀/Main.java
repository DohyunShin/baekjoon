import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static class Point{
		public int r,c;
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	static class Direction{
		public int X;
		public String C; //L:왼, D:오
		public Direction(int X, String C) {
			this.X = X;
			this.C = C;
		}
	}
	
	static int N; //2~100
	static int K; //0~100 사과개수
	static int L; //0~100 방향정보
	static int[][] map = new int[100][100];
	static LinkedList<Point> snake = new LinkedList<Point>();
	static Queue<Direction> directions = new LinkedList<Direction>();
	static int curD = 0; //0:우, 1:하, 2:좌, 3:상
	static int[] dr = {0,1,0,-1};
	static int[] dc = {1,0,-1,0};
	static int time = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		for(int i=0; i<K; i++) {
			st = new StringTokenizer(br.readLine());
			map[Integer.parseInt(st.nextToken())-1][Integer.parseInt(st.nextToken())-1] = 2; //사과
		}
		L = Integer.parseInt(br.readLine());
		for(int i=0; i<L; i++) {
			st = new StringTokenizer(br.readLine());
			directions.add(new Direction(Integer.parseInt(st.nextToken()), st.nextToken()));
		}
		snake.add(new Point(0,0));
		map[0][0] = 1; //뱀
		br.close();
		
		solution();
		System.out.println(time);
	}
	
	private static void solution() {
		Direction direction = null;
		
		int nr, nc;
		while(true) {
			time++;

			if(direction == null && !directions.isEmpty()) {
				direction = directions.poll();
			}
			
			//머리 늘리기
			Point head = snake.getFirst();
			nr = head.r+dr[curD];
			nc = head.c+dc[curD];
			
			snake.addFirst(new Point(nr, nc));
			head = snake.getFirst();
			//map[head.r][head.c] = 1; //여기서 머리 표시하면 사과를 검사할 수 없다.
			
			//머리가 벽 또는 몸에 닿았는지 확인
			if(!(head.r>=0&&head.r<N&&head.c>=0&&head.c<N) || map[head.r][head.c] == 1) return;
			
			//머리 위치에 사과 있는지 확인
			if(map[head.r][head.c] == 2) {
				
			}
			else {
				//사과 없으면 꼬리 움직이기
				Point tail = snake.getLast();
				map[tail.r][tail.c] = 0;
				snake.removeLast();
			}
			map[head.r][head.c] = 1; //사과 검사 끝나고 머리 표시
			
			//방향 바꾸기
			if(direction != null) {
				if(direction.X == time) {
					if(direction.C.equals("L")) {
						curD--;
						if(curD == -1) {
							curD = 3;
						}
					}
					else {
						curD++;
						if(curD == 4) {
							curD = 0;
						}
					}
					
					direction = null; //다음 회차에서 다음 방향 정보를 가져오기 위해
				}
			}
		}
	}
	
	private static void printMap() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}