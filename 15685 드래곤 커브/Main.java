import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static class Dragon{
		public int ptCnt = 0; //좌표 개수
		//0세대: 2개, 1세대: 3개, 2세대:5개, 3세대: 9개, ..., n세대: 2^n+1개
		//최대 10세대: 1024+1개
		public int[] Rs = new int[1025];
		public int[] Cs = new int[1025];
		public int[] Ds = new int[1025];
		public int g; //1~10
		public Dragon() {}
	}

	static int N; //1~20
	static boolean[][] map = new boolean[101][101];
	static Dragon[] dragons = new Dragon[20];
	static int cnt = 0;
	//우상좌하
	static int[] dr= {0,-1,0,1};
	static int[] dc= {1,0,-1,0};
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		int x,y,d,g;
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			d = Integer.parseInt(st.nextToken());
			g = Integer.parseInt(st.nextToken());
			
			Dragon dragon = new Dragon();
			dragon.g = g;
			extendDragon(dragon, y, x, d);
			extendDragon(dragon, y+dr[d], x+dc[d], 0); //0 세대 만든다. 마지막 좌표는 방향을 갖지 않는다.
			dragons[i] = dragon;
		}
		br.close();
		
		solution();
		System.out.println(cnt);
	}
	
	private static void solution() {
		extend();
		//printDragon();
		count();
	}
	
	private static void count() {
		for(int r=0;r<101;r++) {
			for(int c=0;c<101;c++) {
				if(!map[r][c]) continue;
				if(c+1 < 101 && map[r][c+1] && //왼쪽 확인
					r+1 < 101 && map[r+1][c] && //아래 확인
					map[r+1][c+1]) //왼쪽 아래 확인
				{
					cnt++;
				}
			}
		}
	}
	
	//드래곤커브를 확장시킨다.
	private static void extendDragon(Dragon dragon, int r, int c, int d) {
		dragon.Rs[dragon.ptCnt] = r;
		dragon.Cs[dragon.ptCnt] = c;
		dragon.Ds[dragon.ptCnt] = d;
		dragon.ptCnt++;
		map[r][c] = true;
	}
	
	private static void extend() {
		for(int i=0; i<N; i++) {
			Dragon dragon = dragons[i];
			
			//세대 만큼 확장
			for(int g=0;g<dragon.g;g++) {
				int curPtCnt = dragon.ptCnt;
				
				//마지막 이전 부터 반대로 탐색
				for(int j=curPtCnt-2; j>=0; j--) {
					//현재 마지막 좌표
					int lastR = dragon.Rs[dragon.ptCnt-1];
					int lastC = dragon.Cs[dragon.ptCnt-1];
					
					int preD = dragon.Ds[j];
					
					int nextD = preD+1 > 3 ? 0 : preD+1;
					int nextR = lastR;
					int nextC = lastC;
					switch(nextD) {
					case 0: //우
						nextC = lastC+1;
						break;
					case 1: //상
						nextR = lastR-1;
						break;
					case 2: //좌
						nextC = lastC-1;
						break;
					case 3: //하
						nextR = lastR+1;
						break;
					}
					
					if(!(nextR>=0 && nextR<101 && nextC>=0 && nextC<101)) break; //확장할 수 없다.
					
					//회전 방향은 현재의 마지막 좌표에 기입해주고 다음 좌표를 추가하여 마지막 좌표를 갱신한다.
					dragon.Ds[dragon.ptCnt-1] = nextD;
					extendDragon(dragon, nextR, nextC, 0); //마지막 좌표는 방향을 갖지 않는다.
				}
			}
		}
	}
	
	private static void printDragon() {
		for(int i=0;i<N;i++) {
			System.out.println((i+1)+"번째 드래곤커브" );
			for(int j =0; j< dragons[i].ptCnt; j++) {
				System.out.println(dragons[i].Rs[j] + "," + dragons[i].Cs[j]);
			}
		}
	}
}
