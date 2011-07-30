#!/usr/bin/python

import webservices as ws

try: import simplejson as json
except ImportError: import json
import datetime

ACCESS_TOKEN = '167808633292329%7C8d6769f3d1e05caa44b1332f.3-611539591%7CCiU4fQZxbd5u1RD8IgOlWJLaSDo'
BASE_URL = 'https://graph.facebook.com/'

# Usage of Facebook Graph API (from Facebook Documentation)
#Users: https://graph.facebook.com/me
#Pages: https://graph.facebook.com/cocacola
#Likes: https://graph.facebook.com/me/likes?access_token=2227470867|2.AQASEN1gTG1oN2U3.3600.1310245200.0-611539591|TlXJgC6WgKz8gHNtr-6Pd_CW7uM
#Events: https://graph.facebook.com/331218348435
#Groups: https://graph.facebook.com/195466193802264
#Checkin: https://graph.facebook.com/414866888308?access_token=2227470867|2.AQASEN1gTG1oN2U3.3600.1310245200.0-611539591|TlXJgC6WgKz8gHNtr-6Pd_CW7uM
#Friends: https://graph.facebook.com/me/friends?access_token=2227470867|2.AQASEN1gTG1oN2U3.3600.1310245200.0-611539591|TlXJgC6WgKz8gHNtr-6Pd_CW7uM
#Movies: https://graph.facebook.com/me/movies?access_token=2227470867|2.AQASEN1gTG1oN2U3.3600.1310245200.0-611539591|TlXJgC6WgKz8gHNtr-6Pd_CW7uM
#Music: https://graph.facebook.com/me/music?access_token=2227470867|2.AQASEN1gTG1oN2U3.3600.1310245200.0-611539591|TlXJgC6WgKz8gHNtr-6Pd_CW7uM
#Books: https://graph.facebook.com/me/books?access_token=2227470867|2.AQASEN1gTG1oN2U3.3600.1310245200.0-611539591|TlXJgC6WgKz8gHNtr-6Pd_CW7uM
#Notes: https://graph.facebook.com/me/notes?access_token=2227470867|2.AQASEN1gTG1oN2U3.3600.1310245200.0-611539591|TlXJgC6WgKz8gHNtr-6Pd_CW7uM
#Events: https://graph.facebook.com/me/events?access_token=2227470867|2.AQASEN1gTG1oN2U3.3600.1310245200.0-611539591|TlXJgC6WgKz8gHNtr-6Pd_CW7uM
#Groups: https://graph.facebook.com/me/groups?access_token=2227470867|2.AQASEN1gTG1oN2U3.3600.1310245200.0-611539591|TlXJgC6WgKz8gHNtr-6Pd_CW7uM
#Checkins: https://graph.facebook.com/me/checkins?access_token=2227470867|2.AQASEN1gTG1oN2U3.3600.1310245200.0-611539591|TlXJgC6WgKz8gHNtr-6Pd_CW7uM

def getFoafProfile(access_token):
    me = json.loads(ws.queryFacebookGraph('me', access_token))
    likings = json.loads(ws.queryFacebookGraph('me/likes', access_token))
    groups = json.loads(ws.queryFacebookGraph('me/groups', access_token))
    friends = json.loads(ws.queryFacebookGraph('me/friends', access_token))
    
    foaf = """
<?xml version="1.0" encoding="iso-8859-1"?>
<rdf:RDF
xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
xmlns:foaf="http://xmlns.com/foaf/0.1/"
xmlns:geo="http://www.w3.org/2003/01/geo/wgs84_pos#"
xmlns:dc="http://purl.org/dc/elements/1.1/">

<foaf:PersonalProfileDocument rdf:about="">
<foaf:maker rdf:resource="#me"/>
<foaf:primaryTopic rdf:resource="#me"/>
</foaf:PersonalProfileDocument>

"""
    # Personal info
    foaf += '<foaf:Person rdf:ID="me">\n'
    foaf += '<foaf:name>'+unicode(me['name'])+'</foaf:name>\n'
    foaf += '<foaf:givenname>'+unicode(me['first_name'])+'</foaf:givenname>\n'
    foaf += '<foaf:family_name>'+unicode(me['last_name'])+'</foaf:family_name>\n'
    foaf += '<foaf:depiction rdf:resource="https://graph.facebook.com/'+unicode(me['id'])+'/picture"/>\n'
    
    try:
        foaf += '<foaf:gender>'+unicode(me['gender'])+'</foaf:gender>\n'
    except KeyError:
        pass
    
    # Birthday and age info
    birth = me['birthday']
    birthday = datetime.date(int(birth.split('/')[2]), int(birth.split('/')[0]), int(birth.split('/')[1]))
    foaf += '<foaf:birthday>' + birthday.isoformat() + '</foaf:birthday>\n'
    age = datetime.date.today().year - birthday.year # This has been done intentionally
    foaf += '<foaf:age>' + unicode(age) + '</foaf:age>\n'
    
    # Location
    try:
        lat, lng = ws.getCoordinates(unicode(me['location']['name']))
        foaf += '<foaf:based_near><geo:Point geo:lat="'+unicode(lat)+'" geo:long="'+unicode(lng)+'"/></foaf:based_near>\n'
    except KeyError:
        pass
    
    # Interests
    for liking in likings['data']:
        foaf += '<foaf:topic_interest>'+unicode(liking['name'])+'</foaf:topic_interest>\n'        
    for group in groups['data']:
        foaf += '<foaf:topic_interest>'+unicode(group['name'])+'</foaf:topic_interest>\n'
    
    # Knows
    for f in friends['data']:
        friend = json.loads(ws.queryFacebookGraph(f['id'], access_token))
        friend_likings = json.loads(ws.queryFacebookGraph(f['id']+'/likes', access_token))
        friend_groups = json.loads(ws.queryFacebookGraph(f['id']+'/groups', access_token))
        
        foaf += '<foaf:knows><foaf:Person>\n'
        foaf += '<foaf:name>'+unicode(friend['name'])+'</foaf:name>\n'
        foaf += '<foaf:givenname>'+unicode(friend['first_name'])+'</foaf:givenname>\n'
        foaf += '<foaf:family_name>'+unicode(friend['last_name'])+'</foaf:family_name>\n'
        foaf += '<foaf:depiction rdf:resource="https://graph.facebook.com/'+unicode(friend['id'])+'/picture"/>\n'
        try:
            foaf += '<foaf:gender>'+unicode(friend['gender'])+'</foaf:gender>\n'
        except KeyError:
            pass
            
        # Location
        #try:
        #    lat, lng = ws.getCoordinates(unicode(friend['location']['name']))
        #    foaf += '<foaf:based_near><geo:Point geo:lat="'+unicode(lat)+'" geo:long="'+unicode(lng)+'"/></foaf:based_near>\n'
        #except KeyError:
        #    pass

        for liking in friend_likings['data']:
            foaf += '<foaf:topic_interest>'+unicode(liking['name'])+'</foaf:topic_interest>\n'
        for group in friend_groups['data']:
            foaf += '<foaf:topic_interest>'+unicode(group['name'])+'</foaf:topic_interest>\n'
        
        foaf += '</foaf:Person></foaf:knows>\n'
    
    foaf += "</foaf:Person></rdf:RDF>"
    print foaf.encode('utf-8')
    return foaf
