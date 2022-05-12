#Imports
from time import sleep
import pandas as pd
from sqlalchemy import create_engine
from datetime import datetime
import os

#DATABASE ACCESS
user = "service_storm"
passwd = "trac3viabras1l"
host = "10.12.100.19"
port = 3306
db = "tracevia_app"

#Create connection to the MySQL database
engine = create_engine(f'mysql://{user}:{passwd}@{host}:{port}/{db}')
con = engine.connect()

query = pd.read_sql_query(f"SELECT equip_ip, equip_id FROM tracevia_app.dai_equipment", con)
df = pd.DataFrame(columns={"equip_id", "direction","lane","date","hour","incident"})
df = df[["equip_id", "direction","lane","date","hour","incident"]]

equip_info = {}
dates_saved = {}
options = "daiOptions.conf"

for e in query.values:
    equip_info[e[0]] = e[1]

try:
    with open(options, "r") as date:
        for line in date.readlines():
            if "=" not in line:
                continue

            splited = line.split("=")

            if len(splited) > 1:
                dates_saved[splited[0].strip()] = datetime.fromtimestamp(float(splited[1].strip()))
except FileNotFoundError as err:
    open(options, "x").close()

# #MOVING FILES
# dest= 'C:\Cameras\DAI db'
# def copyfiles(files, dest):
#    if os.path.isdir(dest):
#       dst = os.path.join(dest, os.path.basename(files))
#       print(dst)
#    os.replace(files,dest)

#Save Dates
def save_date():
    with open(options, 'w') as o:
        o.writelines(map(lambda x: f'{x[0]}={x[1].timestamp()}\n', dates_saved.items()))

#SPLIT FILES
def allequips(files, ip):
    equip_id = equip_info.get(ip)
    date_saved = dates_saved.get(ip)
    if equip_id:
        for file in files:
            f_name, f_ext = os.path.splitext(os.path.basename(file))
            spl_incident, channel, lane, direction, spl_date, spl_hour, ended = f_name.split(sep="_", maxsplit=6)
            incident = spl_incident.replace("(Event)", "")
            date = datetime.strptime(spl_date, "%Y%m%d").date()
            hour = datetime.strptime(spl_hour, "%H%M%S%f").time().replace(microsecond=0)
            dt = datetime.combine(date, hour)

            if date_saved:
                if dt <= date_saved:
                    continue

            df2 = pd.DataFrame([[equip_id, direction, lane, date, hour, incident]], columns=['equip_id', 'direction', 'lane', 'date', 'hour', 'incident'])
            globals()["df"] = pd.concat([df, df2])

            new_date = dates_saved.get(ip)
            if new_date:
                if dt <= new_date:
                    continue
            dates_saved[ip] = dt
        
#GET PATH
ini_folder = 'C:\Cameras\DAI'
def collect():
    equips = list(map(lambda x: (os.path.join(ini_folder, x, 'Traffic Incident'), x), os.listdir(ini_folder)))
    for (equip, ip) in equips:
        date_saved = dates_saved.get(ip)
        try:
            dates = map(lambda x: os.path.join(equip, x), filter(lambda y: datetime.strptime(y, "%Y%m%d").date() >= date_saved.date() if date_saved else True, os.listdir(equip)))
            days = []
            [days.extend(x) for x in map(lambda x: list(map(lambda y: os.path.join(x, y), os.listdir(x))), dates)]
            files = []
            [files.extend(x) for x in map(lambda x: list(map(lambda y: os.path.join(x, y), os.listdir(x))), days)]
            
            allequips(files, ip)
        except Exception as ex:
            with open('err.log', 'w') as log:
                log.write(str(ex))

while True:
    try:
        collect()
        df.to_sql(con=con, schema='tracevia_app', name='dai_history', if_exists='append', index=False)
        save_date()
        #copyfiles(files[0], dest)
    finally:
        df = df[0:0]
        sleep(20)