from difflib import SequenceMatcher
from telemaco.models import Place

#TODO: http://en.wikipedia.org/wiki/Approximate_string_matching
#TODO: http://seatgeek.com/blog/dev/fuzzywuzzy-fuzzy-string-matching-in-python

def ratio(stra, strb):
    m = SequenceMatcher(None, stra, strb)
    ratio = m.ratio()
    return round(ratio*100, 2)

def getPlace(topic):
    places = Place.objects.all()
    max_ratio = 0
    max_place = None
    for p in places:
        ratio = ratio(topic, p.name)
        if ratio > max_ratio:
            max_ratio = ratio
            max_place = p.name
    return max_place