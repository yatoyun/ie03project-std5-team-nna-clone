""" test WithdrawFinder """
from pymodule.pymodule.WithdrawFinder import WithdrawFinder

def test_get_list_data_dir_by_day():
    """ test get_list_data_dir_by_day() """
    prefix = './data/2023/s1/'
    quarter = 1
    finder = WithdrawFinder(prefix, quarter)
    data_dir_list = finder.get_list_data_dir_by_day()
    print(data_dir_list)