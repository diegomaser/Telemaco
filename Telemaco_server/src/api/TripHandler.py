from piston.handler import BaseHandler
from piston.utils import rc

from telemaco.models import Trip
    
class TripHandler(BaseHandler):
    allowed_methods = ('GET', 'POST', 'PUT', 'DELETE')
    model = Trip
    
    def read(self, request, object_id=None):
        if object_id:
            obj = self.model.objects.get(pk=object_id)
            if obj.user_id == request.user.id:
                return obj
            else:
                return rc.FORBIDDEN
        else:
            return self.model.objects.filter(user_id = request.user.id)
    
    def create(self, request):
        return rc.CREATED
    
    def update(self, request, object_id=None):
        return rc.ALL_OK
    
    def delete(self, request, object_id=None):
        obj = self.model.objects.get(pk=object_id)
        if obj.user_id == request.user.id:
            obj.delete()
            return rc.DELETED
        else:
            return rc.FORBIDDEN
        