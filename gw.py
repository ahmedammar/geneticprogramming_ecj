#!/usr/bin/python

import os
import sqlite3
import re

db_file="./epldata2012.db"
gw_file="./predictions/gw36"

con1=sqlite3.connect(db_file)
c1=con1.cursor()

gw_open = open(gw_file, "rU")
for line in gw_open:
    vs = re.search(" v ", line);
    if (vs):
        s = line.split(' v ')
        c1.execute("select id from teams where name='"+s[0].rstrip('\n')+"'");
        home = c1.fetchone()[0];
        c1.execute("select id from teams where name='"+s[1].rstrip('\n')+"'");
        away = c1.fetchone()[0];
        print s[0].rstrip('\n') + " v " +s[1].rstrip('\n') + " (" + str(home) + ":" + str(away) + ")"
        os.system("java compute "+str(home)+" "+str(away)+" > tmp/" + s[0].rstrip('\n').replace(' ', "\ ") + "\ v\ " +s[1].rstrip('\n').replace(' ', "\ "));
