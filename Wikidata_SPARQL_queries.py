from SPARQLWrapper import SPARQLWrapper, JSON

WIKIDATA_ENDPOINT = "https://query.wikidata.org/sparql"
DBPEDIA_ENDPOINT = "http://dbpedia.org/sparql"

wiki_sparql = SPARQLWrapper(WIKIDATA_ENDPOINT)
dbpedia_sparql = SPARQLWrapper(DBPEDIA_ENDPOINT)

get_wikidata()
number_videogames_pyear(1995)

def get_wikidata():
	"""
    Get information/ tables from wikipedia
	"""
	query = SPARQL_VIDEOGAMES_LIST_ALL
	wiki_sparql.setQuery(query)
	wiki_sparql.setReturnFormat(csv)
	result_set = wiki_sparql.query().convert()
	return result_set

def number_videogames_pyear(year):
    """
    Get the number of video games per year
    """
    query = SPARQL_NUMBER_VIDEOGAMES_PERYEAR.replace("YEAR_PLACEHOLDER", year)
    wiki_sparql.setQuery(query)
    wiki_sparql.setReturnFormat(csv)
    result_set=wiki_sparql.query().convert()
    return result_set


# WIKIDATA
# Number of video games per year (<=1995)
SPARQL_NUMBER_VIDEOGAMES_PERYEAR = """
SELECT DISTINCT ?year (COUNT(DISTINCT ?videogames) AS ?nrvideos) 
WHERE
{
  ?videogames wdt:P31/wdt:P279* wd:Q7889.
  ?videogames wdt:P577 ?date.
  BIND(year(?date) AS ?year)
  Filter(?year >=YEAR_PLACEHOLDER)
} GROUP BY ?year ORDER BY DESC(?year)
"""

# List of all video games & all attributes
SPARQL_VIDEOGAMES_LIST_ALL = """
SELECT DISTINCT ?videogames ?videogamesLabel ?plattformLabel 
?countryOriginLabel ?publicationDate ?genreLabel ?gameModeLabel ?developerLabel ?publisherLabel
?directorLabel ?designedByLabel ?composerLabel ?fictionalUniverseLabel ?followsLabel
?followedByLabel ?basedOnLabel ?WebsiteLabel ?ESRBrating ?CEROrating ?PEGIrating
WHERE
{
?videogames wdt:P31/wdt:P279* wd:Q7889.

OPTIONAL {?videogames wdt:P400 ?plattform.} #36264
OPTIONAL{?videogames wdt:P495 ?countryOrigin.} #21.160
OPTIONAL{?videogames wdt:P577 ?publicationDate.} #32.808
OPTIONAL{?videogames wdt:P136 ?genre.} #30242
OPTIONAL{?videogames wdt:P404 ?gameMode.} #23768
OPTIONAL{?videogames wdt:P178 ?developer.} #21.076
OPTIONAL{?videogames wdt:P136 ?publisher.} #37817
OPTIONAL{?videogames wdt:P287 ?designedBy.} #1857
OPTIONAL{?videogames wdt:P86 ?composer.} #4012
OPTIONAL{?videogames wdt:P1434 ?fictionalUniverse.} #929
OPTIONAL{?videogames wdt:P155 ?follows.} #1630
OPTIONAL{?videogames wdt:P156 ?followedBy.} #1614
OPTIONAL{?videogames wdt:P144 ?basedOn.} #1188
OPTIONAL{?videogames wdt:P856 ?Website.} #8777
OPTIONAL{?videogames wdt:P852 ?ESRBrating.} #4223
OPTIONAL{?videogames wdt:P853 ?CEROrating.} #1541
OPTIONAL{?videogames wdt:P908 ?PEGIrating.} #3334

SERVICE wikibase:label{bd:serviceParam wikibase:language "en".}
}
"""

#Zu wenig inhalt/ Zu viele Missings
#OPTIONAL{?videogames wdt: P1873 ?maxPlayers.} =ca 180
#OPTIONAL{?videogames wdt: P1872 ?minPlayers.} =ca 150
#OPTIONAL{?videogames wdt: P6949 ?anouncementDate.} =440
#OPTIONAL{?videogames wdt:P57 ?director.} = 686
#OPTIONAL{?videogames wdt:P50 ?author.} =155
#OPTIONAL{?videogames wdt:P943 ?programmer.} =191
#OPTIONAL{?videogames wdt:P3080 ?gameArtist.} =72
#OPTIONAL{?videogames wdt:P725 ?voiceActor.} = 365
#OPTIONAL{?videogames wdt:P2408 ?setinPeriod.} = 328
#OPTIONAL{?videogames wdt:P941 ?inspiredBy.} = 121
#OPTIONAL{?videogames wdt:P674 ?characters.} =751
#OPTIONAL{?videogames wdt:P444 ?reviewScore.} =684
#OPTIONAL{?videogames wdt:P166 ?awards.} =185
#OPTIONAL{?videogames wdt:P1411 ?nominatedFor.}=40
#OPTIONAL {?videogames wdt:P277 ?programmingLanguage.} =282


