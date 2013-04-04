from rdflib.Graph import Graph

# Uses the forward chaining method, although the backward chaining method and a mixed approach have been considered
# http://en.wikipedia.org/wiki/Inference_engine
# http://en.wikipedia.org/wiki/Rete_algorithm
# http://en.wikipedia.org/wiki/Semantic_reasoner

class RuleEngine:
    context = Graph()
    resource = Graph()
    
    def __init__(self, context, resource):
        self.context.parseString(context)
        self.resource.parseString(resource)
    
    def search(self, s=None, o=None, p=None):
        triples = []
        
        for (a, b, c) in self.context:
            if o is b:
                if s and p and s is a and p is c:
                    # variables s y p
                    triples.add((a, b, c))
                elif s and s is a:
                    # variable s 
                    triples.add((a, b, c))
                elif p and p is c:
                    # variable p
                    triples.add((a, b, c))
                else:
                    # no variable
                    triples.add((a, b, c))

        for (a, b, c) in self.resource:
            if o is b:
                if s and p and s is a and p is c:
                    # variables s y p
                    triples.add((a, b, c))
                elif s and s is a:
                    # variable s 
                    triples.add((a, b, c))
                elif p and p is c:
                    # variable p
                    triples.add((a, b, c))
                else:
                    # no variable
                    triples.add((a, b, c))
                    
        return triples
    
    def find_variables(self, variables, statement):
        triples = []
        var = []
        
        b = statement[1]
        
        if statement[0] in variables.keys() and statement[2] in variables.keys():
            a = variables[statement[0]]
            c = variables[statement[2]]
            triples = self.search(s=a, o=b, p=c)
        elif statement[0] in variables.keys():
            a = variables[statement[0]]
            triples = self.search(s=a, o=b)
        elif statement[2] in variables.keys():
            c = variables[statement[2]]
            triples = self.search(o=b, p=c)
        else:
            triples = self.search(o=b)
        
        for triple in triples:
            var.append({statement[0]:triple[0], statement[2]:triple[2]})
            
        return var    
    
    def process_rule(self, statements, variables = []):
        if len(statements) == 0:
            # caso base
            result = variables
        else:
            # caso general
            result = []
            for v in variables:
                var = self.find_variables(v, statements[0])
                for w in var:
                    d = dict(w.items() + v.items())
                    res = self.process_rule(statements[1:], d)
                    result.append(res)

        return result
