import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int N; // 2~100
	static int K; // 0~100
	static int L; // 1~100
	static int[][] map = new int[100][100];
	static int dir = 3;
	static final int[] dr = {-1,1,0,0};
	static final int[] dc = {0,0,-1,1};
	static Queue<RotationInfo> rotationInfos = new LinkedList<Main.RotationInfo>();
	static LinkedList<Point> snake = new LinkedList<Main.Point>();
	static int time = 1;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());
		for(int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			map[r][c] = 2; // apple
		}
		L = Integer.parseInt(br.readLine());
		for(int i = 0; i < L; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int c = st.nextToken().equals("L") ? 0 : 1; // 0: 왼쪽, 1: 오른쪽
			rotationInfos.add(new RotationInfo(x, c));
		}
		map[0][0] = 1; //snake
		snake.addFirst(new Point(0,0));
		br.close();
		
		solution();
		System.out.println(time);
	}
	
	public static void solution() {
		RotationInfo rotationInfo = rotationInfos.poll();
		while(true) {
			// 이동
			// 먼저 뱀은 몸길이를 늘려 머리를 다음칸에 위치시킨다.
			Point head = snake.getFirst();
			Point next = new Point(head.r+dr[dir], head.c+dc[dir]);
			
			// 머리가 벽이나 자기 자신의 몸에 부딪히면 종료
			if(!(next.r >= 0 && next.r < N && next.c >= 0 && next.c < N)) break;
			if(map[next.r][next.c] == 1) break;
			
			// 만약 이동한 칸에 사과가 없다면, 몸길이를 줄여서 꼬리가 위치한 칸을 비워준다. 즉, 몸길이는 변하지 않는다.
			if(map[next.r][next.c] != 2) {
				// 꼬리 이동
				Point tail = snake.getLast();
				map[tail.r][tail.c] = 0;
				snake.removeLast();
			}
			// 머리 이동
			snake.addFirst(next);
			map[next.r][next.c] = 1;
			
			// 종료, 회전
			if(rotationInfo != null && rotationInfo.x == time) {
				switch(dir) {
				case 0:
					if(rotationInfo.c == 0) dir = 2;
					else dir = 3;
					break;
				case 1:
					if(rotationInfo.c == 0) dir = 3;
					else dir = 2;
					break;
				case 2:
					if(rotationInfo.c == 0) dir = 1;
					else dir = 0;
					break;
				case 3:
					if(rotationInfo.c == 0) dir = 0;
					else dir = 1;
					break;
				}
				
				// 다음 회전 정보 준비
				if(!rotationInfos.isEmpty()) rotationInfo = rotationInfos.poll();
				else rotationInfo = null;
			}
//			printMap();
			time++;
		}
	}
	
	public static void printMap() {
		for(int r = 0; r < N; r++) {
			for(int c = 0; c < N; c++) {
				System.out.print(map[r][c] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	static class Point{
		public int r, c;
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	static class RotationInfo{
		public int x, c;
		public RotationInfo(int x, int c) {
			this.x = x;
			this.c = c;
		}
	}
}