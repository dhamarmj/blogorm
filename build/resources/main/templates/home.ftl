<html lang="en">
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Blog</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <!-- Fonts! -->
    <link href="/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic" rel="stylesheet"
          type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800"
          rel="stylesheet" type="text/css">

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
<!-- Main Content -->
<div class="container">
    <div class="row">

        <div class="col-lg-8 col-md-10 mx-auto">
            <#list list as articuloComentario>
                <div class="post-preview">
                    <a href="/Post/${articuloComentario.getId()}">
                        <h2 class="post-title">${articuloComentario.getTitle()}</h2>
                    </a>
                    <p class="post-meta">Posted by ${articuloComentario.getUser().getName()}
                        on ${articuloComentario.getFecha()?string.long}</p>
                    <h6 class="post-subtitle">${articuloComentario.getTeaser()}</h6>
                    <#--<p class="post-meta"> ${articuloComentario.getStringEtiqueta()}</p>-->
                    <ul>
                        <#list articuloComentario.getLabels() as etiqueta>
                            <li><a href="#" class="badge badge-light">${etiqueta.getLabelText()}</a></li>
                        </#list>
                    </ul>
                    <#if usuario != "other">
                        <#--onclick="window.location.href = 'https://w3docs.com';"-->
                        <div class="articuloId" id="${articuloComentario.getId()}"><button type="button" id="likeButton" class="btn btn-outline-primary float-left"> ${articuloComentario.getLikes()} Heart!</button></div>
                    </#if>
                    <#assign usuarioAutor="${articuloComentario.getUser().getId()}">
                    <#if  usuario =="admin" || currentUserId == usuarioAutor>
                        <div class="clearfix">
                            <a class="btn btn-danger float-right" href="/removeArticle/${articuloComentario.getId()}">Borrar</a>
                            <a class="btn btn-info float-right" href="/Admin/Modify/${articuloComentario.getId()}">Modificar</a>
                        </div>
                    </#if>
                </div>
                <hr>
            </#list>
        </div>
    </div>
</div>
<hr><#include "footer.ftl">

<!-- Bootstrap core JavaScript -->
<script
        src="http://code.jquery.com/jquery-3.3.1.min.js"
        integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
        crossorigin="anonymous"></script>
<#--<script src="/js/bootstrap.bundle.js"></script>-->
<script src="../js/scripts.js"></script>

</body>
</html>
