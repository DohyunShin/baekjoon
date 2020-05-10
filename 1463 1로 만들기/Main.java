import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	static int N; //1~1,000,000
	static int min = Integer.MAX_VALUE;
	static boolean[] visit = new boolean[1000001];
	static Queue<Integer> q = new LinkedList<Integer>();
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		
		solution();
		System.out.println(min);
	}
	
	private static void solution() {
		if(N==1) {
			min=0;
			return;
		}
		bfs();
	}
	
	private static void bfs() {
		q.add(1);
		visit[1] = true;
		
		int depth = 0;
		while(!q.isEmpty()) {
			int length = q.size();
			depth++;
			for(int i=0; i<length; i++) {
				int cur = q.poll();
				int next=0;
				for(int d=0; d<3; d++) {
					switch(d) {
					case 0:
						next=cur*3;
						break;
					case 1:
						next=cur*2;
						break;
					case 2:
						next=cur+1;
						break;
					}
					
					if(next >= 1000001 || visit[next]) continue;
					
					if(next == N) {
						min = depth;
						return;
					}
					else if(next < N) {
						q.add(next);
						visit[next] = true;
					}
				}
			}
		}
	}
}
