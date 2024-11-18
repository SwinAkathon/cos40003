Sudoku Solver (given in the lecture)
======================================

## Algorithm pseudocode

### `solve()` initial
```java
boolean solve(int i, int j){
  if ( matrix[i][j] != 0) {
    return solve(i, j+1);
  } else {
    for (int k = 1; k <= 9; k++) { 
      if ( check(i, j, k) ) {
        matrix[i][j] = k;
        if ( solve(i, j+1) )
          return true;
      }
    }

    matrix[i][j] = 0;
    return false;
  }
}
```

### `check()` validity
```java
boolean check(int row, int col, int k) {
  // check if k can be put at (row, col)
  for (int i = 0; i < 9; i++) {
    if (matrix[row][i] == k ||
        matrix[i][col] == k) {
      return false;
    }
  }
  int tempRow = row / 3;
  int tempCol = col / 3;
  for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 3; j++) {
      if (matrix[tempRow * 3 + i][tempCol * 3 + j] == k) {
        return false;
      }
    }
  }
  return true;
}
```

### `solve()` with stop condition

```java
boolean solve(int i, int j){
  // stop condition
  if (j == 9) {
    j = 0;
    i++;
    if (i == 9)
      return true;
  } 

  // same as above
  if ( matrix[i][j] != 0) {
    return solve(i, j+1);
  } else {
    for (int k = 1; k <= 9; k++) { 
      if (check(i, j, k) ) {
        matrix[i][j] = k;
        if (solve(i, j+1))
          return true;
      }
    }

    matrix[i][j] = 0;
    return false;
  }
}
```

## Non-concurrent versions
- **Solver1** from StackOverflow
  - quite inefficient: does not remember the current cell
- **Solver2** is an improved version of Solver1
  - remembers the current cell `solver(i,j)` => try from next cell `solver(i,j+1)`
- **Solver4** is the **best** among the three.
  - 

Examine the elapse time!

## Concurrent version
- **Solver5**: concurrent version 

What do you observe of the elapse time?

The concurrent version runs significantly slower than the non-concurrent version. WHY????