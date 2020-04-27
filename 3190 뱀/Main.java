import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
	static class Point {
		int r, c;
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
		
		public Point() {}
	}
	
	static int N; //2~100
	static int K; //0~100 사과 개수
	static boolean[][] apples = new boolean[100][100];
	static int L; //1~100 방향 변환 횟수
	//초 1~10,000 이하
	//방향, L:왼쪽, D:오른쪽 (90방향 회전)
	static HashMap<Integer, Character> moveInfoMap = new HashMap<Integer, Character>(); 
	static int d = 0; //현재 이동 방향
	//우하좌상
	static int[] dr = {0,1,0,-1};
	static int[] dc = {1,0,-1,0};
	static int counter = 1;
	static LinkedList<Point> snake = new LinkedList<Main.Point>();
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		for(int i=0;i<K;i++) {
			st = new StringTokenizer(br.readLine());
			apples[Integer.parseInt(st.nextToken())-1][Integer.parseInt(st.nextToken())-1] = true;
		}
		snake.addFirst(new Point(0,0));
		L = Integer.parseInt(br.readLine());
		for(int i=0;i<L;i++) {
			st = new StringTokenizer(br.readLine());
			moveInfoMap.put(Integer.parseInt(st.nextToken()), st.nextToken().charAt(0));
		}
		br.close();
		
		solution();
		System.out.println(counter);
	}

	private static void solution() {
		Point head = null;
		
		while(true) {
			//뱀의 다음 이동 위치 구하기
			Point next = new Point();
			head = snake.getFirst();
			next.r = head.r + dr[d];
			next.c = head.c + dc[d];
			
			//벽에 닿는 경우 게임 종료(맵의 밖)
			if(!(next.r >= 0 && next.r < N && next.c >= 0 && next.c < N)) break;
		
			//뱀의 머리가 몸통에 닿는 경우 게임 종료
			if(isBody(next)) break;
			
			//뱀 머리 이동
			snake.addFirst(next);
			if(!eatApple()) {
				//뱀이 사과를 먹지 않았다면 꼬리 이동
				snake.removeLast();
			}

			//이동방향 변경
			//현재 시간에 변경할 방향 정보가 있는지 확인, 있다면 현재 방향 정보를 바꿔준다.
			if(moveInfoMap.containsKey(counter)) {
				switch(moveInfoMap.get(counter)) {
				case 'L':
					d--;
					if(d == -1) d = 3;
					break;
				case 'D':
					d++;
					if(d == 4) d = 0;
					break;
				}
				moveInfoMap.remove(counter);
			}
			
			//printMap();
			counter++;
		}
	}
	
	//뱀이 현재 사과를 먹었는지 확인하는 함수, 먹었으면 true 반환하고 apples 맵에서 해당 위치 사과 제거
	private static boolean eatApple() {
		Point head = snake.getFirst();
		if(apples[head.r][head.c]) {
			apples[head.r][head.c] = false;
			return true;
		}
		else return false;
	}
	
	//현재 뱀의 머리 위치가 몸통에 닿았는지 확인
	private static boolean isBody(Point next) {
		Point body = null;
		
		for(int i=1; i<snake.size(); i++) {
			body = snake.get(i);
			if(body.r == next.r && body.c == next.c) {
				return true;
			}
		}
		
		return false;
	}
	
	private static void printMap() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				boolean isSnake = false;
				for(int z=0; z<snake.size(); z++) {
					if(snake.get(z).r == i && snake.get(z).c == j) {
						System.out.print("* ");
						isSnake = true;
						break;
					}
				}
				
				if(!isSnake)
					System.out.print("0 ");
				
			}
			
			System.out.println();
		}
		System.out.println();
	}
}
