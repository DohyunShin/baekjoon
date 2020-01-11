import java.util.HashMap;
import java.util.Scanner;

public class Main {

	/*
	 * 1부터 N까지 선택을 하고 안하고 dp 로 깊이는 N/2만큼
	 * N/2 깊이에서 멈춰서 선택받은 곳 까리의 대각선의 합과 선택받지 못한 곳 끼리의 대각선의 합의 차를 구한다.
	 * 구한 정답과 선택 받은 모든 곳을 매칭하여 메모라이징한다.
	 * 대각선의 합
	 */
	static int N;
	static int M[][];
	static int min = 99999;
	static HashMap<String, Integer> check = new HashMap<String, Integer>();
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = new int[N][N];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				M[i][j] = sc.nextInt();
			}
		}
		sc.close();
		
		solution();
		
		System.out.println(min);
	}
	
	private static void solution() {
		dp(0, 0, new boolean[N]);
	}
	
	private static void dp(int targetIndex, int depth, boolean selected[]) {
		if(depth >= N/2) {
			String selectedStr = "";
			for(int i = 0; i < selected.length; i++) {
				if(selected[i]) {
					selectedStr += Integer.toString(i);
				}
			}
			
			if(!check.containsKey(selectedStr)) {
				//합 계산
				int result = calculate(selected);
				
				//min 변경
				if(min > result) {
					min = result;
				}
				
				//check
				check.put(selectedStr, result);
			}
			return;
		}
		
		if(targetIndex >= N) {
			return;
		}
		
		//targetIndex 를 선택
		boolean nextSelected[] = selected.clone();
		nextSelected[targetIndex] = true;
		dp(targetIndex + 1, depth + 1, nextSelected);
		
		//targetIndex 를 선택 안함
		dp(targetIndex + 1, depth, selected);
	}
	
	private static int calculate(boolean selected[]) {
		int selectedSum = 0;
		int unSelectedSum = 0;
		
		for(int i = 0; i < selected.length - 1; i++) {
			//선택된 것들끼리의 대각선 합
			if(selected[i]) {
				for(int j = i + 1; j < selected.length; j++) {
					if(selected[j]) {
						selectedSum += M[i][j];
						selectedSum += M[j][i];
					}
				}	
			}
			//선택안된 것들 끼리의 대각선 합
			else {
				for(int j = i + 1; j < selected.length; j++) {
					if(!selected[j]) {
						unSelectedSum += M[i][j];
						unSelectedSum += M[j][i];
					}
				}	
			}
		}
		
		int upper = selectedSum > unSelectedSum ? selectedSum : unSelectedSum;
		int lower = selectedSum < unSelectedSum ? selectedSum : unSelectedSum;
		
		//큰거에서 작은거 빼준다.
		return upper - lower;
	}
}
