import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static int N, L;
	static int map[][];
	static int cnt = 0;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		L = sc.nextInt();
		map = new int[N][N];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				map[i][j] = sc.nextInt();
			}
		}
		sc.close();
		
		solution();
		System.out.println(cnt);
	}
	
	private static void solution() { 
		//row check
		for(int i = 0; i < N; i++) {
			check(map[i].clone());
		}
		
		//column check
		for(int i = 0; i < N; i++) {
			int arr[] = new int[N];
			for(int j = 0; j < N; j++) {
				arr[j] = map[j][i];
			}
			
			check(arr.clone());
		}
	}
	
	private static void check(int arr[]) {		
		int preHigh = arr[0]; //직전 높이
		int i = 1;
		boolean buildCheck[] = new boolean[N];
		boolean result = true;
		
		while(i < N) {
			int curHigh = arr[i]; //현재 검사하는 지점 높이
			
			if(preHigh == curHigh) {
				i++;
			}
			else {
				boolean canBuild = true;
				
				//직전 높이가 더 큰 경우
				//현재 지점 부터 확인 후 경사로 길이 만큼 경사로를 만든다.
				if(preHigh > curHigh) {
					if(preHigh - curHigh > 1) {
						result = false;
						break;
					}
					
					for(int j = i; j < i + L; j++) {
						if(j >= N || arr[j] != curHigh) {
							canBuild = false;
							break;
						}
					}
					
					if(canBuild) {
						for(int j = i; j < i + L; j++) {
							buildCheck[j] = true; //경사로 표시
						}
						
						i = i + L; //경사로 다음 부터 진행
						preHigh = arr[i - 1]; //다음 시작 지점 이전이 경사로 이므로
					}
					//경사로를 놓아야할 곳에 하나라도 못 놓는다면 길은 통과할 수 없다.
					else {
						result = false;
						break;
					}
				}
				//현재 높이가 더 큰 경우
				//직전 지점 부터 확인 후 경사로 길이 만큼 경사로를 만든다.
				else {
					if(curHigh - preHigh > 1) {
						result = false;
						break;
					}
					for(int j = i - 1; j >= i - L; j--) {
						//직전 지점 뒤로 경사로를 만들어야 하는데 직전 지점 뒤로 범위 내에 이미 경사로가 있으면 안된다.
						if(j < 0 || arr[j] != preHigh || buildCheck[j] == true) {
							canBuild = false;
							break;
						}
					}
					
					if(canBuild) {
						for(int j = i - 1; j >= i - L; j--) {
							buildCheck[j] = true; //경사로 표시
						}
						
						preHigh = arr[i]; //직전에 경사로 만들었으므로
						i++;//현재지점 다음 부터 진행						
					}
					//경사로를 놓아야할 곳에 하나라도 못 놓는다면 길은 통과할 수 없다.
					else {
						result = false;
						break;
					}
				}
			}
		}
		
		if(result) {
			cnt++;
		}
	}
}