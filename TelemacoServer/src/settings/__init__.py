import socket

if socket.gethostname() == 'crayfish':
    print 'Using development settings'
    from development import *
else:
    print 'Using production settings'
    from production import *
