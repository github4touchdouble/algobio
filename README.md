# Maximal Scoring Subsequence

## Usage
This project is contained in one single `jar`. Based on the input params
the `jar` reacts in different ways.

```sh
java jar main.jar [--v] [--a] [--p]
```
- `--v` can be a space seperated vector of $n$ values. If not provided, `jar` will switch to **benchmark mode**
- `--a` can be any combination of algorithms (space seperated) in this set:  
   `{naive, recursive, divide, dynamic, optimal, smss}`. 
   If not provided, all elements of the set will be executed.
- `--p` can be the name of the csv file, `default=times.csv` 

[!NOTE]  
    A csv containing the execution times per algorithm is only created when in `benchmark mode` (meaning no `--v` is passed)

### Run Examples

#### Example 1
```sh
java jar main.jar --v  5 -2 5 -2 1 -9 5 -2 4 -5 1 -2 3 -1 5 -3 2 -1 2 --a naive optimal 
```
Runs `naive` and `optimal` on vector `v`

#### Example 2
```sh
java jar main.jar --a naive optimal dynamic
```
Benchmarks `naive`, `optimal`, `dynamic` 

#### Example 3
```sh
java jar main.jar --a naive optimal dynamic
```
Benchmarks `naive`, `optimal`, `dynamic` 

#### Example 4
```sh
java jar main.jar 
```
Benchmarks all

#### Example 5
```sh
java jar main.jar --v 1 2 3 -3 10 1
```
Runs all algorithms on `v`


