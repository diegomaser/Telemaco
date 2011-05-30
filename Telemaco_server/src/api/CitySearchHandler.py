from piston.handler import BaseHandler
from piston.utils import rc

from telemaco.models import CitySearch

class CitySearchHandler(BaseHandler):
    allowed_methods = ('GET',)
    fields=('id', 'name',)
    model = CitySearch


    def read(self, request, country_id=None, city=None):
        try:
            if country_id:
                #cities = self.model.objects.filter(country = country_id)
                # Cities of a country
                c1 = CitySearch()
                c2 = CitySearch()
                c1.id=1
                c1.name='Madrid'
                c2.id=2
                c2.name='Barcelona'
                return [c1, c2]
            else:
                return rc.BAD_REQUEST
        except Exception:
            return rc.NOT_FOUND