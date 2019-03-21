<html lang="en"><head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Blog</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
</head>

<body>
<#include "navbar.ftl">
<br>
<br>
<br>
<br>
<div class="container">
    <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
            <form name="sentMessage" id="contactForm" method="post" action="/updateArticle/${articulo.getId()}">
                <div class="control-group">
                    <div class="form-group floating-label-form-group controls">
                        <label>Title</label>
                        <input type="text" class="form-control" value="${articulo.title}"  name="title" required data-validation-required-message="Please enter your name.">
                        <p class="help-block text-danger"></p>
                    </div>
                </div>
                <div class="control-group">
                    <div class="form-group floating-label-form-group controls">
                        <label>Cuerpo</label>
                        <textarea rows="5" class="form-control" name="body" required data-validation-required-message="Please enter a message.">${articulo.body}</textarea>
                        <p class="help-block text-danger"></p>
                    </div>
                </div>
                <div class="control-group">
                    <div class="form-group floating-label-form-group controls">
                        <label>Etiquetas</label>
                        <textarea rows="3" class="form-control"  placeholder="Separadas por coma" name="etiquetas" data-validation-required-message="Please enter a message.">${articulo.stringEtiqueta}</textarea>
                        <p class="help-block text-danger"></p>
                    </div>
                </div>
                <br>
                <div id="success"></div>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary" id="sendMessageButton">Post!</button>
                </div>
            </form>
        </div>
    </div>
</div>
<br>
<br>
<br>
<#include "footer.ftl">
<script src="/jquery/jquery.js"></script>
<script src="/js/bootstrap.bundle.js"></script>

</body>
</html>