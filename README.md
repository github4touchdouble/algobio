# Maximal Scoring Subsequence

## Usage
This project is contained in one single `jar`. Based on the input parameters
the `jar` reacts in different ways.

```sh
java -jar main.jar [--vec] [--algorithms] [--path] [--step] [--size]
```
- `--vec` can be a space separated vector of $n$ values. If not provided, `jar` will switch to **benchmark mode**.
  - `--algorithms` can be any combination of algorithms (space separated) in this set:  
     `{naive, recursive, divide, dynamic, optimal, 2_a, 2_b, 2_c, 2_c_1}`.  
     Note that `2_c_1` is also the same algorithm as `2_c` (utilizing) the dynamic programming algorithm, but  
     in `2_c_1` we optimized the array usage by only creating as many ints in the arrays as necessary. 
     It is still not optimal, but it works on larger input `n` (see report).
     If not provided, all elements of the set will be executed.
- `--path` can be the name of the csv file, `default=times.csv`.
- `--step` this parameter is used in benchmarking. You can control the inner and outer loop 
   iterations: `--step 100 10` will set the outer loop to run 100 times and the vec will be increased by 10 
   each iteration. So the algorithms will be benchmarked on sizes: 10, 20, 30, 40, ..., 990, 1000.
   Default value is set to 100 10.

> [!NOTE]  
> A csv containing the execution times per algorithm is only created when in `benchmark mode`   
> (meaning no `--vec` is passed)

## Run all algorithms on input from sheet
```
java -jar SMSS.jar --vec 5 -2 5 -2 1 -9 12 -2 24 -5 13 -12 3 -13 5 -2 -1 2
```

> // Naive:   
> //   
> // 	[6,10] mit score 42   
> // 	466 µs   
> // 	for input size 19   
>    
> // Recursive:   
> //   
> // 	[6,10] mit score 42   
> // 	180 µs   
> // 	for input size 19   
>    
> // Dynamic Programming:   
> //   
> // 	[6,10] mit score 42   
> // 	29 µs   
> // 	for input size 19   
>    
> // Divide and Conquer:   
> //   
> // 	[6,10] mit score 42
> // 	25 µs   
> // 	for input size 19   
>    
> // Optimal:   
> // 	[6,10] mit score 42   
> // 	4 µs   
> // 	for input size 19   
>    
> // 2_a (MSS):   
> //   
> // 	[6,10] mit score 42   
> // 	24 µs   
> // 	for input size 19   
>    
> // 2_b (SMSS.jar):   
> //   
> // 	[6,10] mit score 42   
> // 	24 µs   
> // 	for input size 19   
>    
> // 2_c (All SMSS.jar):   
> //   
> // 	[6,10] mit score 42   
> // 	32 µs   
> // 	for input size 19   
>    
> // 2_c_1 (All SMSS.jar & Optimized space usage):   
> //   
> // 	[6,10] mit score 42   
> // 	49 µs   
> // 	for input size 19   

## Run Examples

### Example 1
```sh
java -jar SMSS.jar --vec  5 -2 5 -2 1 -9 5 -2 4 -5 1 -2 3 -1 5 -3 2 -1 2 --algorithms naive optimal 
```
Runs `naive` and `optimal` on vector `vec`

### Example 2
```sh
java -jar SMSS.jar --algorithms naive optimal dynamic --path test
```
Benchmarks `naive`, `optimal`, `dynamic` and saves time (in microseconds) as `test.csv`

### Example 3
```sh
java -jar SMSS.jar --algorithms naive optimal dynamic --step 300 
```
Benchmarks `naive`, `optimal`, `dynamic`, increases input vec by 300 each iteration.

### Example 4
```sh
java -jar SMSS.jar --step 1000 1
```
Benchmarks all and saves times to `times.csv`. The algorithms will be benchmarked on vectors, that 
increase by one each iteration until the size of the vec is equal to 1000.

### Example 5
```sh
java -jar SMSS.jar --vec 1 2 3 -3 10 1
```
Runs all algorithms on `vec`

## Plotting Results
```sh
python3 plot.py -p <name.csv> -s

```
Saves plot as `<name.png>` and labels y-axis with seconds. If `-s` is not passed, the y values will be 
displayed in microseconds.
![Example](times_sec_1000_all.png)


