from xml.dom.minidom import parse
from rdflib.Graph import Graph


# Uses the forward chaining method, although the backward chaining method and a mixed approach have been considered
# http://en.wikipedia.org/wiki/Inference_engine
# http://en.wikipedia.org/wiki/Rete_algorithm
# http://en.wikipedia.org/wiki/Semantic_reasoner

class RuleEngine:
    rules = ['rule1.swrl', 'rule2.swrl']
    working_memory = Graph()
    
    def execute(self):
        
        for rule in self.rules:
            print 'Analizing file', rule
#            dom = parse(rule)
#            document = dom.getElementsByTagName('ruleml:imp')[0]
#            body = document.getElementsByTagName('ruleml:_body')[0]
#            
#            operations = body.getElementsByTagName('swrlx:individualPropertyAtom')
#            for o in operations:
#                operation = o.attributes['swrlx:property'].nodeValue
#                print operation
#
#            head = document.getElementsByTagName('ruleml:_head')[0]
#            action = head.getElementsByTagName('swrlx:individualPropertyAtom')[0]
#            operation = action.attributes['swrlx:property'].nodeValue
#            print operation
#            result = locals()[operation]()

            
            g = Graph()
            g.parse('swrl1.n3', format='n3')
           
            for stmt in g:
                print stmt
                
                
e = RuleEngine()
e.execute()