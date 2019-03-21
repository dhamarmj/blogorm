package pucmm.edu.dhamarmj.Services;

import pucmm.edu.dhamarmj.Encapsulation.*;

public class CommentServices extends DatabaseServices<Comment>  {
    private static CommentServices instancia;

    private CommentServices() {
        super(Comment.class);
    }

    public static CommentServices getInstancia(){
        if(instancia==null){
            instancia = new CommentServices();
        }
        return instancia;
    }
}
