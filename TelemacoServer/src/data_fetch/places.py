from telemaco.models import Place
from telemaco.models import City
import webservices as ws

def getPlaces():
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
            p, created = Place.objects.get_or_create(name=name, defaults={'city':city})
            p.description = place['abstract']['value']
            p.lat = place['lat']['value']
            p.lng = place['long']['value']
            p.wikipedia_url = 'http://en.wikipedia.org/w/index.php?title='+name.replace(" ", "_")+'&printable=yes'
            p.save()