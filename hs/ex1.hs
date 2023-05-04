-- HASSAN SHABIR
-- COMPUTER SCIENCE AND NETWORKING
-- SESSION 2021-2022


import Data.Char


{-
1: Implementation of quicksort function for sorting string 
2: Implementation of myMap function 
3: Implementation of toLowerCase function
4: Implementation of ciao function
5: Implementation of isCiaoString function 
6: Tesing
-}
quicksort [] = []  
quicksort (x:xs) =   
    let smallerSorted = quicksort [a | a <- xs, a <= x]  
        biggerSorted = quicksort [a | a <- xs, a > x]  
    in  smallerSorted ++ [x] ++ biggerSorted  



myMap _ [] = [] 
myMap f (x:xs) = f x : myMap f xs 


toLowerCase str = myMap toLower str


ciao str = toLowerCase(quicksort(str))


isCiaoString str = str == ciao str


-- Check if "listen" and "silent" are anagrams
test = isCiaoString "listen" == isCiaoString "silent" 
