from piston.handler import BaseHandler
from piston.utils import rc
from telemaco.models import User

class UserHandler(BaseHandler):
    allowed_methods = ('GET', 'POST', 'PUT')
    model = User
    
    def read(self, request, object_id=None):
        # Check password? 
        try:
            if object_id:
                return self.model.objects.get(pk=object_id)
            else:
                return self.model.objects.all() # Or base.filter(...)
        except Exception:
            return rc.NOT_FOUND
    
    def create(self, request):
        # Sing-up
        if request.content_type:
            object = self.model(username=request.data['username'],
                                password=request.data['password'],
                                                                )
            object.save()
            
            #for comment in data['comments']:
            #    Comment(parent=object, content=comment['content']).save()
            
            return rc.CREATED
        else:
            super(self.model, self).create(request)
            
    def update(self, request, object_id=None):
        # Change password
        # Change profile
        if object_id:
            obj = self.model.objects.get(pk=object_id)
            
            if request.user.id == obj.user_id: 
                obj.password = request.data['password']
                obj.city = request.data['city']
                obj.save()
                return rc.ALL_OK
            else:
                return rc.FORBIDDEN
        else:
            return rc.BAD_REQUEST
        