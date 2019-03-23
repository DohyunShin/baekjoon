g_add = 0
g_sub = 0
g_mul = 0
g_div = 0

g_max = -1000000000
g_min = 1000000000


# Brute Force
def brtf(arr, i, add, sub, mul, div, total):
    global g_add
    global g_sub
    global g_mul
    global g_div
    global g_max
    global g_min

    if len(arr) == i:
        if g_max < total:
            g_max = total
        if g_min > total:
            g_min = total
        return

    if add < g_add:
        brtf(arr, i + 1, add + 1, sub, mul, div, total + arr[i])
    if sub < g_sub:
        brtf(arr, i + 1, add, sub + 1, mul, div, total - arr[i])
    if mul < g_mul:
        brtf(arr, i + 1, add, sub, mul + 1, div, total * arr[i])
    if div < g_div:
        temp = 0
        if (total < 0 and arr[i] >= 0) or (total >= 0 and arr[i] < 0):
            temp = abs(total) // abs(arr[i])
            total = temp * -1
        else:
            temp = total // arr[i]
            total = temp
        brtf(arr, i + 1, add, sub, mul, div + 1, total)


def main():
    global g_add
    global g_sub
    global g_mul
    global g_div
    global g_max
    global g_min

    n = int(input())
    a = list(map(int, input().split()))
    g_add, g_sub, g_mul, g_div = list(map(int, input().split()))

    brtf(a, 1, 0, 0, 0, 0, a[0])

    print(g_max)
    print(g_min)


if __name__ == "__main__":
    main()