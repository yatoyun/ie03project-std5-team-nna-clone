""" test PDFLoader """
from pymodule.pymodule.PDFLoader import PDFLoader
import os


def test_load_pdf_1():
    """ test load_pdf()
        format 1
    """
    path = './pdf/reg082-1_j.pdf'
    basename = os.path.basename(path)
    loader = PDFLoader(path)
    df = loader.load_pdf()
    # export csv
    save_name = basename.replace('.pdf', '.csv')
    df.to_csv(save_name, index=False)


def test_load_pdf_2():
    """ test load_pdf()
        format 2
    """
    path = './pdf/reg082-2_j.pdf'
    basename = os.path.basename(path)
    loader = PDFLoader(path)
    df = loader.load_pdf()
    # export csv
    save_name = basename.replace('.pdf', '.csv')
    df.to_csv(save_name, index=False)


def test_load_pdf_2_2():
    """ test load_pdf()
        format 2_2
    """
    path = './pdf/reg082-2-2_j.pdf'
    basename = os.path.basename(path)
    loader = PDFLoader(path)
    df = loader.load_pdf()
    # export csv
    save_name = basename.replace('.pdf', '.csv')
    df.to_csv(save_name, index=False)


def test_load_pdf_3():
    """ test load_pdf()
        format 3
    """
    path = './pdf/reg082-3_j.pdf'
    basename = os.path.basename(path)
    loader = PDFLoader(path)
    df = loader.load_pdf()
    print(len(df))
    # export csv
    save_name = basename.replace('.pdf', '.csv')
    df.to_csv(save_name, index=False)


def test_readpdf_1():
    """ test read_pdf() """
    path = './pdf/reg082-1_j.pdf'
    loader = PDFLoader(path)
    pdf = loader.read_pdf()
    print(pdf)
    print(loader.format_type)


def test_readpdf_2():
    """ test read_pdf() """
    path = './pdf/reg082-2_j.pdf'
    loader = PDFLoader(path)
    pdf = loader.read_pdf()
    print(pdf)
    print(loader.format_type)


def test_readpdf_2_2():
    """ test read_pdf() """
    path = './pdf/reg082-2-2_j.pdf'
    loader = PDFLoader(path)
    pdf = loader.read_pdf()
    print(pdf)
    print(loader.format_type)


def test_readpdf_3():
    """ test read_pdf() """
    path = './pdf/reg082-3_j.pdf'
    loader = PDFLoader(path)
    pdf = loader.read_pdf()
    print(pdf)
    print(loader.format_type)

