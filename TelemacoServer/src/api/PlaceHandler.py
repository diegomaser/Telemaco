from piston.handler import BaseHandler
from piston.utils import rc

from telemaco.models import Place

class PlaceHandler(BaseHandler):
    allowed_methods = ('GET', 'POST', 'PUT', 'DELETE')
    model = Place
    
    def read(self, request, object_id=None):
        if object_id:
            return self.model.objects.get(pk=object_id)
        else:
            return self.model.objects.all()
    
    def create(self, request):
        return rc.CREATED
    
    def update(self, request, object_id=None):
        return rc.ALL_OK
    
    def delete(self, request, object_id=None):
        obj = self.model.objects.get(pk=object_id)
        obj.delete()
        return rc.DELETED
