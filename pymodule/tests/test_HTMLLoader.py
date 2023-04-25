from pymodule.pymodule.HTMLLoader import *

def test_get_class_list():
    """ test get_class_list() """
    dir_path = './data/2023/s1/q1_definitive/'
    loader = HTMLLoader(dir_path)
    classes = loader.get_df_classes()
    print(classes)

def test_get_member_list():
    """ test get_member_list() """
    dir_path = './data/2023/s1/q1_definitive/'
    loader = HTMLLoader(dir_path)
    members = loader.get_df_members('11-1001-LI13-hdw3')
    print(members)

