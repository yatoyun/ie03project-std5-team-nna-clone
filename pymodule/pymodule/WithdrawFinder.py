""" calculate the number of members in each course who withdrawed from the course """
import glob
import os
from .HTMLLoader import HTMLLoader


class WithdrawFinder:
    def __init__(self, prefix, quarter):
        """ initialize
        Args:
            prefix (str): path to the directory where the data is stored
                e.g. '../../data/2022/s1/'
            quarter (int): quarter of the year
                e.g. 1 for spring, 2 for summer, 3 for fall, 4 for winter
        """
        # remove the last '/' if exists
        if prefix[-1] == '/':
            prefix = prefix[:-1]
        self.prefix = prefix
        self.quarter = quarter

    def get_list_data_dir_by_day(self):
        """ return list of directory paths where the data is stored
            e.g. returns ['../../data/2022/s1/4-12', '../../data/2022/s1/4-13', ...]
        """
        month = None
        if self.quarter == 1:
            month = '04'
        elif self.quarter == 2:
            month = '06'
        elif self.quarter == 3:
            month = '10'
        elif self.quarter == 4:
            month = '12'
        path = os.path.join(self.prefix, f'{month}-*')
        data_dir_list = glob.glob(path)
        data_dir_list.sort()
        definitive_path = os.path.join(self.prefix, f'q{self.quarter}_definitive')
        data_dir_list.append(definitive_path)
        return data_dir_list
