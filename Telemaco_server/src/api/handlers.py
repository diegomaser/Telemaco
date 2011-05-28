from piston.handler import BaseHandler
from piston.utils import rc

from telemaco.models import CitySearch
from telemaco.models import City
from telemaco.models import User
from telemaco.models import Country
from telemaco.models import Trip
from telemaco.models import Place

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

class CityHandler(BaseHandler):
    allowed_methods = ('GET',)
    model = City
    
    def read(self, request, object_id=None):
        try:
            if object_id:
                return self.model.objects.get(pk=object_id)
            else:
                return rc.BAD_REQUEST
        except Exception:
            return rc.NOT_FOUND
    
class CountryHandler(BaseHandler):
    allowed_methods = ('GET',)
    model = Country
    
    def read(self, request, object_id=None):
        if object_id:
            return self.model.objects.get(pk=object_id)
        else:
            return self.model.objects.all()

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
