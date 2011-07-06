#!/usr/bin/python

import os
import webservices
from telemaco.models import Currency 

os.environ['DJANGO_SETTINGS_MODULE'] = 'settings'

print "Executing data_fetch process..."

# We fetch data entity by entity
# City

# Country

# Currency
currencies = Currency.objects.all()
for currency in currencies:
    print "Getting info for currency: ", currency.name
    currency.rate = webservices.getCurrencyExchange("EUR", currency.code)
    currency.save()

# Language

# Place
# Item (place)

# Weather

print "Finished process data_fetch."