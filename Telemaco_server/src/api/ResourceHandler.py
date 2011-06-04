from piston.handler import BaseHandler
from piston.utils import rc

class ResourceHandler(BaseHandler):
    allowed_methods = ('GET',)
    fields = ('name', 'url')
    base_url = 'http://10.0.0.2:8000/telemaco_api/'

    def read(self, request):
        try:
            resources = [{'name':'City', 'url':self.base_url+'city'},
                         {'name':'CitySearch', 'url':self.base_url+'city_search'},
                         {'name':'Trip', 'url':self.base_url+'trip'},
                         {'name':'Place', 'url':self.base_url+'place'},
                         {'name':'User', 'url':self.base_url+'user'}]
            return resources
        except Exception:
            return rc.NOT_FOUND