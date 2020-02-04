import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class Main {
	
	static int r, c, k;
	static int A[][];
	static int rCnt = 3;
	static int cCnt = 3;
	static int cnt = 0;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		r = sc.nextInt() - 1;
		c = sc.nextInt() - 1;
		k = sc.nextInt();
		
		A = new int[rCnt][cCnt];
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				A[i][j] = sc.nextInt();
			}
		}
		sc.close();
		
		solution();
		System.out.println(cnt);
	}
	
	private static void solution() {
		while(true) {
			if(cnt > 100) {
				cnt = -1;
				break;
			}
			
			if(r < rCnt && c < cCnt && A[r][c] == k) {
				break;
			}
			
			if(rCnt >= cCnt) {
				//column 횟수를 최대로 잡은 임시 배열
				int T[][] = new int[rCnt][100];
				int maxColCnt = 0; //행의 컬럼 개수 중 가장 많은 것
				
				for(int r = 0; r < rCnt; r++) {
					//한 행의 숫자들의 횟수를 정리한다.
					HashMap<Integer, Integer> numCnts = new HashMap<Integer, Integer>();
					
					for(int c = 0; c < cCnt; c++) {
						if(A[r][c] == 0) {
							continue;
						}
						
						if(numCnts.containsKey(A[r][c])) {
							numCnts.replace(A[r][c], numCnts.get(A[r][c]) + 1);
						}
						else {
							numCnts.put(A[r][c], 1);
						}
					}
					
					//가장 횟수가 적은 숫자 부터 횟수 만큼 임시배열에 저장한다.
					int colCnt = 0; //한 행의 column 수
					while(!numCnts.isEmpty()) { 
						//가장 횟수가 적은 숫자를 찾는다.
						//횟수가 같은 경우 작은 수 부터 찾는다.
						int minCnt = 9999999;
						int minNum = 0;
						
						for(Entry<Integer, Integer> entry : numCnts.entrySet()) {
							int num = entry.getKey();
							int numCnt = entry.getValue();
							
							if(numCnt < minCnt) {
								minCnt = numCnt;
								minNum = num;
							}
							else if(numCnt == minCnt) {
								if(num < minNum) {
									minCnt = numCnt;
									minNum = num;
								}
							}
						}
						
						//찾은 수와 횟수를 임시배열에 저장한다. 최대 100개의 컬럼까지만 저장
						T[r][colCnt++] = minNum;
						if(colCnt >= 100) {
							break;
						}
						
						T[r][colCnt++] = minCnt;
						if(colCnt >= 100) {
							break;
						}
						
						numCnts.remove(minNum);
					}
					
					//가장 긴 컬럼 수 보다 많으면 바꾼다.
					if(maxColCnt < colCnt) {
						maxColCnt = colCnt;
					}
				}
				
				//배열을 갱신한다.
				cCnt = maxColCnt;
				int newA[][] = new int[rCnt][cCnt];
				for(int i = 0; i < rCnt; i++) {
					for(int j = 0; j < cCnt; j++) {
						newA[i][j] = T[i][j];
					}
				}
				
				A = newA;
				
			}
			else {
				//열 횟수를 최대로 잡은 임시 배열
				int T[][] = new int[100][cCnt];
				int maxRowCnt = 0; //열의 컬럼 개수 중 가장 많은 것
				
				for(int c = 0; c < cCnt; c++) {
					//한 열의 숫자들의 횟수를 정리한다.
					HashMap<Integer, Integer> numCnts = new HashMap<Integer, Integer>();
					
					for(int r = 0; r < rCnt; r++) {
						if(A[r][c] == 0) {
							continue;
						}
						if(numCnts.containsKey(A[r][c])) {
							numCnts.replace(A[r][c], numCnts.get(A[r][c]) + 1);
						}
						else {
							numCnts.put(A[r][c], 1);
						}
					}
					
					//가장 횟수가 적은 숫자 부터 횟수 만큼 임시배열에 저장한다.
					int rowCnt = 0; //한 열의 row수
					while(!numCnts.isEmpty()) { 
						//가장 횟수가 적은 숫자를 찾는다.
						//횟수가 같은 경우 작은 수 부터 찾는다.
						int minCnt = 9999999;
						int minNum = 0;
						
						for(Entry<Integer, Integer> entry : numCnts.entrySet()) {
							int num = entry.getKey();
							int numCnt = entry.getValue();
							
							if(numCnt < minCnt) {
								minCnt = numCnt;
								minNum = num;
							}
							else if(numCnt == minCnt) {
								if(num < minNum) {
									minCnt = numCnt;
									minNum = num;
								}
							}
						}
						
						
						T[rowCnt++][c] = minNum;
						if(rowCnt >= 100) {
							break;
						}
						
						T[rowCnt++][c] = minCnt;
						if(rowCnt >= 100) {
							break;
						}
						
						numCnts.remove(minNum);
					}
					
					//가장 긴 행 수 보다 많으면 바꾼다.
					if(maxRowCnt < rowCnt) {
						maxRowCnt = rowCnt;
					}
				}
				
				//배열을 갱신한다.
				rCnt = maxRowCnt;
				int newA[][] = new int[rCnt][cCnt];
				for(int i = 0; i < rCnt; i++) {
					for(int j = 0; j < cCnt; j++) {
						newA[i][j] = T[i][j];
					}
				}
				
				A = newA;
			}
			
//			for(int i = 0; i < rCnt; i++) {
//				for(int j =0; j < cCnt; j++) {
//					System.out.print(A[i][j] + " ");
//				}
//				System.out.println();
//			}
//			System.out.println();
			cnt++;
		}
	}
}
