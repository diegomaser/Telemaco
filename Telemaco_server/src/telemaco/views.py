# Create your views here.
from django.shortcuts import render_to_response
from django.views.generic.create_update import *
from django.views.generic.list_detail import *
#import api

from django.http import HttpResponseRedirect
from django.contrib.auth.forms import UserCreationForm
from django.forms.models import ModelFormMetaclass
#from models import Trip, TripForm

# Consultas con SPARQL
