from rdflib.Graph import Graph
from data_fetch import webservices as ws

class SPARQLRuleEngine:
    context = Graph()
    resources = []

    def __init__(self, context):
        self.context.parseString(context)
        
    def in_context(self, o):
        for (a, b, c) in self.context:
            if o is b:
                return True
        return False

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
    
    def process_rule(self, head, body):
        variables = []
        filters = []
        
        for s in body:
            var = self.find_variables(variables, s)
            
            if len(var) == 0:
                filters.append(s)
            else:
                variables = dict(var.items() + variables.items())
        
        print variables
        
        sparql_query = """
            CONSTRUCT { """+head+""" . }
            WHERE { """
        
        for f in filters:
            sparql_query += f + " . \n"
            
        sparql_query += """ FILTER()
            } """

        # TODO: Aniadir valores en filter (valores de variables)
            
        print sparql_query
        
        result = ws.querySPARQLtoJSON(sparql_query)
        print result
        return result
