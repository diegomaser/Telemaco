from django.conf.urls.defaults import patterns
from django.conf.urls.defaults import url

from piston.resource import Resource
from piston.authentication import HttpBasicAuthentication

from CityHandler import CityHandler
from CitySearchHandler import CitySearchHandler
from TripHandler import TripHandler
from UserHandler import UserHandler
from PlaceHandler import PlaceHandler

user_handler = Resource(UserHandler)
city_handler = Resource(CityHandler)
citysearch_handler = Resource(CitySearchHandler)
trip_handler = Resource(TripHandler)
place_handler = Resource(PlaceHandler)

urlpatterns = patterns('',
    #####
    # AQUI VA DEFINIDA LA INTERFAZ REST
    #####
    
    #url(r'^user/(?P<object_id>[^/]+)/', user_handler, { 'emitter_format': 'json' }),
    #url(r'^users/', user_handler, { 'emitter_format': 'json' }),
    
    url(r'^city/(?P<object_id>[^/]+)/', city_handler, { 'emitter_format': 'json' }),

    url(r'^cities_search/(?P<country_id>[^/]+)/(?P<city>\w+)', citysearch_handler, { 'emitter_format': 'json' }),
    url(r'^cities_search/(?P<country_id>[^/]+)', citysearch_handler, { 'emitter_format': 'json' }),
    
#    url(r'^trip/(?P<object_id>[^/]+)/', trip_handler, { 'emitter_format': 'json' }),
#    url(r'^trips/', trip_handler, { 'emitter_format': 'json' }),
    
#    url(r'^places/(?P<object_id>[^/]+)/', place_handler, { 'emitter_format': 'json' }),
#    url(r'^place/', place_handler, { 'emitter_format': 'json' }),
)
