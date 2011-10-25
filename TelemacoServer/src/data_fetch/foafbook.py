#!/usr/bin/python

import webservices as ws
import datetime
import matching
from telemaco.models import User

#Usage of Facebook Graph API (from Facebook Documentation)
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

# Use the FQL query execution interface for testing purposes. http://developer.facebook.com/   

def updateProfiles(self):
    users = User.objects.all()
    
    for u in users:
        try:
            print 'Getting profile info for user', u.username
            if u.access_token:
                fb = FOAFBook(u.access_token)
                u.foaf_profile = fb.get_foaf_profile()
                u.save()
        except Exception, e:
            print 'Resource not available or incorrect access token for user', u.username, 'Error', e

class FOAFBook:
    access_token = None
    users_info = None
    likings_info = None
    pages_info = None
    foaf_profile = None
    
    def __init__(self, access_token):
        self.access_token = access_token
        
    def get_foaf_profile(self):
        self.users_info = self._execute_users_query()
        self.pages_info = self._execute_pages_query()
        self.likings_info = self._execute_likes_query()
        self.foaf_profile = self._generate_foaf_profile()
        return self.foaf_profile
    
    def _execute_users_query(self):
        users = """
        SELECT uid, first_name, last_name, name, pic_square, birthday_date, sex, hometown_location, current_location, significant_other_id
        FROM user
        WHERE uid = me()
        OR uid IN (SELECT uid2
                   FROM friend
                   WHERE uid1 = me())
        """
        return ws.queryFacebookFQL(users, self.access_token)
        
    def _execute_likes_query(self):
        likes = """
        SELECT uid, page_id, type
        FROM page_fan
        WHERE (uid = me()
               OR uid IN (SELECT uid2
                          FROM friend
                          WHERE uid1 = me()))
        AND (
            (type = "BAR") OR
            (type = "LANDMARK") OR
            (type = "CHURCH/RELIGIOUS ORGANIZATION") OR
            (type = "LOCAL BUSINESS") OR
            (type = "RESTAURANT/CAFE") OR
            (type = "CLUB") OR
            (type = "MOVIE THEATER") OR
            (type = "SCHOOL") OR
            (type = "ATTRACTIONS/THINGS TO DO") OR
            (type = "ARTS/ENTERTAINMENT/NIGHTLIFE") OR
            (type = "LOCAL/TRAVEL") OR
            (type = "RESTAURANT") OR
            (type = "EDUCATION") OR
            (type = "UNIVERSITY") OR
            (type = "SHOPPING/RETAIL") OR
            (type = "FOOD/BEVERAGES") OR
            (type = "BOOK STORE") OR
            (type = "THEME PARK") OR
            (type = "HOTEL") OR
            (type = "SPAS/BEAUTY/PERSONAL CARE") OR
            (type = "TRAVEL/LEISURE") OR
            (type = "TOURS/SIGHTSEEING") OR
            (type = "SMALL BUSINESS") OR
            (type = "ARTS/HUMANITIES") OR
            (type = "CITY") OR
            (type = "LIBRARY") OR
            (type = "PUBLIC PLACES") OR
            (type = "CONCERT VENUE") OR
            (type = "HEALTH/MEDICAL/PHARMACY") OR
            (type = "ENTERTAINMENT") OR
            (type = "MUSEUM/ART GALLERY") OR
            (type = "MUSEUM") OR
            (type = "FOOD")
        )
        """
        return ws.queryFacebookFQL(likes, self.access_token)
                
    def _execute_pages_query(self):
        pages = """
        SELECT page_id, name
        FROM pages
        WHERE page_id IN (SELECT page_id
                         FROM page_fan
                         WHERE (uid = me()
                         OR uid IN (SELECT uid2
                                   FROM friend
                                   WHERE uid1 = me()))
                        )
        """

        return ws.queryFacebookFQL(pages, self.access_token)
        
    def _generate_foaf_profile(self):
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
        for user in self.users_info:
            if user is self.users_info[0]:
                foaf += self._get_foaf_personalinfo(user)
                foaf += self._get_foaf_birthday(user)
                foaf += self._get_foaf_location(user)
                foaf += self._get_foaf_likings(user)     
    
            else:
                # Knows
                foaf += '<foaf:knows><foaf:Person>\n'
                foaf += self._get_foaf_personalinfo(user)
                #foaf += _get_foaf_location(user)
                foaf += self._get_foaf_likings(user)
                foaf += '</foaf:Person></foaf:knows>\n'
        
        foaf += "</foaf:Person></rdf:RDF>"
        #print foaf.encode('utf-8')
        return foaf
    
    def _get_foaf_likings(self, user):
        likings_foaf = ''
        for liking in self.likings_info['data']:
            if user['id'] is self.likings_info['user_id']:
                place = matching.getPlace(unicode(liking['name']))
                likings_foaf += '<foaf:topic_interest>'+place+'</foaf:topic_interest>\n'
        return likings_foaf 
    
    def _get_foaf_location(self, user):
        location_foaf = ''
        try:
            lat, lng = ws.getCoordinates(unicode(user['location']['name']))
            location_foaf += '<foaf:based_near><geo:Point geo:lat="'+unicode(lat)+'" geo:long="'+unicode(lng)+'"/></foaf:based_near>\n'
        except KeyError:
            pass
        return location_foaf
    
    def _get_foaf_personalinfo(self, user):
        personal_foaf = ''
        personal_foaf += '<foaf:Person rdf:ID="me">\n'
        personal_foaf += '<foaf:name>'+unicode(user['name'])+'</foaf:name>\n'
        personal_foaf += '<foaf:givenname>'+unicode(user['first_name'])+'</foaf:givenname>\n'
        personal_foaf += '<foaf:family_name>'+unicode(user['last_name'])+'</foaf:family_name>\n'
        personal_foaf += '<foaf:depiction rdf:resource="https://graph.facebook.com/'+unicode(user['id'])+'/picture"/>\n'
    
        try:
            personal_foaf += '<foaf:gender>'+unicode(user['gender'])+'</foaf:gender>\n'
        except KeyError:
            pass
        
        return personal_foaf
    
    def _get_foaf_birthday(self, user):
        birthday_foaf = ''
        # Birthday and age info
        birth = user['birthday']
        birthday = datetime.date(int(birth.split('/')[2]), int(birth.split('/')[0]), int(birth.split('/')[1]))
        birthday_foaf += '<foaf:birthday>' + birthday.isoformat() + '</foaf:birthday>\n'
        age = datetime.date.today().year - birthday.year # This has been done intentionally
        birthday_foaf += '<foaf:age>' + unicode(age) + '</foaf:age>\n'
        return birthday_foaf
