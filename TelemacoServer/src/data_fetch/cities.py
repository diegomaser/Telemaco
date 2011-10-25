from telemaco.models import City
from telemaco.models import Country
import webservices as ws

def getCities():
    # Cities of a country
    countries = Country.objects.all().filter(name='Spain')
    
    for country in countries:
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
        ?city dbpprop:populationTotal ?population .
        OPTIONAL {?city dbpedia-owl:abstract ?abstract}
        OPTIONAL {?city dbpprop:utcOffset ?timezone}
        OPTIONAL {?city dbpprop:utcOffsetDst ?timezone_dst}    
        
        FILTER(LANGMATCHES(LANG(?abstract), 'EN'))
        FILTER(LANGMATCHES(LANG(?name), 'EN'))
        } ORDER BY DESC(?population)
        LIMIT 100
        """)
        
        #?country rdf:type dbpedia-owl:Country .\ 
        #?city rdf:type dbpedia-owl:PopulatedPlace . 
        #?city rdf:type dbpedia-owl:City .
        
    #    OPTIONAL {  ?city dbpprop:janHighC ?janHighC .
    #                ?city dbpprop:janLowC ?janLowC .
    #                ?city dbpprop:janMeanC ?janMeanC .
    #                ?city dbpprop:janPrecipitationDays ?janPrecipitationDays .
    #                ?city dbpprop:janPrecipitationMm ?janPrecipitationMm .
    #                ?city dbpprop:janSun ?janSun .
    #                
    #                ?city dbpprop:febHighC ?febHighC .
    #                ?city dbpprop:febLowC ?febLowC .
    #                ?city dbpprop:febMeanC ?febMeanC .
    #                ?city dbpprop:febPrecipitationDays ?febPrecipitationDays .
    #                ?city dbpprop:febPrecipitationMm ?febPrecipitationMm .
    #                ?city dbpprop:febSun ?febSun .
    #    
    #                ?city dbpprop:marHighC ?marHighC .
    #                ?city dbpprop:marLowC ?marLowC .
    #                ?city dbpprop:marMeanC ?marMeanC .
    #                ?city dbpprop:marPrecipitationDays ?marPrecipitationDays .
    #                ?city dbpprop:marPrecipitationMm ?marPrecipitationMm .
    #                ?city dbpprop:marSun ?marSun .
    #    
    #                ?city dbpprop:aprHighC ?aprHighC .
    #                ?city dbpprop:aprLowC ?aprLowC .
    #                ?city dbpprop:aprMeanC ?aprMeanC .
    #                ?city dbpprop:aprPrecipitationDays ?aprPrecipitationDays .
    #                ?city dbpprop:aprPrecipitationMm ?aprPrecipitationMm .
    #                ?city dbpprop:aprSun ?aprSun .
    #    
    #                ?city dbpprop:mayHighC ?mayHighC .
    #                ?city dbpprop:mayLowC ?mayLowC .
    #                ?city dbpprop:mayMeanC ?mayMeanC .
    #                ?city dbpprop:mayPrecipitationDays ?mayPrecipitationDays .
    #                ?city dbpprop:mayPrecipitationMm ?mayPrecipitationMm .
    #                ?city dbpprop:maySun ?maySun .
    #
    #                ?city dbpprop:junHighC ?junHighC .
    #                ?city dbpprop:junLowC ?junLowC .
    #                ?city dbpprop:junMeanC ?junMeanC .
    #                ?city dbpprop:junPrecipitationDays ?junPrecipitationDays .
    #                ?city dbpprop:junPrecipitationMm ?junPrecipitationMm .
    #                ?city dbpprop:junSun ?junSun .
    #
    #                ?city dbpprop:julHighC ?julHighC .
    #                ?city dbpprop:julLowC ?julLowC .
    #                ?city dbpprop:julMeanC ?julMeanC .
    #                ?city dbpprop:julPrecipitationDays ?julPrecipitationDays .
    #                ?city dbpprop:julPrecipitationMm ?julPrecipitationMm .
    #                ?city dbpprop:julSun ?julSun .
    #    
    #                ?city dbpprop:augHighC ?augHighC .
    #                ?city dbpprop:augLowC ?augLowC .
    #                ?city dbpprop:augMeanC ?augMeanC .
    #                ?city dbpprop:augPrecipitationDays ?augPrecipitationDays .
    #                ?city dbpprop:augPrecipitationMm ?augPrecipitationMm .
    #                ?city dbpprop:augSun ?augSun .
    #
    #                ?city dbpprop:sepHighC ?sepHighC .
    #                ?city dbpprop:sepLowC ?sepLowC .
    #                ?city dbpprop:sepMeanC ?sepMeanC .
    #                ?city dbpprop:sepPrecipitationDays ?sepPrecipitationDays .
    #                ?city dbpprop:sepPrecipitationMm ?sepPrecipitationMm .
    #                ?city dbpprop:sepSun ?sepSun .
    #
    #                ?city dbpprop:octHighC ?octHighC .
    #                ?city dbpprop:octLowC ?octLowC .
    #                ?city dbpprop:octMeanC ?octMeanC .
    #                ?city dbpprop:octPrecipitationDays ?octPrecipitationDays .
    #                ?city dbpprop:octPrecipitationMm ?octPrecipitationMm .
    #                ?city dbpprop:octSun ?octSun .
    #    
    #                ?city dbpprop:novHighC ?novHighC .
    #                ?city dbpprop:novLowC ?novLowC .
    #                ?city dbpprop:novMeanC ?novMeanC .
    #                ?city dbpprop:novPrecipitationDays ?novPrecipitationDays .
    #                ?city dbpprop:novPrecipitationMm ?novPrecipitationMm .
    #                ?city dbpprop:novSun ?novSun .
    #
    #                ?city dbpprop:decHighC ?decHighC .
    #                ?city dbpprop:decLowC ?decLowC .
    #                ?city dbpprop:decMeanC ?decMeanC .
    #                ?city dbpprop:decPrecipitationDays ?decPrecipitationDays .
    #                ?city dbpprop:decPrecipitationMm ?decPrecipitationMm .
    #                ?city dbpprop:decSun ?decSun }
     
        
        for city in cities:
            name = city['name']['value']
            print 'Saving information for city', name, ',', country.name
            c, created = City.objects.get_or_create(name=name, defaults={'country':country})
            c.description = city['abstract']['value']
            c.population = city['population']['value']
            c.lat = city['lat']['value']
            c.lng = city['long']['value']
            c.wikipedia_url = 'http://en.wikipedia.org/w/index.php?title='+name.replace(" ", "_")+'&printable=yes'
            c.wikitravel_url = 'http://wikitravel.org/wiki/en/index.php?title='+name.replace(" ", "_")+'&printable=yes'
    
            c.timezone = getValue(city, 'timezone')
            c.timezone_dst = getValue(city, 'timezone_dst')            
            c.rdf = ws.getResource(name)
    
    #        c.janHighC = getValue(city, 'janHighC')
    #        c.janLowC = getValue(city, 'janLowC')
    #        c.janMeanC = getValue(city, 'janMeanC')
    #        c.janPrecipitationDays = getValue(city, 'janPrecipitationDays')
    #        c.janPrecipitationMm = getValue(city, 'janPrecipitationMm')
    #        c.janSun = getValue(city, 'janSun')
    #        
    #        c.febHighC = getValue(city, 'febHighC')
    #        c.febLowC = getValue(city, 'febLowC')
    #        c.febMeanC = getValue(city, 'febMeanC')
    #        c.febPrecipitationDays = getValue(city, 'febPrecipitationDays')
    #        c.febPrecipitationMm = getValue(city, 'febPrecipitationMm')
    #        c.febSun = getValue(city, 'febSun')
    #
    #        c.marHighC = getValue(city, 'marHighC')
    #        c.marLowC = getValue(city, 'marLowC')
    #        c.marMeanC = getValue(city, 'marMeanC')
    #        c.marPrecipitationDays = getValue(city, 'marPrecipitationDays')
    #        c.marPrecipitationMm = getValue(city, 'marPrecipitationMm')
    #        c.marSun = getValue(city, 'marSun')
    #        
    #        c.aprHighC = getValue(city, 'aprHighC')
    #        c.aprLowC = getValue(city, 'aprLowC')
    #        c.aprMeanC = getValue(city, 'aprMeanC')
    #        c.aprPrecipitationDays = getValue(city, 'aprPrecipitationDays')
    #        c.aprPrecipitationMm = getValue(city, 'aprPrecipitationMm')
    #        c.aprSun = getValue(city, 'aprSun')
    #        
    #        c.mayHighC = getValue(city, 'mayHighC')
    #        c.mayLowC = getValue(city, 'mayLowC')
    #        c.mayMeanC = getValue(city, 'mayMeanC')
    #        c.mayPrecipitationDays = getValue(city, 'mayPrecipitationDays')
    #        c.mayPrecipitationMm = getValue(city, 'mayPrecipitationMm')
    #        c.maySun = getValue(city, 'maySun')
    #
    #        c.junHighC = getValue(city, 'junHighC')
    #        c.junLowC = getValue(city, 'junLowC')
    #        c.junMeanC = getValue(city, 'junMeanC')
    #        c.junPrecipitationDays = getValue(city, 'junPrecipitationDays')
    #        c.junPrecipitationMm = getValue(city, 'junPrecipitationMm')
    #        c.junSun = getValue(city, 'junSun')
    #
    #        c.julHighC = getValue(city, 'julHighC')
    #        c.julLowC = getValue(city, 'julLowC')
    #        c.julMeanC = getValue(city, 'julMeanC')
    #        c.julPrecipitationDays = getValue(city, 'julPrecipitationDays')
    #        c.julPrecipitationMm = getValue(city, 'julPrecipitationMm')
    #        c.julSun = getValue(city, 'julSun')
    #
    #        c.augHighC = getValue(city, 'augHighC')
    #        c.augLowC = getValue(city, 'augLowC')
    #        c.augMeanC = getValue(city, 'augMeanC')
    #        c.augPrecipitationDays = getValue(city, 'augPrecipitationDays')
    #        c.augPrecipitationMm = getValue(city, 'augPrecipitationMm')
    #        c.augSun = getValue(city, 'augSun')
    #
    #        c.sepHighC = getValue(city, 'sepHighC')
    #        c.sepLowC = getValue(city, 'sepLowC')
    #        c.sepMeanC = getValue(city, 'sepMeanC')
    #        c.sepPrecipitationDays = getValue(city, 'sepPrecipitationDays')
    #        c.sepPrecipitationMm = getValue(city, 'sepPrecipitationMm')
    #        c.sepSun = getValue(city, 'sepSun')
    #
    #        c.octHighC = getValue(city, 'octHighC')
    #        c.octLowC = getValue(city, 'octLowC')
    #        c.octMeanC = getValue(city, 'octMeanC')
    #        c.octPrecipitationDays = getValue(city, 'octPrecipitationDays')
    #        c.octPrecipitationMm = getValue(city, 'octPrecipitationMm')
    #        c.octSun = getValue(city, 'octSun')
    #        
    #        c.novHighC = getValue(city, 'novHighC')
    #        c.novLowC = getValue(city, 'novLowC')
    #        c.novMeanC = getValue(city, 'novMeanC')
    #        c.novPrecipitationDays = getValue(city, 'novPrecipitationDays')
    #        c.novPrecipitationMm = getValue(city, 'novPrecipitationMm')
    #        c.novSun = getValue(city, 'novSun')
    #
    #        c.decHighC = getValue(city, 'decHighC')
    #        c.decLowC = getValue(city, 'decLowC')
    #        c.decMeanC = getValue(city, 'decMeanC')
    #        c.decPrecipitationDays = getValue(city, 'decPrecipitationDays')
    #        c.decPrecipitationMm = getValue(city, 'decPrecipitationMm')
    #        c.decSun = getValue(city, 'decSun')
    
            c.save()

def getValue(dict, key):
    try:
        return dict[key]['value']
    except KeyError:
        pass