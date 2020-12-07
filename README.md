# WDI-Project of Group 4: Video Games

This git repository contains the code and datasets used in the Web Data Integration Project of group 4.

## File Structure

1. Datasets folder: contains the datasets we used for creating the schemas: d1_gsales.csv (d1), wiki_preprocessed.csv (d2), games1.jsonl (d3). It also contains a preprocessing script for some preprocessing steps.

2. IR folder: contains our java project for the identity resolution and fusion step (vg_IR), the goldstandard files and test and training sets

	2.1. vg_IR folder: contains the java project
    
    2.1.1 data folder contains our target schemas created in the schema creation step (sales_target.xml (d1), target_games_Wiki.xml (d2)). The rawg schema (d3) can be found in the Datasets folder (RAWG_xml_4.zip). It also contains our final fused dataset (fused.xml), our correspondences and some debug reports.
    
    2.1.2 src folder contains all scripts for the identity resolution step (see folder blockingGenerator, genralClasses, similarityMeasures, xmlReaders and vG) and for the data fusion step (see fusers, genralClasses, xmlReaders, xmlFormatters and vG). 

        -> Running the script Main.java activates the identity resolution step
        -> Running the script DataFusion_Main.java activates the fusion identity step
	
3. Mapping folder: contains our results of the mapping step in Altova MapForce

## Additional Files:

1. "db_wikidata_exloration.ipynb": Used for exploring the results of querying DBPedia and Wikidata. 
2. "Merging RAWG files.py": Used to merge the 11 different rawg files that were created after the matching step
3. "Wikidata_SPARQL_queries": Contains the queries send to wikidata API
4. "RAWG_API_calls.ipynb": Contains calls send to the rawg API
