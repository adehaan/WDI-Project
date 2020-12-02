# Python program to 
# demonstrate merging of 
# two files 
  
# Creating a list of filenames 
import pathlib
import os

path= pathlib.Path(".\data\RAWG")
flist = []
for p in pathlib.Path(path).iterdir():
    if p.is_file():
        print(p)
        flist.append(p)



c = 0
while len(flist) != 0 :
    setof11 = [] 
    while len(setof11) < 11 and len(flist) != 0:
        setof11.append(str(flist.pop(0)))
    c += 1


    # Open file3 in write mode 
    with open(".\data\RAWG_concat\cfile" + str(c) +'.jsonl', 'w') as outfile: 
    
        # Iterate through list 
        for names in setof11: 
    
            # Open each file in read mode 
            with open(names) as infile: 
    
                # read the data from file1 and 
                # file2 and write it in file3 
                outfile.write(infile.read()) 
    
            # Add '\n' to enter data of file2 
            # from next line 
            outfile.write("\n") 

print("Fertig")