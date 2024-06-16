# Gene Regulatory Network
## Usage

**Task 2a**
```sh
java -jar GRN.jar -network PATH_TO_EDGE_LIST -active PATH_TO_ACTIVE_GENES
```
**Task 2b**
```sh
java -jar GRN.jar -cnf PATH_TO_DIMACS
```
**Task 2c**
```sh
java -jar GRN.jar -network PATH_TO_EDGE_LIST -active PATH_TO_ACTIVE_GENES -sol PATH_TO_AKMAXSAT_OUT
```
**Execute all tasks:**
```sh
java -jar GRN.jar -network PATH_TO_EDGE_LIST -active PATH_TO_ACTIVE_GENES -all
```

## Complexity
### Generation of GRN
The complexity of initializing `GRN` should be $\mathcal{O}(m + n)$ where $n = \# \text{ lines in edge list}$
and $m = \# \text{ active genes}$, since we read each file only once.

Converting the `GRN` to a $SAP$ formatted file in `toSAP()`:  
We loop through each `gene` ($\equiv$ clauses) and for each clause $C$ we loop 
through all `TFs` ($\equiv$ variables) $v_{i} \in C$. 
So we can approximate the worst case like this:
Let's say each clause $C$ holds all variables (meaning each `gene` if affected by all `TF`).
So our worst case run time would be: $\mathcal{O}(|C| \cdot |V|)$

### Execution of akmaxsat
We call it in $\mathcal{O}(1)$ and write the output in $\mathcal{O}(n)$ to `SOLVED.txt`.
We searched for docs on `akmaxsat` in order to identify its complexity but 
there were no real doc on complexity ;).


### Mapping solution to active TFs
In order to map a solution to our `TFs` we again need to initialize the `GRN` in $\mathcal{O}(m+n)$.
Then we go through each line $x_{i}$ of the `SOLVED.txt` output (holding $x$ lines) of `akmaxsat` until we hit $x_{i}[0] == v$ 
We split its content into a `Sting[]` array $V$ of length $|V|$. We iterate over each item in our `String[]` array and
update the corresponding `TFs` in our `HashMap`. This means our complexity is 
equal to $\mathcal{O}(x + |V|)$.

### getActiveTFs()
Linear complexity $\mathcal{O}(n)$

### Execution of -all flag
Is dominated by [TASK 2a](#Generation of GRN)

> [!NOTE]
> This only applies because we assume that `akmaxsat` solver has a better complexity than [TASK 2a](#Generation of GRN).
> If not, then the run time complexity is dominated by [Execution of akmaxsat](#Execution of akmaxsat).

