#!/usr/bin/python

import os
import webservices as ws

os.environ['DJANGO_SETTINGS_MODULE'] = 'settings'
from telemaco.models import Country
from telemaco.models import Currency

# We fetch data entity by entity
print "Executing data_fetch process..."

# Country
print 'Getting countries information'
countries = ws.querySPARQLtoJSON("""
SELECT * WHERE {
?country rdf:type dbpedia-owl:Country .
?country rdfs:label ?name .
?country dbpprop:currencyCode ?currency_code .
?country dbpprop:currency ?currency_name . 
?country dbpedia-owl:abstract ?abstract .
FILTER(LANGMATCHES(LANG(?abstract), 'EN'))
FILTER(LANGMATCHES(LANG(?name), 'EN'))
} LIMIT 1000
""")['results']['bindings']
 
for c in countries:
    name = c['name']['value']
    print 'Saving information for country', name
    
    result = Country.objects.filter(name=name)
    if result:
        country = result[0]
        country.description = c['abstract']['value']
        currency_code = c['currency_code']['value']
        currency, created = Currency.objects.get_or_create(code=currency_code, defaults={'name': c['currency_name']['value']})
        country.currency = currency
        country.save()
    
#print countries

# Languages of a country
#"""SELECT * WHERE {
#?country rdf:type dbpedia-owl:Country .
#?language rdf:type dbpedia-owl:Language .
#?language dbpprop:iso ?code .
#?language rdfs:label ?name .
#?country dbpedia-owl:language ?language .
#?country rdfs:label "Belgium"@en .
#FILTER(LANG(?name)='es')
#} LIMIT 1000"""

# Cities of a country
#for country in Country.objects.all():
#    query = """
#    SELECT * WHERE {
#    ?country rdf:type dbpedia-owl:Country .
#    #?city rdf:type dbpedia-owl:PopulatedPlace .
#    #?city rdf:type dbpedia-owl:City .
#    ?city dbpedia-owl:country ?country .
#    ?city dbpprop:populationTotal ?population .
#    ?country rdfs:label """"
#    query += country.name
#    query += """"
#    @en .
#    FILTER(?population > 2000)
#    } LIMIT 1000"""
#    
#    cities = ws.querySPARQLtoJSON("""
#    
#    """)
#    
#    print cities

# Currency
currencies = Currency.objects.all()
for currency in currencies:
    print "Getting info for currency: ", currency.name
    currency.rate = ws.getCurrencyExchange("EUR", currency.code)
    currency.save()

# Language

# Places near coordinates
#"""PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> 
#PREFIX onto: <http://dbpedia.org/ontology/> 
#SELECT * WHERE {
#?s a onto:Place .
#?s geo:lat ?lat .
#?s geo:long ?long .
#} FILTER ( ?long > YOUR_LONG - radius && ?long < YOUR_LONG + radius &&
#lat > YOUR_LAT - radius && ?lat < YOUR_LAT + radius)
#LIMIT 100"""

# Item (place)

# Weather

print "Finished process data_fetch."