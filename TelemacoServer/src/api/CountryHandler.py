from piston.handler import BaseHandler
from piston.utils import rc

from telemaco.models import Country

class CountryHandler(BaseHandler):
    allowed_methods = ('GET',)
    model = Country
    
    fields = ('id', 'name', 'description',
              'wikipedia_url', 'wikitravel_url',
              'plug_frequency', 'plug_voltage',
              'currency', 'plug', 'languages')
    
    def read(self, request, object_id=None):
        try:
            if object_id:
                return self.model.objects.get(pk=object_id)
            else:
                return rc.BAD_REQUEST
        except Exception:
            return rc.NOT_FOUND