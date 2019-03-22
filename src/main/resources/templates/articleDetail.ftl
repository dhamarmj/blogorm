<html lang="en">
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Blog</title>
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
</head>
<body>
<#if userSigned == false>
    <#include "firstNav.ftl">
<#else>
    <#include "navbar.ftl">
</#if>
<br>
<br>
<br>
<br>
<header class="masthead">
    <div class="overlay"></div>
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-md-10 mx-auto">
                <div class="post-heading">
                    <h1>${articulo.getTitle()}</h1>
                    <br>
                    <blockquote class="blockquote">Posted by
                        ${articulo.getUser().getName()}
                        on ${articulo.getFecha()?string.long}</blockquote>
                </div>
            </div>
        </div>
    </div>
</header>
<br>
<article>
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-md-10 mx-auto">
                <p>${articulo.getBody()}</p>
                <ul>
                    <#list articulo.getLabels() as etiqueta>
                        <li><p class="badge badge-light">${etiqueta.getLabelText()}</p></li>
                    </#list>
                </ul>
            </div>
        </div>
    </div>
</article>
<br>
<div class="container">
    <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
            <h6 class="blockquote">Comentarios</h6>
            <ul>
                <#list articulo.getComments() as comentario >
                    <div class="card mr-2 mb-0">
                        <div class="card-body">
                            <h5 class="card-title"> ${comentario.getUser().name}</h5>
                            <p class="card-text">${comentario.getCommentText()}</p>
                        </div>
                    </div>
                </#list>
            </ul>
        </div>
    </div>
</div>
<hr>
<#if  usuario != "other">
<div class="container">
    <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
            <form name="sentMessage" id="contactForm" method="post" action="/sendComment/${articulo.getId()}">
                <div class="control-group">
                    <div class="form-group floating-label-form-group controls">
                        <label>Comenta!</label>
                        <input type="text" class="form-control" name="comment" id="comment" required="">
                        <p class="help-block text-danger"></p>
                    </div>
                </div>
                <div id="success"></div>
                <div class="form-group">
                    <button type="submit" class="btn btn-info" id="sendMessageButton">Send Comment!</button>
                </div>
            </form>
        </div>
    </div>
</div>
</#if>
<br>
<hr><#include "footer.ftl">
<script src="/jquery/jquery.js"></script>
<script src="/js/bootstrap.bundle.js"></script>

</body>
</html>