from rdflib.Graph import Graph

# Uses the forward chaining method, although the backward chaining method and a mixed approach have been considered
# http://en.wikipedia.org/wiki/Inference_engine
# http://en.wikipedia.org/wiki/Rete_algorithm
# http://en.wikipedia.org/wiki/Semantic_reasoner

class RuleEngine:
    rules = ['rule1.swrl', 'rule2.swrl']
    working_memory = Graph()
    
    def execute(self):
        
        for r in self.rules:
            g = Graph()
            g.parse(r)
            
            for stmt in g:
                print stmt
                
                
                #result = locals()['recommend']()