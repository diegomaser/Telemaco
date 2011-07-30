#!/usr/bin/python

import os
import webservices
from telemaco.models import Currency 

os.environ['DJANGO_SETTINGS_MODULE'] = 'settings'

print "Executing data_fetch process..."

# We fetch data entity by entity
# Country
"""SELECT * WHERE {
?country rdf:type dbpedia-owl:Country .
?country rdfs:label "Spain"@en .
?country dbpprop:currencyCode ?currency_code .
?country dbpedia-owl:abstract ?abstract .
FILTER(LANGMATCHES(LANG(?abstract), 'EN'))
} LIMIT 1000"""

"""SELECT * WHERE {
?country rdf:type dbpedia-owl:Country .
?language rdf:type dbpedia-owl:Language .
?language dbpprop:iso ?code .
?language rdfs:label ?name .
?country dbpedia-owl:language ?language .
?country rdfs:label "Belgium"@en .
FILTER(LANG(?name)='es')
} LIMIT 1000"""

webservices.querySPARQLtoJSON("""

""")

# Cities of a country
"""SELECT * WHERE {
?country rdf:type dbpedia-owl:Country .
#?city rdf:type dbpedia-owl:PopulatedPlace .
?city rdf:type dbpedia-owl:City .
?city dbpedia-owl:country ?country .
?city dbpprop:populationTotal ?population .
?country rdfs:label "Spain"@en .
} ORDER BY DESC(?population)"""


# Currency
currencies = Currency.objects.all()
for currency in currencies:
    print "Getting info for currency: ", currency.name
    currency.rate = webservices.getCurrencyExchange("EUR", currency.code)
    currency.save()

# Language

# Places near coordinates
"""PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> 
PREFIX onto: <http://dbpedia.org/ontology/> 
SELECT * WHERE {
?s a onto:Place .
?s geo:lat ?lat .
?s geo:long ?long .
} FILTER ( ?long > YOUR_LONG - radius && ?long < YOUR_LONG + radius &&
lat > YOUR_LAT - radius && ?lat < YOUR_LAT + radius)
LIMIT 100"""

# Item (place)

# Weather

print "Finished process data_fetch."