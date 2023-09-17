// manga.js

$(document).ready(function() {
    $('#mangaForm').submit(function(e) {
        e.preventDefault();

        var url = $('#urlInput').val();
        var nameZip = $('#nameZipInput').val();

        var requestData = {
            "url": url,
            "nameZip": nameZip
        };

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/download-manga/mangareader',
            contentType: 'application/json',
            data: JSON.stringify(requestData),
            success: function(data) {
                // Lógica de tratamento de sucesso aqui
            },
            error: function(error) {
                // Lógica de tratamento de erro aqui
            }
        });
    });
});
