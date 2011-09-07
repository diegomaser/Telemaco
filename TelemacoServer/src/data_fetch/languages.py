from telemaco.models import Language
from telemaco.models import Country
import webservices as ws

def getLanguages():
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
