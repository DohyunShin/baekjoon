import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.StringTokenizer;

public class Main {
	static int N; // 2~100
	static int[][] map = new int[100][100];
	static int K; // 0~100
	static int L; // 1~100
	static ArrayList<Point> apples = new ArrayList<Main.Point>();
	static ArrayList<Turn> turns = new ArrayList<Main.Turn>();
	static int d = 3; // 상하좌우
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	static int res = 1;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		for(int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			apples.add(new Point(Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken())-1));
		}
		L = Integer.parseInt(br.readLine());
		for(int i = 0; i < L; i++) {
			st = new StringTokenizer(br.readLine());
			turns.add(new Turn(Integer.parseInt(st.nextToken()), st.nextToken().equals("L") ? 1 : 0));
		}
		br.close();
		
		solution();
		System.out.println(res);
	}
	
	public static void solution() {
		LinkedList<Point> snake = new LinkedList<Main.Point>();
		map[0][0] = 1;
		snake.add(new Point(0,0));
		
		Point next = new Point();
		
		int turnIdx = 0;
		Turn turn = turns.get(turnIdx);
		
		while(true) {
//			TEST
//			for(int i = 0; i < N; i++) {
//				for(int j = 0; j < N; j++) {
//					System.out.print(map[i][j] + " ");
//				}
//				System.out.println();
//			}
//			System.out.println();
			
			// 다음 칸 위치
			next.r = snake.getFirst().r + dr[d];
			next.c = snake.getFirst().c + dc[d];
			
			// 벽이나 자기자신에 닿으면 종료
			if(!(next.r >= 0 && next.r < N && next.c >= 0 && next.c < N) || map[next.r][next.c] == 1) break;
			
			// 머리를 다음 칸에 위치
			snake.addFirst(new Point(next.r, next.c));
			map[next.r][next.c] = 1;
			
			// 이동한 칸에 사과 확인
			if(apples.contains(snake.getFirst())) {
				// 사과 제거
				apples.remove(snake.getFirst()); 
			}
			else {
				// 꼬리 줄이기
				Point tail = snake.getLast();
				map[tail.r][tail.c] = 0;
				snake.removeLast();
			}
			
			// x초 끝, 방향 바꾸기
			if(res == turn.x) {
				turn(turn.c);
				turnIdx++;
				if(turns.size() > turnIdx)
					turn = turns.get(turnIdx);
			}
			
			// 초 증가
			res++;
		}
	}
	
	public static void turn(int dir) {
		switch(d) {
		case 0:
			if(dir == 1) d = 2;
			else d = 3;
			break;
		case 1:
			if(dir == 1) d = 3;
			else d = 2;
			break;
		case 2:
			if(dir == 1) d = 1;
			else d = 0;
			break;
		case 3:
			if(dir == 1) d = 0;
			else d = 1;
			break;
		}
	}
	
	static class Point{
		public int r, c;
		public Point() {
			
		}
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
		@Override
		public int hashCode() {
			return Objects.hash(r,c);
		}
		@Override
		public boolean equals(Object obj) {
			Point pt = (Point)obj;
			return pt.r == this.r && pt.c == this.c;
		}
	}
	
	static class Turn{
		public int x; // 1~10,000
		public int c; // 왼:0, 오:1
		public Turn(int x, int c) {
			this.x = x;
			this.c = c;
		}
	}
}