# Imports
import pandas as pd
from sqlalchemy import create_engine
from tkinter import *
from openpyxl import *
from MySQLdb import *

# 
user = "service_storm"
passwd = "trac3viabras1l"
host = "10.12.100.18"
port = 3306
db = "tracevia_app"

def save_db(path):
  
    df = pd.read_excel(path, skiprows=2)
    df = df.replace("Timeout", 0)
    list_df = divide_df(df)
    list_table = ["sv_data", "mto_data"]
    
    zipped = list(zip(list_df, list_table))

    con = get_con()

    for z in zipped:
        send_mysql(con, z)
    
    print("\u001b[37;1mDados salvos com sucesso! \u001b[41;1m(Cuidado para não repetir a ação, dados podem ser duplicados no db)\u001b[0m")

def divide_df(dataflame):
    dataflame.rename(columns={
        "Timestamp": "datetime_",
        "visibility\n601 [m] Cur": "visibility", "ambient temperature\n100 [°C] Cur": "ambient_temperature",
        "temperature\n100 [°C] Cur": "temperature", "wind speed\n405 [km/h] Cur": "wind_speed", "wind direction\n500 [°] Cur": "wind_direction",
        "precipitation absol.\n600 [l/m²] Cur": "absolute_precipitation",
        "temperature\n100 [°C] Cur.1": "temperature", "wind speed\n405 [km/h] Cur.1": "wind_speed", "wind direction\n500 [°] Cur.1": "wind_direction",
        "precipitation absol.\n600 [l/m²] Cur.1": "absolute_precipitation",
         "temperature\n100 [°C] Cur.2": "temperature", "wind speed\n405 [km/h] Cur.2": "wind_speed", "wind direction\n500 [°] Cur.2": "wind_direction",
        "precipitation absol.\n600 [l/m²] Cur.2": "absolute_precipitation",
        "road temperature\n100 [norm value] Cur": "road_temperature"
    }, inplace=True)

    sv3 = dataflame.iloc[:,7:11].copy()
    sv1 = dataflame.iloc[:,12:16].copy()
    sv5=  dataflame.iloc[:,16:20].copy()
    sv3["datetime_"] = dataflame["datetime_"]
    sv1["datetime_"] = dataflame["datetime_"]
    sv5["datetime_"] = dataflame["datetime_"]

    svmeteo = dataflame.iloc[:,:7].copy()
    svmeteo["road_temperature"] = dataflame["road_temperature"]

    return [sv3, sv1, sv5, svmeteo]

def get_con():
    #Create connection to the MySQL database
    engine = create_engine(f'mysql://{user}:{passwd}@{host}:{port}/{db}')
    return engine.connect()

def send_mysql(con, obj):
    obj[0].to_sql(con=con, schema='tracevia_app', name=obj[1], if_exists='append', index=False)

if __name__ == '__main__':
    main()


