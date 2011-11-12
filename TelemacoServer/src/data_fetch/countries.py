from telemaco.models import Country
from telemaco.models import Currency
import webservices as ws

def getCountries(fetch_rdf=False):
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
            
            if fetch_rdf:
                country.rdf = ws.getResource(name)
            
            country.save()