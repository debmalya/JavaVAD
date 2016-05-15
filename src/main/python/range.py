def clique(a):
   count = 0 
   for j in range(a):
       for i in range(j):
           count = count + 1
   print(count)        


clique(4)           
