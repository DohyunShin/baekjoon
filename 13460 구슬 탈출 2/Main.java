import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, M; // 3~10
	static int[][] map = new int[10][10];
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	static Point red;
	static Point blue;
	static int res = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		for(int i = 0; i < N; i++) {
			String line = br.readLine();
			for(int j = 0; j < M; j++) {
				char tok = line.charAt(j);
				
				if(tok == '#') {
					map[i][j] = 1;
				}
				else if(tok == 'O') {
					map[i][j] = 2;
				}
				else if(tok == 'R') {
					red = new Point(i,j);
				}
				else if(tok == 'B') {
					blue = new Point(i,j);
				}
			}
		}
		br.close();
		
		solution();
		System.out.println(res == Integer.MAX_VALUE ? -1 : res);
	}
	
	public static void solution() {
		recursive(1);
	}
	
	public static void recursive(int cnt) {
		if(cnt > 10) {
			return;
		}
		
		Point copy_red = new Point(red.r, red.c);
		Point copy_blue = new Point(blue.r, blue.c);
				
		for(int d = 0; d < 4; d++) {
			red.r = copy_red.r;
			red.c = copy_red.c;
			blue.r = copy_blue.r;
			blue.c = copy_blue.c;
			
			// red
			int nr, nc;
			while(true) {
				nr = red.r + dr[d];
				nc = red.c + dc[d];
				
				if(!(nr >= 0 && nr < N && nc >= 0 && nc < M) || map[nr][nc] == 1) break; // 맵 밖이거나 벽인 경우
				
				red.r = nr;
				red.c = nc;
				
				if(map[red.r][red.c] == 2) break; // 구멍인 경우
			}
			
			// blue
			while(true) {
				nr = blue.r + dr[d];
				nc = blue.c + dc[d];
				
				if(!(nr >= 0 && nr < N && nc >= 0 && nc < M) || map[nr][nc] == 1) break; // 맵 밖이거나 벽인 경우

				blue.r = nr;
				blue.c = nc;
				
				if(map[blue.r][blue.c] == 2) break; // 구멍인 경우
			}
			
			// check
			if(map[blue.r][blue.c] == 2) continue;
			if(red.r == copy_red.r && red.c == copy_red.c && blue.r == copy_blue.r && blue.c == copy_blue.c) continue;
			if(map[red.r][red.c] == 2) {
				if(res > cnt) res = cnt;
				return;
			}
			if(red.r == blue.r && red.c == blue.c) {
				if(d == 0) {
					if(copy_red.r < copy_blue.r) {
						blue.r++;
					}
					else {
						red.r++;
					}
				}
				else if(d == 1) {
					if(copy_red.r < copy_blue.r) {
						red.r--;
					}
					else {
						blue.r--;
					}
				}
				else if(d == 2) {
					if(copy_red.c < copy_blue.c) {
						blue.c++;
					}
					else {
						red.c++;
					}
				}
				else if(d == 3) {
					if(copy_red.c < copy_blue.c) {
						red.c--;
					}
					else {
						blue.c--;
					}
				}
			}
			
			recursive(cnt+1);
		}		
	}
	
	public static void printMap() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(i == red.r && j == red.c) {
					System.out.print("4 ");
				}
				else if(i == blue.r && j == blue.c) {
					System.out.print("5 ");
				}
				else System.out.print(map[i][j] + " ");
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
}