#Imports
import numpy as np
import pandas as pd
from matplotlib import pyplot as plt
from sqlalchemy import create_engine

#XLSX/EXCEL IMPORT
df = pd.read_excel('1 - 12042021.xlsx', skiprows=2)
df2 = pd.read_excel('2 - Meteo 01-09-2021.xlsx', skiprows=2)
df3 = pd.read_excel('3 - meteo 15-07-2021.xlsx', skiprows=2)
df4 = pd.read_excel('4 - meteo 22-08-2021.xlsx', skiprows=2)
df5 = pd.read_excel('5 - Meteo 28-06-2021.xlsx', skiprows=2)
df6 = pd.read_excel('6 - 03082020.xlsx', skiprows=2)
df = df.replace("Timeout", 0)

#RENAME DATAFRAME
df.rename(columns={"Timestamp": "datetime_", 
                   "visibility\n601 [m] Cur": "visibility", "ambient temperature\n100 [°C] Cur": "ambient_temperature",
                   "temperature\n100 [°C] Cur": "temperature", "wind speed\n405 [km/h] Cur": "wind_speed", "wind direction\n500 [°] Cur": "wind_direction", 
                   "precipitation absol.\n600 [l/m²] Cur": "absolute_precipitation",
                   "temperature\n100 [°C] Cur.1": "temperature", "wind speed\n405 [km/h] Cur.1": "wind_speed", "wind direction\n500 [°] Cur.1": "wind_direction", 
                   "precipitation absol.\n600 [l/m²] Cur.1": "absolute_precipitation",
                   "road temperature\n100 [norm value] Cur": "road_temperature"
                   }, inplace=True)


#SVs 
sv3=df.iloc[:,7:11].copy()
sv1=df.iloc[:,12:16].copy()
sv5=df.iloc[:,16:20].copy()
sv3["datetime_"] = df["datetime_"]
sv3

#SVMTO
svmeteo = df.iloc[:,:7].copy()
svmeteo["road_temperature"] = df["road_temperature"]
svmeteo

user = "root"
passwd = "trcvbr18"
host = "localhost"
port = 3306
db = "tracevia_app"

#Create connection to the MySQL database
engine = create_engine(f'mysql://{user}:{passwd}@{host}:{port}/{db}')
con = engine.connect()

#Adjust SVs COLUMNS
# svlist = sv3.columns.tolist()
# svlist.insert(0, svlist.pop(3))
# svlist.insert(1, svlist.pop(2))
# svlist.insert(2, svlist.pop(3))
# #sv3[svlist]

#SENDING DO MYSQL
svmeteo.to_sql(con=con, schema='tracevia_app', name='mto_data', if_exists='append', index=False) 
sv3.to_sql(con=con, schema='tracevia_app', name='sv_data', if_exists='append', index=False) 

#Adjust MTO COLUMNS
# svmeteolist = svmeteo.columns.tolist()
# svmeteolist.insert(6, svmeteolist.pop(1))
# svmeteolist.insert(1, svmeteolist.pop(5))
# svmeteolist.insert(2, svmeteolist.pop(4))
# svmeteolist.insert(3, svmeteolist.pop(5))
# svmeteolist.insert(4, svmeteolist.pop(5))
# svmeteo[svmeteolist]
