import java.util.Scanner;

public class Main {
	static int gs[][] = new int[4][8];
	static int K;
	static int gNums[];
	static int dirs[];
	static int score = 0;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		for(int i = 0; i < 4; i++) {	
			char gCharArr[] = sc.nextLine().toCharArray();
			
			for(int j = 0; j < gCharArr.length; j++) {
				gs[i][j] = gCharArr[j] - '0';
			}
		}
		
		K = sc.nextInt();
		gNums = new int[K];
		dirs = new int[K];
		
		for(int i = 0; i < K; i++) {
			gNums[i] = sc.nextInt() - 1; //인덱스니까
			dirs[i] = sc.nextInt();
		}
		
		sc.close();
		
		solution();
		System.out.println(score);
	}
	
	private static void solution() {
		for(int i = 0; i < K; i++) {
			boolean tryGs[] = new boolean[4]; //회전할 기어들 표시
			int tryDirs[] = new int[4]; //회전할 기어들의 회전 방향
			
			tryGs[gNums[i]] = true;
			tryDirs[gNums[i]] = dirs[i];
			
			//기준의 오른쪽 검사
			//기준의 2와 오른쪽 기어의 6 비교
			int curG = gNums[i];
			int curDir = dirs[i];
			
			int sideG = curG + 1;
			while(sideG < 4) {
				if(gs[curG][2] != gs[sideG][6]) {
					tryGs[sideG] = true;
					tryDirs[sideG] = curDir * -1;
					
					curG = sideG;
					curDir = tryDirs[sideG];
					sideG++;
				}
				else {
					//멈춘 기어의 오른쪽은 더이상 볼 필요 없다.
					break;
				}
			}
			
			//기준의 왼쪽 검사
			//기준의 6과 왼쪽 기어의 2 비교
			curG = gNums[i];
			curDir = dirs[i];
			
			sideG = curG - 1;
			while(sideG >= 0) {
				if(gs[curG][6] != gs[sideG][2]) {
					tryGs[sideG] = true;
					tryDirs[sideG] = curDir * -1;
					
					curG = sideG;
					curDir = tryDirs[sideG];
					sideG--;
				}
				else {
					//멈춘 기어의 왼쪽은 더이상 볼 필요 없다.
					break;
				}
			}
			
			//알맞게 회전시키기
			for(int j = 0; j < 4; j++) {
				if(tryGs[j]) {
					spin(j, tryDirs[j]);
				}
			}
			//printG();
		}
		
		calculateScore();
	}
	
	private static void printG() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 8; j++) {
				System.out.print(gs[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private static void spin(int tryG, int dir) {
		int tryGArr[] = gs[tryG];
		int cloneArr[] = tryGArr.clone();
		
		//시계방향
		if(dir == 1) {
			for(int i = 0; i < 8; i++) {
				if(i == 7) {
					tryGArr[0] = cloneArr[i];
				}
				else {
					tryGArr[i + 1] = cloneArr[i];	
				}
			}
		}
		//반시계방향
		else {
			for(int i = 7; i >= 0; i--) {
				if(i == 0) {
					tryGArr[7] = cloneArr[i];
				}
				else {
					tryGArr[i - 1] = cloneArr[i];	
				}
			}
		}
	}
	
	private static void calculateScore() {
		if(gs[0][0] == 1) {
			score += 1;
		}
		
		if(gs[1][0] == 1) {
			score += 2;
		}
		
		if(gs[2][0] == 1) {
			score += 4;
		}
		
		if(gs[3][0] == 1) {
			score += 8;
		}
	}
}
