import pathlib
import os
import xml.etree.ElementTree as ET
import pandas as pd

gspath = ("./IR/Goldstandard_sales_rawg.csv")
goldstandard = pd.read_csv(gspath, names= ["SalesID", "RAWGID", "Match"])
IDs = goldstandard["RAWGID"]
#IDs = ["rawg_get-out-2", "rawg_block-party", "rawg_acdc-live-rock-band-track-pack", "rawg_hoverboard-racing", "rawg_power-pro-kun-pocket", "rawg_super-monkeys-ball"]


secondRawgXMLfile = pathlib.Path("./Datasets/RAWG_xml_1.xml")
print(f"**Loading xml file: {secondRawgXMLfile}")
tree_old = ET.parse(secondRawgXMLfile)
print("**xml file loaded")


#for anID in IDs
#xPath = "Games/Game/[id = 'rawg_dotted-2']"
tree_new = ET.ElementTree(ET.Element("Games"))
a = tree_new.getroot()
print("**New Tree created")
i=1
f=0
for anID in IDs:
    #Check the games with the id that is also in the goldstandard
    xPath = f"./Game[id = '{anID}']"
    print(f"{i}. Looking for: {xPath}")
    i+=1
    #Retrieve the element with the corresponding ID
    game = tree_old.find(xPath)
    if game != None:
        #Add the element in the new tree
        print(f"###Game found: {xPath}")
        print(f"Game found: {game.itertext()}")
        a.append(game)
        f+=1

print(f"{f} Files found.")#Write the new tree in a new file
tree_new.write(file_or_filename=pathlib.Path("./Datasets/RAWG_xml_1gs.xml"))
print("File written.")