def rec_russion(a,b):
    if a == 0: return 0
    if a % 2 = 0: return rec_russian(a/2,b)
    return rec_russian(a-1/2,b)
