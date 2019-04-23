package pucmm.edu.dhamarmj.Handler;

import freemarker.template.Configuration;
import org.h2.store.PageStreamTrunk;
import pucmm.edu.dhamarmj.Encapsulation.*;
import pucmm.edu.dhamarmj.Services.*;
import pucmm.edu.dhamarmj.transformation.JsonTransformer;
import spark.ModelAndView;
import spark.Request;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;
import sun.awt.geom.AreaOp;

import javax.jws.soap.SOAPBinding;
import java.util.*;

import static spark.Spark.*;
import static spark.Spark.before;

public class MainHandler {

    public MainHandler() {
//        System.out.println(encryptPassword("admin"));
//        List<Article> arts = ArticleServices.getInstancia().getLabelArticles(65);
//        System.out.println(arts.size());
    }


    User currentUser;
    int pageSize = 5;
    int currentPage=1;
    int totalPages=1;

    public void startup() {
        staticFiles.location("/publico");

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(MainHandler.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);

        get("/", (request, response) -> {
            currentPage =  request.queryParams("page") != null && !request.queryParams("page").isEmpty() ? Integer.parseInt(request.queryParams("page")) : 1;
            List<Article> articulo = ArticleServices.getInstancia().paginateArticles(currentPage);
            StartUser();
            String user = request.cookie("LoginU");
            if (user != null) {
                String passw = Encryption.Decrypt(request.cookie("LoginP"));
                String usern = Encryption.Decrypt(user);
                currentUser = UserServices.getInstancia().getUser(usern, passw);
                CreateSession(request, currentUser);
            }
            currentUser = getSessionUsuario(request);
            Map<String, Object> attributes = ShowHomeAttributes(articulo);
            attributes.put("fromLabel", false);
            return new ModelAndView(attributes, "home.ftl");
        }, freeMarkerEngine);

        before("/Admin/*", (request, response) -> {
            User usuario = request.session().attribute("usuario");
            if (usuario == null) {
                response.redirect("/");
            } else {
                if (!usuario.isAdmin() && !usuario.isAuthor()) {
                    response.redirect("/");
                }
            }
        });

        get("/Post/:idpost", (request, response) -> {
            Map<String, Object> attributes = getArticle(request.params("idpost"));
            return new ModelAndView(attributes, "articleDetail.ftl");
        }, freeMarkerEngine);

        get("/LogIn/", (request, response) -> {
            return new ModelAndView(null, "signIn.ftl");
        }, freeMarkerEngine);

        post("/logInUser/", (request, response) -> {
                User user = UserServices.getInstancia().getUser(request.queryParams("username"), encryptPassword(request.queryParams("password")));
                if (user != null) {
                CreateSession(request, user);
                boolean remember = Boolean.parseBoolean(request.queryParams("remember"));
                if (remember) {
                    response.cookie("/", "LoginU", Encryption.Encrypt(user.getUsername()), 604800, false);
                    response.cookie("/", "LoginP", Encryption.Encrypt(user.getPassword()), 604800, false);
                }
                response.redirect("/");
            } else {
                response.redirect("/LogIn/");
            }
            return null;
        }, freeMarkerEngine);

        get("/Admin/CreateUser/", (request, response) -> {
            Map<String, Object> attributes = validateUSer();
            return new ModelAndView(attributes, "createUser.ftl");
        }, freeMarkerEngine);

        post("/Admin/saveUser/", (request, response) -> {
            UserServices.getInstancia().crear(new User(request.queryParams("name"),
                    request.queryParams("username"),
                    encryptPassword(request.queryParams("pass")),
                    Boolean.parseBoolean(request.queryParams("adminVal")),
                    Boolean.parseBoolean(request.queryParams("authorVal"))));
            response.redirect("/");
            return null;
        }, freeMarkerEngine);

        get("/Admin/Compose/", (request, response) -> {
            Map<String, Object> attributes = validateUSer();
            return new ModelAndView(attributes, "compose.ftl");
        }, freeMarkerEngine);

        post("/saveArticle/", (request, response) -> {
            Set<Label> listE = getEtiquetas(request.queryParams("etiquetas"));
            Set<Comment> comment = new HashSet<Comment>();
            Article Art = new Article(request.queryParams("title"),
                    request.queryParams("body").trim(),
                    new Date(),
                    currentUser,
                    comment,
                    listE,
                    request.queryParams("etiquetas"));
            ArticleServices.getInstancia().crear(Art);
            response.redirect("/");
            return null;
        }, freeMarkerEngine);

        get("/Articles/:label", (request, response) -> {
            currentPage =  request.queryParams("page") != null && !request.queryParams("page").isEmpty() ? Integer.parseInt(request.queryParams("page")) : 1;
            Label label = LabelServices.getInstancia().getLabel(request.params("label"));
            List<Article> art = ArticleServices.getInstancia().getLabelArticles(label.getId(), currentPage);
            Map<String, Object> attributes = ShowHomeAttributes(art);
            attributes.put("fromLabel", true);
            return new ModelAndView(attributes, "home.ftl");
        }, freeMarkerEngine);


        get("/Admin/Modify/:idpost", (request, response) -> {
            Map<String, Object> attributes = getArticle(request.params("idpost"));
            attributes.remove("list");
            return new ModelAndView(attributes, "modifyPost.ftl");
        }, freeMarkerEngine);

        post("/sendComment/:idpost", (request, response) -> {
            Article Ar = ArticleServices.getInstancia().find(Long.parseLong(request.params("idpost")));
            Comment C = new Comment(request.queryParams("comment").trim(),
                    currentUser, Ar);
            Ar.setComments(C);
            CommentServices.getInstancia().crear(C);
            response.redirect("/Post/" + Ar.getId());
            return null;
        }, freeMarkerEngine);

        post("/updateArticle/:idpost", (request, response) -> {
            Map<String, Object> attributes = validateUSer();
            Article Ar = ArticleServices.getInstancia().find(Long.parseLong(request.params("idpost")));
            Ar.setBody(request.queryParams("body"));
            Ar.setTitle(request.queryParams("title"));
            Ar.StartTeaser();
            Ar.setLabels(getEtiquetas(request.queryParams("etiquetas")));
            Ar.setStringEtiqueta(request.queryParams("etiquetas"));
            ArticleServices.getInstancia().editar(Ar);
            response.redirect("/");
            return null;
        }, freeMarkerEngine);

        get("/removeArticle/:idpost", (request, response) -> {
            Article art = ArticleServices.getInstancia().find(Long.parseLong(request.params("idpost")));
            ArticleServices.getInstancia().eliminar(art.getId());
            response.redirect("/");
            return null;
        }, freeMarkerEngine);

        get("/addlike/:idpost", (request, response) -> {
            response.header("Content-type", "application/json");
            Article art = ArticleServices.getInstancia().find(Long.parseLong(request.params("idpost")));
            art.updateLikes(currentUser);
            ArticleServices.getInstancia().editar(art);
            art.setLabels(null);
            return art;
        }, new JsonTransformer());

        get("/LogOut/", (request, response) -> {
            request.session().removeAttribute("usuario");
            request.session().invalidate();
            currentUser = null;
            response.removeCookie("/", "LoginU");
            response.removeCookie("/", "LoginP");
            response.redirect("/");
            return null;
        }, freeMarkerEngine);
    }

    private Map<String, Object> ShowHomeAttributes(List<Article> art) {
        Map<String, Object> attributes = validateUSer();
        if (currentUser != null) {
            attributes.put("currentUserId", String.valueOf(currentUser.getId()));
        } else {
            attributes.put("currentUserId", "0");
        }
        attributes.put("list", art);

        totalPages = GetPageTotal();
        attributes.put("nextPagina", currentPage+1);
        attributes.put("pastPagina", currentPage-1);
        attributes.put("ultimaPagina", totalPages);

        return attributes;
    }

    private Set<Label> getEtiquetas(String values) {
        if (values.equalsIgnoreCase(""))
            return null;

        Set<Label> list = new HashSet<Label>();
        Label E;
        String[] et = values.split(",");
        for (String e :
                et) {
            E = LabelServices.getInstancia().InsertLabel(new Label(e.trim()));
            list.add(E);
        }
        return list;
    }

    private Map<String, Object> getArticle(String request) {
        Map<String, Object> attributes = validateUSer();
        Article Ar = ArticleServices.getInstancia().find(Long.parseLong(request));
        attributes.put("articulo", Ar);
        // attributes.put("list", Ar.getComments());
        return attributes;
    }

    private User getSessionUsuario(Request request) {
        return request.session().attribute("usuario");
    }

    private void CreateSession(Request request, User user) {
        Session session = request.session(true);
        session.attribute("usuario", user);
    }

    public static String getHash(String txt, String hashType) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance(hashType);
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static String encryptPassword(String txt) {
        return getHash(txt, "MD5");
    }

    private int GetPageTotal() {
        int countResults = ArticleServices.getInstancia().findAll().size();
        int PageTotal = (int) (Math.ceil(countResults / pageSize));
        if(countResults / pageSize % 1 == 0){
            PageTotal++;
        }
        return PageTotal;
    }

    private Map<String, Object> validateUSer() {
        Map<String, Object> attributes = new HashMap<>();
        if (currentUser == null) {
            attributes.put("usuario", "other");
            attributes.put("userSigned", false);
            return attributes;
        }

        if (currentUser.isAdmin()) {
            attributes.put("usuario", "admin");
        } else if (currentUser.isAuthor()) {
            attributes.put("usuario", "author");
        } else {
            attributes.put("usuario", "visitor");
        }
        attributes.put("userSigned", true);
        return attributes;
    }

    private void StartUser() {
        User a = UserServices.getInstancia().findAll().get(0);
        if (a == null) {
            UserServices.getInstancia().crear(new User("admin",
                    "admin",
                    encryptPassword("admin"),
                    true,
                    true));
        }
    }

}
