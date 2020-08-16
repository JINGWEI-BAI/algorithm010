//1.柠檬水找零
class Solution {
    public boolean lemonadeChange(int[] bills) {
        int five = 0, ten = 0;
        for (int bill: bills) {
            if (bill == 5)
                five++;
            else if (bill == 10) {
                if (five == 0) return false;
                five--;
                ten++;
            } else {
                if (five > 0 && ten > 0) {
                    five--;
                    ten--;
                } else if (five >= 3) {
                    five -= 3;
                } else {
                    return false;
                }
            }
        }

        return true;
    }
}

//2.买卖股票Ⅱ，使用贪心算法
public class Solution {

    public int maxProfit(int[] prices) {
        int res = 0;
        int len = prices.length;
        for (int i = 0; i < len - 1; i++) {
            int diff = prices[i + 1] - prices[i];
            if (diff > 0) {
                res += diff;
            }
        }
        return res;
    }
}

//3.模拟行走机器人
class Solution {
    public int robotSim(int[] commands, int[][] obstacles) {
        int[] dx = new int[]{0, 1, 0, -1};
        int[] dy = new int[]{1, 0, -1, 0};
        int x = 0, y = 0, di = 0;

        // Encode obstacles (x, y) as (x+30000) * (2^16) + (y+30000)
        Set<Long> obstacleSet = new HashSet();
        for (int[] obstacle: obstacles) {
            long ox = (long) obstacle[0] + 30000;
            long oy = (long) obstacle[1] + 30000;
            obstacleSet.add((ox << 16) + oy);
        }

        int ans = 0;
        for (int cmd: commands) {
            if (cmd == -2)  //left
                di = (di + 3) % 4;
            else if (cmd == -1)  //right
                di = (di + 1) % 4;
            else {
                for (int k = 0; k < cmd; ++k) {
                    int nx = x + dx[di];
                    int ny = y + dy[di];
                    long code = (((long) nx + 30000) << 16) + ((long) ny + 30000);
                    if (!obstacleSet.contains(code)) {
                        x = nx;
                        y = ny;
                        ans = Math.max(ans, x*x + y*y);
                    }
                }
            }
        }

        return ans;
    }
}

//4.单词接龙，使用BFS
class Solution {
  public int ladderLength(String beginWord, String endWord, List<String> wordList) {

    // Since all words are of same length.
    int L = beginWord.length();

    // Dictionary to hold combination of words that can be formed,
    // from any given word. By changing one letter at a time.
    Map<String, List<String>> allComboDict = new HashMap<>();

    wordList.forEach(
        word -> {
          for (int i = 0; i < L; i++) {
            // Key is the generic word
            // Value is a list of words which have the same intermediate generic word.
            String newWord = word.substring(0, i) + '*' + word.substring(i + 1, L);
            List<String> transformations = allComboDict.getOrDefault(newWord, new ArrayList<>());
            transformations.add(word);
            allComboDict.put(newWord, transformations);
          }
        });

    // Queue for BFS
    Queue<Pair<String, Integer>> Q = new LinkedList<>();
    Q.add(new Pair(beginWord, 1));

    // Visited to make sure we don't repeat processing same word.
    Map<String, Boolean> visited = new HashMap<>();
    visited.put(beginWord, true);

    while (!Q.isEmpty()) {
      Pair<String, Integer> node = Q.remove();
      String word = node.getKey();
      int level = node.getValue();
      for (int i = 0; i < L; i++) {

        // Intermediate words for current word
        String newWord = word.substring(0, i) + '*' + word.substring(i + 1, L);

        // Next states are all the words which share the same intermediate state.
        for (String adjacentWord : allComboDict.getOrDefault(newWord, new ArrayList<>())) {
          // If at any point if we find what we are looking for
          // i.e. the end word - we can return with the answer.
          if (adjacentWord.equals(endWord)) {
            return level + 1;
          }
          // Otherwise, add it to the BFS Queue. Also mark it visited
          if (!visited.containsKey(adjacentWord)) {
            visited.put(adjacentWord, true);
            Q.add(new Pair(adjacentWord, level + 1));
          }
        }
      }
    }

    return 0;
  }
}

//5.岛屿数量，使用DFS
class Solution {
    void dfs(char[][] grid, int r, int c) {
        int nr = grid.length;
        int nc = grid[0].length;

        if (r < 0 || c < 0 || r >= nr || c >= nc || grid[r][c] == '0') {
            return;
        }

        grid[r][c] = '0';
        dfs(grid, r - 1, c);
        dfs(grid, r + 1, c);
        dfs(grid, r, c - 1);
        dfs(grid, r, c + 1);
    }

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int nr = grid.length;
        int nc = grid[0].length;
        int num_islands = 0;
        for (int r = 0; r < nr; ++r) {
            for (int c = 0; c < nc; ++c) {
                if (grid[r][c] == '1') {
                    ++num_islands;
                    dfs(grid, r, c);
                }
            }
        }

        return num_islands;
    }
}

//6.跳跃游戏，使用贪心算法
public class Solution {
    public boolean canJump(int[] nums) {
        int n = nums.length;
        int rightmost = 0;
        for (int i = 0; i < n; ++i) {
            if (i <= rightmost) {
                rightmost = Math.max(rightmost, i + nums[i]);
                if (rightmost >= n - 1) {
                    return true;
                }
            }
        }
        return false;
    }
}

