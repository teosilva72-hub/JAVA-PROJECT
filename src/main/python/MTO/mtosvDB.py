#Imports
from tkinter import *
from tkinter import filedialog
from tkinter import messagebox
from mto import save_db
filename = ''

# Function for opening the
# file explorer window
def browseFiles():
    globals()['filename'] = filedialog.askopenfilename(initialdir = "./",
                                          title = "Select a File",
                                          filetypes = (("Excel files",
                                                        "*.xls .xlsx .xlsm .xlsb .xlsb .odf .ods .odt*"),
                                                       ))
    # Change label contents
    label_file_explorer.configure(text="Selected File: "+filename)

# Create the root window
window = Tk()

# Set window title
window.title('MTO/SV EXCEL IMPORT TO DB')

# Set window size
window.geometry("500x200")

# Alert Box
def msgalert():
    conf_save=messagebox.askokcancel('Ok Cancel','Do you wish proceed?')
    if conf_save: 
        save_db(filename) 
        messagebox.showinfo(title='É isso memo',message='Succeed')

# Create a File Explorer label
label_file_explorer = Label(window,
                            text = "MTO/SV Excel File Explorer",
                            height = 4,
                            fg = "blue")

button_save = Button(window,
                        text = "Send to db",
                        command = msgalert)

button_explore = Button(window,
                        text = "Browse Excel File",
                        command = browseFiles)
  
button_exit = Button(window,
                     text = "Exit",
                     command = window.destroy)



# Grid method is chosen for placing
# the widgets at respective positions
# in a table like structure by
# specifying rows and columns
label_file_explorer.grid(column = 0, row = 0, columnspan=15, padx=40)

button_save.grid(column = 1, row = 2, columnspan=10)
  
button_explore.grid(column = 1, row = 3, columnspan=10)
  
button_exit.grid(column = 1,row = 4, columnspan=10 )
  
# Let the window wait for any events
window.mainloop()