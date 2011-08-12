#!/usr/bin/python

import os
import webservices as ws

os.environ['DJANGO_SETTINGS_MODULE'] = 'settings'
from telemaco.models import Country
from telemaco.models import City
from telemaco.models import Currency
from telemaco.models import Place
from telemaco.models import Language

# We fetch data entity by entity
print "Executing data_fetch process..."

# Country
print 'Getting countries information'
countries = ws.querySPARQLtoJSON("""
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>
PREFIX dbpprop: <http://dbpedia.org/property/>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>

SELECT DISTINCT *
WHERE {
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
        #language, created = Language.objects.get_or_create(name=name)
        country.currency = currency
        country.wikipedia_url = 'http://en.wikipedia.org/w/index.php?title='+name.replace(" ", "_")+'&printable=yes'
        country.wikitravel_url = 'http://wikitravel.org/wiki/en/index.php?title='+name.replace(" ", "_")+'&printable=yes'
        country.save()
        
# Languages of a country
languages = ws.querySPARQLtoJSON("""
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>
PREFIX dbpprop: <http://dbpedia.org/property/>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>

SELECT * WHERE {
?country rdf:type dbpedia-owl:Country .
?country dbpedia-owl:language ?language .
?language rdfs:label ?name .
?country rdfs:label ?country_name .
FILTER(LANGMATCHES(LANG(?name), 'EN'))
FILTER(LANGMATCHES(LANG(?country_name), 'EN'))
} LIMIT 1000
""")

for language in languages:
    name = language['name']['value']
    country_name = language['country_name']['value'] 

    for country in Country.objects.filter(name=country_name):
        print 'Saving information for', name, 'in', country_name
        language, created = Language.objects.get_or_create(name=name)
        language.save()

        if language not in country.languages.all():
            country.languages.add(language)
            country.save()

# Cities of a country
for country in Country.objects.all().filter(name='Spain'):
    print 'Querying cities for country', country.name
    cities = ws.querySPARQLtoJSON("""
    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
    PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>
    PREFIX dbpprop: <http://dbpedia.org/property/>
    PREFIX foaf: <http://xmlns.com/foaf/0.1/>

    SELECT DISTINCT * WHERE {
    ?city dbpedia-owl:country ?country .
    ?city rdfs:label ?name .
    ?country rdfs:label '"""+country.name+"""'@en .
    ?city geo:lat ?lat .
    ?city geo:long ?long .
    OPTIONAL {?city dbpedia-owl:abstract ?abstract}
    OPTIONAL {?city dbpprop:utcOffset ?timezone}
    OPTIONAL {?city dbpprop:utcOffsetDst ?timezone_dst}
    ?city dbpprop:populationTotal ?population .
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
        c, created = City.objects.get_or_create(name=name, defaults={'country':country})
        c.description = city['abstract']['value']
        c.lat = city['lat']['value']
        c.lng = city['long']['value']
        c.wikipedia_url = 'http://en.wikipedia.org/w/index.php?title='+name.replace(" ", "_")+'&printable=yes'
        c.wikitravel_url = 'http://wikitravel.org/wiki/en/index.php?title='+name.replace(" ", "_")+'&printable=yes'
        try:
            c.timezone = city['timezone']['value']
            c.timezone_dst = city['timezone_dst']['value']
        except KeyError:
            pass
        c.save()

for city in City.objects.all():
    print 'Querying places for city', city
    places = ws.querySPARQLtoJSON("""
    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
    PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>
    PREFIX dbpprop: <http://dbpedia.org/property/>
    PREFIX foaf: <http://xmlns.com/foaf/0.1/>
    
    SELECT DISTINCT * WHERE {
    ?place rdfs:label ?name .
    ?place geo:lat ?lat .
    ?place geo:long ?long .
    OPTIONAL{?place dbpedia-owl:abstract ?abstract}
    FILTER(xsd:double(?lat) - xsd:double("""+str(city.lat)+""") <= 0.10 &&
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
        p, created = Place.objects.get_or_create(name=name)
        p.description = place['abstract']['value']
        p.lat = place['lat']['value']
        p.lng = place['long']['value']
        p.wikipedia_url = 'http://en.wikipedia.org/w/index.php?title='+name.replace(" ", "_")+'&printable=yes'
        p.save()

# Item (place)

# Weather

# Currency
currencies = Currency.objects.all()
for currency in currencies:
    print "Getting info for currency: ", currency.name
    currency.rate = ws.getCurrencyExchange("EUR", currency.code)
    currency.save()


print "Finished process data_fetch."