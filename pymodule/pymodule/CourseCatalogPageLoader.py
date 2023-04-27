""" get course information such as course name and semester 　"""

import pandas as pd
import re
import requests
import sys
import shelve
import time
from bs4 import BeautifulSoup
from pathlib import Path

COURSE_CATALOG_URL_INDEX = 'http://web-ext.u-aizu.ac.jp/official/curriculum/syllabus/1_J_000.html'

class CourseCatalogPageLoader:
    """ load course catalog page and convert to dataframe """

    def __init__(self):
        """ initialize """
        # load shelve
        Path('../data').mkdir(parents=True, exist_ok=True)
        self.shelves = shelve.open('../data/CourseCatalogPageLoader.shelve')
        self.top_page = self.get_content_souped(COURSE_CATALOG_URL_INDEX)
        self.course_info_detail_links = self.get_set_course_info_detail_links()

    def get_df_courses(self):
        """ returns dataframe of course list """
        courses = self.top_page.find_all('td', attrs={'class': 'kamoku'})
        course_dict = {}
        for course in courses:
            text = course.text
            # remove unnecessary spaces
            text = text.lstrip().rstrip()
            # split course id and course name
            course_id, course_name = text.split(' ', 1)
            course_dict[course_id] = course_name
        df_courses = pd.DataFrame.from_dict(course_dict, orient='index', columns=['course_name'])
        return df_courses

    def get_set_course_info_detail_links(self):
        """ find links to course information detail pages
            Returns: set of links
        """
        # deal with relative path
        prefix = COURSE_CATALOG_URL_INDEX[:COURSE_CATALOG_URL_INDEX.rfind('/') + 1]
        # find all links
        a_list = self.top_page.find_all('a')
        links_set = set()
        for a in a_list:
            link = a.get('href')
            # remove parameters # and ?
            link = link.split('#')[0].split('?')[0]
            # if link is html link, add to set
            if link.endswith('.html'):
                # if link is relative path, add prefix
                if not link.startswith('http'):
                    link = prefix + link
                links_set.add(link)
        return links_set

    def get_df_course_info(self):
        """ returns dataframe of course information details """
        # if course information details are already loaded, return it
        if 'df_course_info' in self.shelves:
            return self.shelves['df_course_info']
        course_info = {}
        for url in self.course_info_detail_links:
            # print as stderr
            message = f'Loading {url} ...'
            print(message, file=sys.stderr)
            soup = self.get_content_souped(url)
            courses = soup.find_all('div', attrs={'class': 'sytab'})
            for course in courses:
                soup = BeautifulSoup(str(course), 'html.parser')
                # get course name and remove spaces at the beginning and end
                couse_id = soup.find('ul').text.strip().split()[0]
                # get course information table
                # it contains semester, grade, credit, and so on
                table = soup.find('table', attrs={'class': 'syllabus-normal'})
                # convert to dataframe
                df = pd.read_html(str(table))[0]
                # get semester information at df[1][0]
                semester = df[1][0].split()[2]
                # remove unnecessary characters. and extract smester number
                # get grade information at df[1][1]
                grade = df[1][1]
                # remove unnecessary characters. and extract grade number
                grade = [int(re.sub('年', '', i).strip()) for i in grade.split(',')]
                course_info[couse_id] = {'semester': semester, 'grade': grade}
            time.sleep(4)
        df_course_info = pd.DataFrame.from_dict(course_info, orient='index')
        self.shelves['df_course_info'] = df_course_info
        return df_course_info

    def get_content_souped(self, url):
        """ return page content as souped object """
        r = requests.get(url)
        soup = BeautifulSoup(r.content, 'html.parser')
        return soup


if __name__ == '__main__':
    loader = CourseCatalogPageLoader()
    df_course_info = loader.get_df_course_info()
    df_courses = loader.get_df_courses()
    df = pd.concat([df_courses, df_course_info], axis=1)
    print(df)
    df.to_csv('../data/course_info.csv')





