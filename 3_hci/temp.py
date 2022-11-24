# SAHIL AKTAR SHAIKH
# 3350
# HCI Assignment 3 - Design a Graphical User Interface in Python

from tkinter import *
from tkinter import messagebox
import os

def login():
    user=username.get()
    code=password.get()

    if user=="Sahil" and code=="3350":
        import HCI.py

    elif user=="" and code=="":
      messagebox.showerror(" Invalid", "Username & Password is required")
    elif user=="":
      messagebox.showerror(" Invalid", "Username is required")
    elif code=="":
      messagebox.showerror(" Invalid", "Password is required")
    elif user!="Sahil" and code!="3350":
      messagebox.showerror(" Invalid", "Username or Password ")
    elif user!="Sahil":
        messagebox.showerror(" Invalid", "Enter a valid Username") 
    elif code!="3350":
        messagebox.showerror(" Invalid", "Enter a valid Password ")  
      

def main_screen():

    global screen
    global username
    global password
    screen=Tk()
    screen.geometry("800x720+150+80")
    screen.configure(bg="#d7dae2")

    image_icon1 = PhotoImage(file="hidden.png")
    screen.iconphoto(False,image_icon1)
    screen.title("Login System")

    lb1Title=Label(text="Message Encoder\n Login System", font=("arial",50,'bold'),fg="Red",bg="#d7dae2")
    lb1Title.pack(pady=50)

    bordercolor=Frame(screen,bg="blue",width=500,height=400)
    bordercolor.pack()


    mainframe=Frame(bordercolor,bg="#d7dae2",width=800,height=400)
    mainframe.pack(padx=20,pady=20)

    Label(mainframe,text="Username",font=("arial",30,"bold"),bg="#d7dae2").place(x=100,y=50)
    Label(mainframe,text="Password",font=("arial",30,"bold"),bg="#d7dae2").place(x=100,y=150)

    username=StringVar()
    password=StringVar()

    entry_username=Entry(mainframe,textvariable=username,width=12,bd=2,font=("arial",30))
    entry_username.place(x=400,y=50)
    entry_password=Entry(mainframe,textvariable=password,width=12,bd=2,font=("arial",30),show="*")
    entry_password.place(x=400,y=150)

    def reset():
      entry_username.delete(0,END)
      entry_password.delete(0,END)

    Button(mainframe,text="Login",font=("arial",10,"bold"),height="3",width=23,bg="#ed8333",fg="white",bd=0,command=login).place(x=100,y=250)
    Button(mainframe,text="Reset",font=("arial",10,"bold"),height="3",width=23,bg="#1089ff",fg="white",bd=0,command=reset).place(x=300,y=250)
    Button(mainframe,text="Exit",font=("arial",10,"bold"),height="3",width=23,bg="#00bd56",fg="white",bd=0,command=screen.destroy).place(x=500,y=250)

    screen.mainloop()

main_screen()