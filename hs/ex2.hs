-- HASSAN SHABIR
-- COMPUTER SCIENCE AND NETWORKING
-- SESSION 2021-2022

data Dictionary a b = Dict [(a, [b])] deriving (Show)

-- Returns an empty Dictionary
empty :: Dictionary a b
empty = Dict []

 -- Returns a dictionary obtained by inserting in dict the element v with key k
insert :: Eq a => Dictionary a b -> a -> b -> Dictionary a b
insert (Dict listOfTuples) k v = Dict (insert_new listOfTuples) 
    where
        insert_new [] = [(k, [v])]
        insert_new ((key, val_list):xs)
         | key == k   = (key, v : val_list) : xs
         | otherwise  = (key, val_list) : insert_new xs

-- Returns a value of type Maybe [b], which is the list of elements with key k, if such list exists in dict, and Nothing otherwise
lookup' :: Eq t => Dictionary t b -> t -> Maybe [b]
lookup' (Dict []) _ = Nothing
lookup' (Dict ((key, val_list):xs)) k
      | key == k   = Just val_list
      | otherwise  = lookup' (Dict xs) k
    

-- Returns a list containing the keys of dict
keys :: Dictionary b1 b2 -> [b1]
keys (Dict listOfTuples) = map fst listOfTuples

-- Returns a single list containing all the values present in dict
values :: Dictionary a b -> [b]
values (Dict listOfTuples) = concat (map snd listOfTuples)

-- It works similar to insert function, but it adds a list of values instead a single value for a given key
insertList :: Eq a => Dictionary a b -> a -> [b] -> Dictionary a b
insertList (Dict l) k v_list = Dict (insert_list l)
  where
    insert_list [] = [(k, v_list)]
    insert_list ((key, val_list):xs)
      | key == k  = (key, v_list++val_list):xs
      | otherwise = (key, val_list):insert_list xs


-- Returns the Dictionary obtained by merging the contents of the two dictionaries
merge :: Ord a => Dictionary a b -> Dictionary a b -> Dictionary a b
merge (Dict l1) (Dict l2) = Dict (mergeList l1 l2)
  where
    mergeList [] l2 = l2
    mergeList l1 [] = l1
    mergeList ((k1,v1):xs) ((k2,v2):ys)
      | k1 == k2  = (k1,v1++v2) : mergeList xs ys
      | k1 < k2   = (k1,v1) : mergeList xs ((k2,v2):ys)
      | otherwise = (k2,v2) : mergeList ((k1,v1):xs) ys