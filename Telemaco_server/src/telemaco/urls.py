from django.conf.urls.defaults import patterns
from django.conf.urls.defaults import include

from django.contrib.auth.decorators import login_required

# Model
from models import Country
from models import City
from models import Currency
from models import Item
from models import Language
from models import Note
from models import Place
from models import Plug
from models import Transport
from models import Trip
from models import User
from models import CityVisit
from models import PlaceVisit

# Admin and databrowse interface
from django.contrib import admin
from django.contrib import databrowse

admin.autodiscover()
databrowse.site.register(Country)
databrowse.site.register(City)
databrowse.site.register(Currency)
databrowse.site.register(Item)
databrowse.site.register(Language)
databrowse.site.register(Note)
databrowse.site.register(Place)
databrowse.site.register(Plug)
databrowse.site.register(Transport)
databrowse.site.register(Trip)
databrowse.site.register(User)
databrowse.site.register(CityVisit)
databrowse.site.register(PlaceVisit)

urlpatterns = patterns('',
                       # URLs for Admin and Databrowse
                       (r'^admin/doc/', include('django.contrib.admindocs.urls')),
                       (r'^admin/', include(admin.site.urls)),
                       (r'^databrowse/(.*)', login_required(databrowse.site.root)),
                       (r'^$', 'django.views.generic.simple.direct_to_template', dict(template='index.html')),
                       
                       #(r'^guide/(?P<country>\w+)/(?P<city>\w+)/$', 'guias.guide.views.viewGenericGuide'),
                       #(r'^guide/(?P<object_id>\d+)/$', 'guide.views.viewGuide'),
                       #(r'^trip/$', 'django.views.generic.list_detail.object_list', dict(queryset=Trip.objects.all())),
                       #(r'^trip/$', 'guide.views.list_trip', ),
                       #(r'^trip/(?P<object_id>\d+)/$', 'django.views.generic.list_detail.object_detail', dict(queryset=Trip.objects.all())),
                       #(r'^transport/(?P<object_id>\d+)/edit/$', 'django.views.generic.create_update.update_object', dict(model=Transport, post_save_redirect='/trip/')),
                       #(r'^transport/(?P<object_id>\d+)/delete/$', 'django.views.generic.create_update.delete_object', dict(model=Transport, post_delete_redirect='/trip/')),                   
)