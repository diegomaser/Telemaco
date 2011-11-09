from django.conf.urls.defaults import include
from django.conf.urls.defaults import patterns
from django.views.generic.simple import redirect_to
from django.contrib.staticfiles.urls import staticfiles_urlpatterns

import telemaco.urls
import api.urls

urlpatterns = patterns('',
    # Own apps
    (r'^telemaco/', include(telemaco.urls)),
    (r'^telemaco_api/',   include(api.urls)),
    
    (r'^accounts/login/$', 'django.contrib.auth.views.login'),
    (r'^accounts/logout/$', 'django.contrib.auth.views.logout_then_login'),
    
    (r'^$', redirect_to, dict(url='telemaco/', permanent=False)),
)

urlpatterns += staticfiles_urlpatterns()