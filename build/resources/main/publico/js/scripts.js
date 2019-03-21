$(document).ready(function () {
    $(document).on('click', '#likeButton', function () {
        var id = $(this).parent().attr('id');
        var thisNode = this;
        $.ajax({
            url: '/addlike/' + id,
            type: 'GET',
            contentType: 'application/json',
            success: function(result) {
                $(thisNode).text(result.likes + " Hearts");
            }});
    })
});