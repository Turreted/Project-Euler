from math import factorial
from pprint import pprint


class Solution:
    def __init__(self):

        self.vmap = {5: [], 4: [5], 3: [5, 4], 2: [5, 4, 3]}

        # precomputed lookup table using the bruteforce dfs.
        # I'm not smart enough to find a way to do this dynamically
        self.lookup_table = {
            "3": 0.5,
            "34": 0.6527777777777777,
            "35": 0.9166666666666666,
            "355": 0.6944444444444445,
            "4": 0.0,
            "44": 0.5,
            "445": 0.38888888888888884,
            "4455": 0.3194444444444445,
            "45": 0.5,
            "455": 0.3333333333333333,
            "4555": 0.24999999999999997,
            "5": 0.0,
            "55": 0.0,
            "555": 0.0,
            "5555": 0.0,
        }

        self.iter = 0
        self.sum = 0

        self.dfs_avg = 0
        self.total_sum = 0

    def dfs(self, sheets: list, cur_ones: int, prob: int):

        # if our current position will never result in a lone sheet,
        # compute the result from our position
        if all([x == 5 for x in sheets]):
            self.dfs_avg += cur_ones / prob
            return

        for i, s in enumerate(sheets):

            # get sheets that are added as a result of the the sheet chosen
            added_sheets = self.vmap[s]

            # remove the old sheet
            ns = sheets[:i] + sheets[i + 1 :] + added_sheets
            is_one = 1 if (len(ns) == 1 and ns[0] != 5) else 0

            # convert to hashable string for lookup table
            ns.sort()
            pos = "".join([str(s) for s in ns])

            # check lookup table
            if pos in self.lookup_table:
                self.dfs_avg += (cur_ones + self.lookup_table[pos]) / (
                    prob * len(sheets)  # divide by probability of the LOOKUP
                )

            else:
                # perform dfs
                self.dfs(ns, cur_ones + is_one, prob * len(sheets))

    def bruteforce_dfs(self, sheets: list, cur_ones: int, prob: int):

        if all([x == 5 for x in sheets]):
            self.dfs_avg += cur_ones / prob
            return

        for i, s in enumerate(sheets):

            # get sheets that are added as a result of the the sheet chosen
            added_sheets = self.vmap[s]

            # remove the old sheet
            ns = sheets[:i] + sheets[i + 1 :] + added_sheets
            is_one = 1 if (len(ns) == 1 and ns[0] != 5) else 0

            # recurse
            self.bruteforce_dfs(ns, cur_ones + is_one, prob * len(sheets))

    def compute(self, sheets: int):
        self.dfs(sheets, 0, 1)
        # self.bruteforce_dfs(sheets, 0, 1)

        # print(f"Chance using pruning {self.sum}, {self.iter}")
        # print(f"{self.dfs_avg}")

        pprint(self.lookup_table)
        # print(round(self.sum / self.iter, 6))
        print(round(self.dfs_avg, 6))


s = Solution()
s.compute([2, 3, 4, 5])
