#!/usr/bin/python

import os
os.environ['DJANGO_SETTINGS_MODULE'] = 'settings'

from data_fetch import foafbook
from data_fetch import countries
from data_fetch import cities
from data_fetch import languages
from data_fetch import places
from data_fetch import currencies

def main():
    # We fetch data entity by entity
    print "Executing data_fetch process..."
    countries.getCountries()
    languages.getLanguages()
    cities.getCities()
    places.getPlaces()
    currencies.getCurrencies()
    foafbook.updateProfiles()
    print "Finished process data_fetch."

if __name__ == '__main__':
    main()