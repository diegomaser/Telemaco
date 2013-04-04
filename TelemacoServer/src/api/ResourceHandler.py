from piston.handler import BaseHandler
from piston.utils import rc

class ResourceHandler(BaseHandler):
    allowed_methods = ('GET',)
    fields = ('name', 'url')
    
    def read(self, request):
        base_url = 'http://' + request.META['HTTP_HOST'] + '/telemaco_api/'
        
        try:
            resources = [{'name':'City', 'url':base_url+'city'},
                         {'name':'Country', 'url':base_url+'country'},
                         {'name':'CitySearch', 'url':base_url+'city_search'},
                         {'name':'Trip', 'url':base_url+'trip'},
                         {'name':'Place', 'url':base_url+'place'},
                         {'name':'User', 'url':base_url+'user'},
                         {'name':'Recommendation', 'url':base_url+'recommendation'},
                         {'name':'Register', 'url':base_url+'register'}]
            
            return resources
        except Exception:
            return rc.NOT_FOUND