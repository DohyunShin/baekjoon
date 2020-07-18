import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
	static class Unit{
		public int r, c, d;
		public Unit(int r, int c, int d) {
			this.r = r;
			this.c = c;
			this.d = d;
		}
	}
	static int N; //4~12
	static int K; //4~10
	static int[][] map = new int[12][12]; //0:흰, 1:빨, 2:파
	static Deque<Unit>[][] unitMap = new LinkedList[12][12];
	static Unit[] units = new Unit[10];
	//우좌상하
	static int[] dr = {0,0,-1,1};
	static int[] dc = {1,-1,0,0};
	static int res = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				unitMap[i][j] = new LinkedList<Unit>();
			}
		}
		for(int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			int d = Integer.parseInt(st.nextToken())-1;
			Unit unit = new Unit(r,c,d);
			units[i]  = unit;
			unitMap[r][c].addLast(unit);
		}
		br.close();
		
		solution();
		System.out.println(res >= 1000 ? -1 : res);
	}
	
	public static void solution() {
		Unit unit;
		int nr, nc, nColor;
		boolean stop;
		
		while(res++ < 1000) {
			for(int num = 0; num < K; num++) {
				unit = units[num];
				
				//이동가능한지 본다.
				nr = unit.r + dr[unit.d];
				nc = unit.c + dc[unit.d];
				
				if(!(nr >= 0 && nr < N && nc >= 0 && nc < N))
					nColor = 2;
				else 
					nColor = map[nr][nc];
				
				//방향을 반대로 변경하여 한칸 이동
				if(nColor == 2) {
					unit.d++;
					if(unit.d == 2) unit.d = 0;
					else if(unit.d == 4) unit.d = 2;
					
					nr = unit.r + dr[unit.d];
					nc = unit.c + dc[unit.d];
					
					//방향을 반대로 했을 때 벽이거나 파란색인 경우 이동하지 않는다.
					if(!(nr >= 0 && nr < N && nc >= 0 && nc < N))
						nColor = 2;
					else
						nColor = map[nr][nc];
					
					if(nColor == 2) continue;
				}
				
				stop = move(unit, nr, nc, nColor == 1 ? true : false);
				
				if(stop) return;
			}
		}
	}
	
	public static boolean move(Unit unit, int nr, int nc, boolean isReverse) {
		boolean stop = false;
		
		Deque<Unit> from = unitMap[unit.r][unit.c];
		Deque<Unit> to = unitMap[nr][nc];
		Deque<Unit> temp = new LinkedList<Unit>();
		int tempCnt;
		Unit tempUnit;
		
		boolean found = false;
		
		for(Unit u : from) {
			if(u.equals(unit) && u.hashCode() == unit.hashCode()) {
				found = true;
			}
			
			if(found) {
				temp.addLast(u);
			}
		}
		
		tempCnt = temp.size();
		
		for(int i = 0; i < tempCnt; i++) {
			tempUnit = isReverse ? temp.pollLast() : temp.pollFirst();
			to.addLast(tempUnit);
			from.remove(tempUnit);
			
			tempUnit.r = nr;
			tempUnit.c = nc;
		}
		
		if(to.size() >= 4) stop = true;
		
		return stop;
	}
}
