from django.conf.urls.defaults import *
from django.contrib.auth.decorators import login_required

from django.contrib import admin
from django.contrib import databrowse

urlpatterns = patterns('',
    # Own apps
    (r'^telemaco/', include('Telemaco_server.telemaco.urls')),
    (r'^telemaco/api/',   include('Telemaco_server.api.urls')),
    
    # URLs for Admin and Databrowse
    (r'^admin/doc/', include('django.contrib.admindocs.urls')),
    (r'^admin/', include(admin.site.urls)),
    (r'^databrowse/(.*)', login_required(databrowse.site.root)),
    #(r'^databrowse/(.*)', databrowse.site.root),
)
