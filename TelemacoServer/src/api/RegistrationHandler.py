from piston.handler import BaseHandler
from piston.utils import rc
from telemaco.models import User
from django.db.utils import IntegrityError

class RegistrationHandler(BaseHandler):
    class _UserProxy(User): pass
    
    allowed_methods = ('POST')
    model = _UserProxy
    
    def create(self, request):
        try:
            # Sing-up
            print "Create user:", request.data
            
            obj = User(username=request.data['username'])
            obj.set_password(request.data['password'])
            obj.save()
                                
            return rc.CREATED
        except IntegrityError, e:
            print e
            return rc.DUPLICATE_ENTRY        
        except Exception, e:
            print e
            return rc.BAD_REQUEST
