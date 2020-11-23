# Python program to 
# demonstrate merging of 
# two files 
  
# Creating a list of filenames 
import pathlib

path= pathlib.Path(".\data\RAWG")
flist = []
for p in pathlib.Path(path).iterdir():
    if p.is_file():
        print(p)
        flist.append(p)

filenames = ['file1.txt', 'file2.txt'] 
  
# Open file3 in write mode 
with open('file3.txt', 'w') as outfile: 
  
    # Iterate through list 
    for names in flist: 
  
        # Open each file in read mode 
        with open(names) as infile: 
  
            # read the data from file1 and 
            # file2 and write it in file3 
            outfile.write(infile.read()) 
  
        # Add '\n' to enter data of file2 
        # from next line 
        outfile.write("\n") 