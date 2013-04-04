from piston.handler import BaseHandler
from piston.utils import rc

from telemaco.models import CitySearch
from telemaco.models import City

class CitySearchHandler(BaseHandler):
    allowed_methods = ('GET',)
    fields=('id', 'name',)
    model = CitySearch


    def read(self, request, country_id=None, city=None):
        if country_id:
            # Cities of a country
            items = []
            
            cities = City.objects.filter(country = (int(country_id)))

            if city:
                cities = cities.filter(name__startswith=city)
                
            cities = cities.order_by('name')
            
            for c in cities:
                city = CitySearch(id=c.id, name=c.name)
                items.append(city)

            if items:
                return items
            else:
                return rc.NOT_FOUND
        else:
            return rc.BAD_REQUEST