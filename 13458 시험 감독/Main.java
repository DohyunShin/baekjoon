import java.util.Scanner;

public class Main {

	static int N; //시험장 개수
	static int A[]; //각 시험장 응시자 수
	static int B; //총 감독관(1명)이 감시할 수 있는 응시자의 수
	static int C; //부 감독관이 감시할 수 있는 응시자의 수
	//총 감독관은 시험장 당 1명, 부 감독관은 여러명 가능
	//필요한 감독관 수는?
	//?? 총 감독관은 시험장 마다 무조건 한 명이 필요한 것 같은데, 맞는가?
	//Tip. 시간 초과가 문제임. 인원수가 같은 시험장의 계산을 피한다. 메모라이징
	static int check[];
	static long sum; //인원수가 1,000,000 으로 크고 시험장 개수도 1,000,000 으로 크기 때문에 결과 값의 자료형을 크게 잡아야한다.
	
	public static void main(String[] args) {
		check = new int[1000000]; //한 시험장의  인원수 범위 (1~1,000,000)
		
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		A = new int[N];
		for(int i = 0; i < N; i++) {
			A[i] = sc.nextInt();
		}
		B = sc.nextInt();
		C = sc.nextInt();
		sc.close();
		
		getSum();
		System.out.println(sum);
	}

	private static void getSum() {
		
		int rest;
		int temp_sum = 0;
		for(int i = 0; i < N; i++) {
			rest = 0;
			temp_sum = 0;
			
			int num = A[i];
			if(check[num - 1] > 0) {
				sum += check[num - 1];
				continue;
			}
			
			rest = num - B;
			temp_sum++;
			
			while(rest > 0) {
				rest -= C;
				temp_sum++;
			}
			
			check[num - 1] = temp_sum;
			sum += temp_sum;
		}
	}
}
