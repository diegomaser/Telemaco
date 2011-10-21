import xml.sax

def open(rule_file):
    print 'Analizing rule_file', rule_file

    parser = xml.sax.make_parser()
    handler = SWRLHandler()
    parser.setContentHandler(handler)
    
    try:
        parser.parse(open(rule_file, 'r'))
    except Exception, e:
        print 'Error while parsing rule_file', rule_file, 'Description:', str(e)
        
    print 'Antecedent:', handler.antecedent
    print 'Consequent:', handler.consequent

    return (handler.antecedent, handler.consequent)

class SWRLHandler(xml.sax.ContentHandler):
    def __init__(self):
        self.antecedent = []
        self.consequent = []
        self.body = False
        self.head = False
        self.property = False
        self.var = False
        
        self.subject = None
        self.object = None
        self.predicate = None
      
    def startElement(self, name, attrs):
        if name == 'ruleml:_body':
            self.body = True
        elif name == 'ruleml:_head':
            self.head = True
        elif name == 'swrlx:individualPropertyAtom':
            self.property = True
            self.object = attrs['swrlx:property']
        elif name == 'ruleml:var':
            self.var = True

    def characters(self, content):
        if self.var:
            if self.subject is None:
                self.subject = content
            elif self.predicate is None:
                self.predicate = content

    def endElement(self, name):
        if name == 'ruleml:_body':
            self.body = False
        elif name == 'ruleml:_head':
            self.head = False 
        elif name == 'swrlx:individualPropertyAtom':
            if self.head:
                self.consequent.append((self.subject,self.object,self.predicate))
            elif self.body:
                self.antecedent.append((self.subject,self.object,self.predicate))
            
            self.property = False
            self.subject = None
            self.object = None
            self.predicate = None
        elif name == 'ruleml:var':
            self.var = False
