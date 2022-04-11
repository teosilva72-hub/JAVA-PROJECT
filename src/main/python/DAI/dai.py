#Imports
import numpy as np
import pandas as pd
from sqlalchemy import create_engine
from datetime import datetime
from functools import reduce
import os

df = pd.DataFrame(columns={"equip_id", "direction","lane","date","hour","incident"})
df = df[["equip_id", "direction","lane","date","hour","incident"]]

#DATABASE ACCESS
user = "root"
passwd = "trcvbr18"
host = "localhost"
port = 3306
db = "tracevia_app"

#Create connection to the MySQL database
engine = create_engine(f'mysql://{user}:{passwd}@{host}:{port}/{db}')
con = engine.connect()

#MOVING FILES
dest= 'C:\Camaras DAI\DAI db'
def copyfiles(files, dest):
   if os.path.isdir(dest):
      dst = os.path.join(dest, os.path.basename(files))
      print(dst)
   os.replace(files,dest)

#SPLIT FILES
def allequips(files, ip):
    for file in files:
        
        f_name, f_ext = os.path.splitext(os.path.basename(file))
        spl_incident, channel, lane, direction, spl_date, spl_hour, ended = f_name.split(sep="_", maxsplit=6)
        equip_id = pd.read_sql_query(f"SELECT equip_id FROM tracevia_app.dai_equipment where equip_ip = '{ip}'", con).iloc[0]['equip_id']
        incident = spl_incident.replace("(Event)", "")
        date = datetime.strptime(spl_date, "%Y%m%d").date()
        hour = datetime.strptime(spl_hour, "%H%M%S%f").time().replace(microsecond=0)
        df2 = pd.DataFrame([[equip_id, direction, lane, date, hour, incident]], columns=['equip_id', 'direction', 'lane', 'date', 'hour', 'incident'])
        globals()['df'] = df.append(df2)

#GET PATH
ini_folder = 'C:\Camaras DAI'
equips = list(map(lambda x: (os.path.join(ini_folder, x, 'Traffic Incident'), x), os.listdir(ini_folder)))
for (equip, ip) in equips:
    try:
        dates = map(lambda x: os.path.join(equip, x), os.listdir(equip))
        days = []
        [days.extend(x) for x in map(lambda x: list(map(lambda y: os.path.join(x, y), os.listdir(x))), dates)]
        files = []
        [files.extend(x) for x in map(lambda x: list(map(lambda y: os.path.join(x, y), os.listdir(x))), days)]
        
        allequips(files, ip)
    except Exception as ex:
        with open('err.log', 'w') as log:
            log.write(str(ex))

df
df.to_sql(con=con, schema='tracevia_app', name='dai_history', if_exists='append', index=False)
#copyfiles(files[0], dest)