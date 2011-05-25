from django.conf.urls.defaults import include
from django.conf.urls.defaults import patterns

import telemaco.urls
import api.urls

urlpatterns = patterns('',
    # Own apps
    (r'^telemaco/', include(telemaco.urls)),
    (r'^telemaco_api/',   include(api.urls)),
)