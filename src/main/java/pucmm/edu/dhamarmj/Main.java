package pucmm.edu.dhamarmj;

import pucmm.edu.dhamarmj.Encapsulation.User;
import pucmm.edu.dhamarmj.Handler.MainHandler;
import pucmm.edu.dhamarmj.Services.StartDatabase;
import pucmm.edu.dhamarmj.Services.UserServices;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        StartDatabase.getInstancia().startDb();




        //   UserServices.getInstancia().crear(new User("admin","Admin Name","tarea123",true,false));
//        UserServices.getInstancia().find()
//        UserServices.getInstancia().eliminar();

        new MainHandler().startup();

    }
}
