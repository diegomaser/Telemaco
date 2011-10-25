from piston.handler import BaseHandler
from piston.utils import rc

from telemaco.models import Place
from rule_engine.rule_engine import RuleEngine

class RecommendationHandler(BaseHandler):
    allowed_methods = ('GET')
    model = Place
    
    rules = ['example1.xml',
             'rule1.xml',
             'rule2.xml']
    
    def read(self, request, object_id=None):
        recommendations = []
        
        if object_id:
            return self.model.objects.get(pk=object_id)
        else:
            return self.model.objects.all()
    
        for rule in self.rules:
            body, head = self.open(rule)
            
            #para los lugares que estan en las ciudades del viaje del que se piden las recomendaciones
            places = None#models.places.all().filter(trip=0)
            user = None#models.User.all().filter(user=0)
            
            for resource in places:
                variables = {}
                s = RuleEngine(resource.rdf, user.rdf)
                var = s.process_rule(body, variables)
                
                if len(var)>0:
                    # el grafo resultante no es persistible en el servidor
                    # porque depende tanto del perfil FOAF como del recurso RDF --> seguro??
                    for values in var:
                        pass
                        #resource.addTriple(head)
                        if ('', 'isRecommended', 'true') in values:
                        #if head in values:                            
                            recommendations.append(values)
        return self.recommendations