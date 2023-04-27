""" pdf loader module"""
import os
from PyPDF2 import PdfReader
import camelot

GENERAL_AND_FOREIGN_LANGUAGE_COURSES = '1'
SPECIALIZED_EDUCATION_COURSES = '2'
TEACHER_TRAINING_COURSES = '2_2'
REQUIRED_CREDITS_FOR_GRADUATION = '3'

class PDFLoader:
    def __init__(self, file):
        """ initialize
        Args:
            path (str): path to the pdf file
        """
        self.file = file

        # recognize format type
        # format '1': General Education and Foreign Language Courses
        # format '2': Specialized Education Courses
        # format '2_2': Teacher Training Courses
        # format '3': Required Credits for Graduation
        self.format_type = self.recognize_format()

    def recognize_format(self):
        """ recognize format type
        Returns:
            str: format type ('1', '2', '2_2', '3')
        """
        title = self.read_pdf()
        title_list = {'別表第１（第４条関係）　教養科目及び外国語科目　': '1',
                      '別表第２（第５条関係）　専門教育科目': '2',
                      '別表第２の２（第５条の２関係）　教職課程関連科目': '2_2',
                      '別表第３（第７条関係）　コンピュータ理工学科': '3'}
        return title_list[title]

    def read_pdf(self):
        """ read pdf file and returns text of the first page """
        reader = PdfReader(self.file)
        page = reader.pages[0]
        text = page.extractText()
        # extract first line
        text = text.split('\n')[0]
        return text

    def load_pdf(self):
        """ load pdf file and convert to dataframe. returns dataframe """
        tables = camelot.read_pdf(self.file)
        return tables[0].df
