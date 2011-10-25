#!/usr/bin/python

import sqlite3
import csv
import re
import os

def winlosedraw(gf, ga):
    if(gf>ga):
        return (1,0,0)
    if(gf<ga):
        return (0,1,0)
    if(gf==ga):
        return (0,0,1)

db_file="./epldata2012.db"
csv_file="./data/epldata2012.csv"

os.system("rm ./epldata2012.db")

con1=sqlite3.connect(db_file)
c1=con1.cursor()

c1.execute('CREATE TABLE Teams \
        (Id INTEGER PRIMARY KEY AUTOINCREMENT, \
        Name TEXT UNIQUE)')

c1.execute('CREATE TABLE Games \
        (Id INTEGER PRIMARY KEY AUTOINCREMENT, \
        HomeId INTEGER, AwayId INTEGER, GameWeek INTEGER, GoalsFor INTEGER, \
        GoalsAgainst INTEGER)')

c1.execute('CREATE TABLE TeamWeek \
        (GameWeek INTEGER, TeamId INTEGER, GameId INTEGER, Home BOOLEAN, \
        GoalsFor INTEGER, GoalsAgainst INTEGER, W BOOLEAN, L BOOLEAN, \
        D BOOLEAN)')

c1.execute('CREATE TABLE TeamWeekSum \
        (GameWeek INTEGER, TeamId INTEGER, GameId INTEGER, Home INTEGER, \
        GoalsFor INTEGER, GoalsAgainst INTEGER, W INTEGER, L INTEGER, \
        D INTEGER, Morale INTEGER)')

csv_open = open(csv_file, "rU")
parser = csv.reader(csv_open)
gw = 0

for row in parser:
    m = re.search("(?<=GW)[0-9]+",row[0])
    if (m):
        gw=m.group(0)
        print "Game Week: " + gw
        continue
    if (row[0] == ""):
        continue
    else: # Fixture for $gw
        home=row[0]
        away=row[4]
        gf=row[1]
        ga=row[3]
        try:
            c1.execute("INSERT INTO Teams VALUES (NULL, '"+home+"')")
            c1.execute("INSERT INTO Teams VALUES (NULL, '"+away+"')")
            con1.commit()
        except sqlite3.IntegrityError, msg:
            pass
        if (gf == 'P'):
            continue
        print gw + ":" + home + " " + gf + " " + ga + " " + away
        c1.execute("SELECT Id FROM Teams where Teams.Name == '"+home+"'")
        homeid=c1.fetchone()[0]
        c1.execute("SELECT Id FROM Teams where Teams.Name == '"+away+"'")
        awayid=c1.fetchone()[0]
        c1.execute("INSERT INTO Games VALUES \
                (NULL,'"+str(homeid)+"','"+str(awayid)+"','"  \
                 +str(gw)+"','"+str(gf)+"','"+str(ga)+"')")
        con1.commit()

# Get all the teams
c1.execute("SELECT * FROM Teams")
for teamid in c1.fetchall():
    c1.execute("SELECT * FROM Games WHERE \
            (Games.HomeID = '%i') or (Games.AwayID = '%i')" % (teamid[0], teamid[0]))
    print teamid[1] 
    for game in c1.fetchall():
        try:
            if teamid[0] == game[1]: #Home
                w, l, d = winlosedraw(game[4], game[5])
                c1.execute("INSERT INTO TeamWeek VALUES \
                    ('%i','%i','%i','1','%i','%i','%i','%i','%i')" % (game[3], teamid[0],
                    game[0], game[4], game[5], w, l, d))
            if teamid[0] == game[2]: #Away
                w, l, d = winlosedraw(game[5], game[4])
                c1.execute("INSERT INTO TeamWeek VALUES \
                    ('%i','%i','%i','0','%i','%i','%i','%i','%i')" % (game[3], teamid[0],
                    game[0], game[5], game[4], w, l, d))
            con1.commit()
        except TypeError: #Case where Matches results are P
            pass

c1.execute("SELECT * FROM Teams")
for teamid in c1.fetchall():
    c1.execute("SELECT * FROM TeamWeek WHERE TeamWeek.TeamID=%i" % teamid[0])
    morale = gw = tid = gid = h = gf = ga = w = l = d = 0
    for week in c1.fetchall():
        gw=week[0]+1 #Sums are a week after
        if (gw == 2):
            c1.execute("INSERT INTO TeamWeekSum VALUES \
                    ('%i','%i','%i','%i','%i','%i','%i','%i','%i', '%i')" % (1, 
                    tid, gid, h, gf, ga, w, l, d, morale))
        tid=week[1]
        gid=week[2]
        h+=week[3]
        gf+=week[4]
        ga+=week[5]
        w+=week[6]
        l+=week[7]
        d+=week[8]
        morale=w-l
        c1.execute("INSERT INTO TeamWeekSum VALUES \
                    ('%i','%i','%i','%i','%i','%i','%i','%i','%i', '%i')" % (gw,
                    tid, gid, h, gf, ga, w, l, d, morale))
        con1.commit()
c1.close()
