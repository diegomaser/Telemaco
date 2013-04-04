from django.conf.urls.defaults import patterns
from django.conf.urls.defaults import url

from piston.resource import Resource
from piston.authentication import HttpBasicAuthentication

from CityHandler import CityHandler
from CountryHandler import CountryHandler
from CitySearchHandler import CitySearchHandler
from TripHandler import TripHandler
from UserHandler import UserHandler
from PlaceHandler import PlaceHandler
from RecommendationHandler import RecommendationHandler
from ResourceHandler import ResourceHandler
from RegistrationHandler import RegistrationHandler

auth = HttpBasicAuthentication(realm="TelemacoAPI")

registration_handler = Resource(RegistrationHandler)
user_handler = Resource(UserHandler, auth)
city_handler = Resource(CityHandler)
country_handler = Resource(CountryHandler)
citysearch_handler = Resource(CitySearchHandler)
trip_handler = Resource(TripHandler, auth)
#place_handler = Resource(PlaceHandler, auth)
place_handler = Resource(PlaceHandler)
#recommendation_handler = Resource(RecommendationHandler, auth)
recommendation_handler = Resource(RecommendationHandler)
resource_handler = Resource(ResourceHandler)

urlpatterns = patterns('',
    #####
    # AQUI VA DEFINIDA LA INTERFAZ REST
    #####
    
    url(r'^register', registration_handler, { 'emitter_format': 'json' }),
    url(r'^user', user_handler, { 'emitter_format': 'json' }),
    
    url(r'^city/(?P<object_id>[^/]+)', city_handler, { 'emitter_format': 'json' }),
    url(r'^city_search/(?P<country_id>[^/]+)/(?P<city>\w+)', citysearch_handler, { 'emitter_format': 'json' }),
    url(r'^city_search/(?P<country_id>[^/]+)', citysearch_handler, { 'emitter_format': 'json' }),
    
    url(r'^trip/(?P<object_id>[^/]+)', trip_handler, { 'emitter_format': 'json' }),
    url(r'^trip', trip_handler, { 'emitter_format': 'json' }),
    
    url(r'^place/(?P<object_id>[^/]+)', place_handler, { 'emitter_format': 'json' }),
    url(r'^recommendation/(?P<object_id>[^/]+)', recommendation_handler, { 'emitter_format': 'json' }),
    
    url(r'', resource_handler, {'emitter_format' : 'json'} ),
)
