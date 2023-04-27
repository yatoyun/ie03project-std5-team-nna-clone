""" load mailing list data and convert to dataframe """
import glob
import os
import pandas as pd


class HTMLLoader:
    """ load html file and convert to dataframe """

    def __init__(self, path):
        """ initialize
        Args:
            path (str): path to the directory where the data is stored
                e.g. '../../data/2022/s1/q1_definitive'
        """
        self.prefix = path

    def get_index_html_path(self):
        """ return index html path """
        index_path = glob.glob(os.path.join(self.prefix, '**', 'mlindex_j.html'), recursive=True)[0]
        return index_path

    def get_member_html_path(self, ml_name):
        """ return the html path of the specific ml_name
        Args:
            ml_name (str): mailing list name
                e.g. '11-1001-LI13-hdw3'
        """
        member_path = glob.glob(os.path.join(self.prefix, '**', 'member', f'{ml_name}.html'), recursive=True)[0]
        return member_path

    def get_df_courses(self):
        """ return dataframe of courses """
        index_path = self.get_index_html_path()
        # first element is the title of the table (unnecessary)
        df_courses = pd.read_html(index_path)[1]
        # remove unnecessary columns
        df_courses = df_courses.drop(columns=['詳細'])
        # change column name
        df_courses = df_courses.rename(columns={'Mailing list名': 'ml_name', 'コマ': 'period', '授業名称':'course_name', '担当教員': 'instructor'})
        return df_courses

    def get_df_members(self, ml_name):
        """ return dataframe of members
        Args:
            ml_name (str): mailing list name
                e.g. '11-1001-LI13-hdw3'
        """
        # find member file
        member_path = self.get_member_html_path(ml_name)
        df_members = pd.read_html(member_path)[0]
        # remove unnecessary columns
        df_members = df_members.drop(columns=['番号No'])
        # change column name
        df_members = df_members.rename(columns={'氏名Name': 'name', '学籍番号UserID': 'id', '氏名（英語）Name(English)': 'name_en'})
        return df_members

