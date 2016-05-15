def naive(a,b):
    x = a; y=b
    z = 0
    while x > 0:
        z = z + y
        x = x - 1
        if x == 20 and y == 12:
            print(z)
    return z


print(naive(63,12))

              
