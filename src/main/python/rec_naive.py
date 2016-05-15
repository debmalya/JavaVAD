def rec_naive(a,b):
   if a == 0:
       return 0
   return b + rec_naive(a - 1,b)

    


print(rec_naive(63,12))

              
