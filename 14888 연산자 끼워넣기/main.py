def pm(n, k, arr, res):
    if k == 0:
        res.append(arr[:])
        #print(arr)
        return

    for i in range(n - 1, -1, -1):
        arr[i], arr[n - 1] = arr[n - 1], arr[i]
        pm(n - 1, k - 1, arr, res)
        arr[i], arr[n - 1] = arr[n - 1], arr[i]


def main():
    # 입력
    n = int(input()) # 수의 개수
    a = list(map(int, input().split())) # 수
    add, sub, mul, div = list(map(int, input().split())) # 각 연산자 개수
    op = [] # 1: add, 2: sub, 3: mul, 4: div

    for i in range(add):
        op.append(1)
    for i in range(sub):
        op.append(2)
    for i in range(mul):
        op.append(3)
    for i in range(div):
        op.append(4)

    res = []
    pm(len(op), len(op), op, res)

    max = 0
    min = 0

    for i in range(0, len(res)):
        sum = 0
        for j in range(0, len(a)):
            if j == 0:
                sum = a[j]
            else:
                case_op = res[i][j - 1]
                #print(sum, case_op, a[j])
                if case_op == 1:
                    sum += a[j]
                elif case_op == 2:
                    sum -= a[j]
                elif case_op == 3:
                    sum *= a[j]
                elif case_op == 4:
                    if (sum < 0 and a[j] >= 0) or (sum >= 0 and a[j] < 0):
                        temp = abs(sum) // abs(a[j])
                        sum = temp * -1
                    else:
                        sum //= a[j]

        if i == 0:
            max = sum
            min = sum
        else:
            if max < sum:
                max = sum
            if min > sum:
                min = sum

    print(max)
    print(min)

if __name__ == "__main__":
    main()