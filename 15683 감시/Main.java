import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static class Cctv{
		int t, r, c, dCnt;
		public Cctv() {}
		public Cctv(int t, int r, int c, int dCnt) {
			this.t = t;
			this.r = r;
			this.c = c;
			this.dCnt = dCnt;
		}
	}
	static int N,M;//1~8
	static int[][] map = new int[8][8];
	static int min = Integer.MAX_VALUE;
	static Cctv[] cctvs = new Cctv[8];
	static int cctvCnt = 0;
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		int input;
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				input = Integer.parseInt(st.nextToken());
				if(input >= 1 && input <= 5) {
					int dCnt;
					if(input == 1 || input == 3 || input == 4) dCnt = 4;
					else if(input == 2) dCnt = 2;
					else dCnt = 1; //5
					cctvs[cctvCnt++] = new Cctv(input-1, i, j, dCnt);
				}
				else {
					map[i][j] = input;	
				}
			}
		}
		br.close();
		
		solution();
		System.out.println(min);
	}
	
	private static void solution() {
		recursive(0, map);
	}
	
	//cctv 하나씩 타입에 따라 회전 개수 만큼 방향 돌려가면서 확인한다.
	private static void recursive(int idx, int[][] curMap) {		
		if(idx == cctvCnt) {
			int cnt = 0;
			for(int i=0;i<N;i++) {
				for(int j=0;j<M;j++) {
					if(curMap[i][j] == 0) cnt++;
				}
			}
			
			if(cnt < min) min = cnt;
			return;
		}
		
		int[][] save = curMap.clone();
		for(int i=0;i<N;i++) {
			save[i] = curMap[i].clone();
		}
		
		for(int d=0;d<cctvs[idx].dCnt;d++) {
			look(idx, d, curMap);
			recursive(idx+1, curMap);
			curMap = save.clone();
			for(int i=0;i<N;i++) {
				curMap[i] = save[i].clone();
			}
		}
	}
	
	private static void lookEx(int cr, int cc, int d, int[][] curMap) {
		int nr = cr;
		int nc = cc;
		
		while(true) {
			nr = nr+dr[d];
			nc = nc+dc[d];
			if(!(nr>=0 && nr<N && nc>=0 && nc<M) || curMap[nr][nc]==6) break;
			curMap[nr][nc] = 7;
		}
	}
	
	private static void look(int idx, int d, int[][] curMap) {
		int type = cctvs[idx].t;
		int cr = cctvs[idx].r;
		int cc = cctvs[idx].c;
		
		curMap[cr][cc] = 7;
		
		
		
		//위
		if((type==0&&d==0) || 
			(type==1&&d==0) || 
			(type==2&&(d==0||d==3)) || 
			(type==3&&(d==0||d==1||d==3)) ||
			(type==4)) {
			lookEx(cr,cc,0,curMap);
		}
		//아래
		if((type==0&&d==1) || 
			(type==1&&d==0) || 
			(type==2&&(d==1||d==2)) || 
			(type==3&&(d==1||d==2||d==3)) ||
			(type==4)) {
			lookEx(cr,cc,1,curMap);
		}
		//왼
		if((type==0&&d==2) || 
			(type==1&&d==1) || 
			(type==2&&(d==2||d==3)) || 
			(type==3&&(d==0||d==2||d==3)) ||
			(type==4)) {
			lookEx(cr,cc,2,curMap);
		}
		//오른
		if((type==0&&d==3) || 
			(type==1&&d==1) || 
			(type==2&&(d==0||d==1)) || 
			(type==3&&(d==0||d==1||d==2)) ||
			(type==4)) {
			lookEx(cr,cc,3,curMap);
		}
	}
	
	private static void printMap(int[][] curMap) {
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				System.out.print(curMap[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
