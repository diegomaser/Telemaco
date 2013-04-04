from piston.handler import BaseHandler
from piston.utils import rc

from telemaco.models import Place

class PlaceHandler(BaseHandler):
    allowed_methods = ('GET',)
    model = Place
    fields = ('id', 'name', 'description', 'lat', 'lng', 'wikipedia_url', 'city_id')
    
    def read(self, request, object_id=None):
        places = []
        if object_id:
            p = Place.objects.filter(city=object_id)
            places.extend(p)                    
            return places
        else:
            return rc.BAD_REQUEST
