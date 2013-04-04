from piston.handler import BaseHandler
from piston.utils import rc

from telemaco.models import Place
from telemaco.models import City
from rule_engine.SPARQLRuleEngine import SPARQLRuleEngine

class RecommendationHandler(BaseHandler):
    class _PlaceProxy(Place): pass
    
    allowed_methods = ('GET')
    model = _PlaceProxy
    fields = ('id', 'name', 'description', 'lat', 'lng', 'wikipedia_url', 'city_id')
    
    rules = ['example1.xml',
             'rule1.xml',
             'rule2.xml']
    
    def read(self, request, object_id=None):
        recommendations = []
        
        if object_id:
            city = City.objects.get(pk=object_id)
            recommendations.append(Place.objects.filter(pk=118))
            recommendations.append(Place.objects.filter(pk=147))
            recommendations.append(Place.objects.filter(pk=141))
            recommendations.append(Place.objects.filter(pk=166))
            recommendations.append(Place.objects.filter(pk=163))
            recommendations.append(Place.objects.filter(pk=183))
            recommendations.append(Place.objects.filter(pk=185))
            recommendations.append(Place.objects.filter(pk=254))

            #para los lugares que estan en las ciudades del viaje del que se piden las recomendaciones
            city = None#models.places.all().filter(trip=0)
            user = None#models.User.all().filter(user=0)

            #s = SPARQLRuleEngine(context=user.rdf) #, city
            #s = RuleEngine(resource=resource.rdf, context=user.rdf)
        
            #for rule in self.rules:
            #    body, head = self.open(rule)
            #    recomm = s.process_rule(head, body)
            #    recommendations.append(recomm)
            return recommendations
        else:
            rc.BAD_REQUEST
