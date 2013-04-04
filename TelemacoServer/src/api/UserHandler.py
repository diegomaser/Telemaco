from piston.handler import BaseHandler
from piston.utils import rc
from telemaco.models import User

class UserHandler(BaseHandler):
    allowed_methods = ('PUT')
    model = User
    
    def read(self, request):
        try:
            obj = self.model.objects.get(username=request.user) 
            return obj
        except Exception, e:
            return rc.BAD_REQUEST

    def update(self, request):
        # Change access_token
        try:
            print "Update user:", request.data
            obj = self.model.objects.get(username=request.user)
            obj.facebook_access_token = request.data['access_token']
            #obj.password = request.data['password']
            #obj.city = request.data['city']
            obj.save()
            return rc.ALL_OK
        except Exception, e:
            print str(e)
            return rc.BAD_REQUEST
