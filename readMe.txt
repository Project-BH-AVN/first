1. circular rotation trick

logic

1 check if length of s1 and s2 are equal
2 make s3 = s1 + s1
3 check if s2 appears inside s3
4 if yes → s2 is circular rotation of s1

example

s1 = ABCDEF
s2 = DEFABC

s3 = ABCDEFABCDEF

DEFABC appears in s3
so s2 is a circular rotation of s1

=======================================================================

2. prime number check using sqrt n

logic

1 if n ≤ 1 → not prime
2 check divisibility from 2 to sqrt(n)
3 if any number divides n → not prime
4 if none divide → prime

example

n = 29

sqrt(29) ≈ 5

check divisibility

29 % 2 ≠ 0
29 % 3 ≠ 0
29 % 4 ≠ 0
29 % 5 ≠ 0

no number divides 29

so 29 is prime

==========================================================================================