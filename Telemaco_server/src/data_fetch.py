#!/usr/bin/python

import os
import webservices as ws

os.environ['DJANGO_SETTINGS_MODULE'] = 'settings'
from telemaco.models import Country
from telemaco.models import City
from telemaco.models import Currency
from telemaco.models import Place

# We fetch data entity by entity
print "Executing data_fetch process..."

# Country
print 'Getting countries information'
countries = ws.querySPARQLtoJSON("""
SELECT DISTINCT * WHERE {
?country rdf:type dbpedia-owl:Country .
?country rdfs:label ?name .
?country dbpprop:currencyCode ?currency_code .
?country dbpprop:currency ?currency_name .
?country dbpedia-owl:abstract ?abstract .
FILTER(LANGMATCHES(LANG(?abstract), 'EN'))
FILTER(LANGMATCHES(LANG(?name), 'EN'))
} LIMIT 1000
""")
 
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
        
# Currency
currencies = Currency.objects.all()
for currency in currencies:
    print "Getting info for currency: ", currency.name
    currency.rate = ws.getCurrencyExchange("EUR", currency.code)
    currency.save()

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
for country in Country.objects.all().filter(name='Spain'):
    print 'Querying cities for country', country.name
    cities = ws.querySPARQLtoJSON("""
    SELECT DISTINCT * WHERE {
    ?city dbpedia-owl:country ?country .
    ?city rdfs:label ?name .
    ?city dbpedia-owl:abstract ?abstract .
    ?city geo:lat ?lat .
    ?city geo:long ?long .
    ?city dbpprop:populationTotal ?population .
    ?country rdfs:label '"""+country.name+"""'@en .
    FILTER(LANGMATCHES(LANG(?abstract), 'EN'))
    FILTER(LANGMATCHES(LANG(?name), 'EN'))
    } ORDER BY DESC(?population)
    LIMIT 100
    """)
    
    #?country rdf:type dbpedia-owl:Country .\ 
    #?city rdf:type dbpedia-owl:PopulatedPlace . 
    #?city rdf:type dbpedia-owl:City . 
    
    for city in cities:
        name = city['name']['value']
        print 'Saving information for city', name, ',', country.name
        c, created = City.objects.get_or_create(name=name, defaults={'description':city['abstract']['value'],
                                                                     'country':country,
                                                                     'lat':city['lat']['value'],
                                                                     'lng':city['long']['value']})
        c.save()

for city in City.objects.all():
    print 'Querying places for city', city
    places = ws.querySPARQLtoJSON("""
    SELECT DISTINCT * WHERE {
    ?place rdfs:label ?name .
    ?place dbpedia-owl:abstract ?abstract .
    ?place geo:lat ?lat .
    ?place geo:long ?long .
    FILTER(xsd:double(?jlat) - xsd:double("""+str(city.lat)+""") <= 0.10 &&
    xsd:double("""+str(city.lat)+""") - xsd:double(?lat) <= 0.10 &&
    xsd:double(?long) - xsd:double("""+str(city.lng)+""") <= 0.10 &&
    xsd:double("""+str(city.lng)+""") - xsd:double(?long) <= 0.10 &&
    LANGMATCHES(LANG(?name), 'EN') &&
    LANGMATCHES(LANG(?abstract), 'EN')
    ) } LIMIT 1000
    """)

    for place in places:
        name = place['name']['value']
        print 'Saving information for place', name, 'in', city
        p, created = Place.objects.get_or_create(name=name, defaults={'description':place['abstract']['value'], 
                                                                      'lat': place['lat']['value'],
                                                                      'lng': place['long']['value']})
        p.save()
    

# Item (place)

# Weather

print "Finished process data_fetch."