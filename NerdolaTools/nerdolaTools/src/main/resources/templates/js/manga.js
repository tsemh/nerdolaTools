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
            headers: {
                Accept: 'application/zip'
            },
            success: function(data) {
                var blob = new Blob([data], { type: 'application/zip' });

                var downloadLink = document.createElement('a');
                downloadLink.href = window.URL.createObjectURL(blob);
                downloadLink.download = nameZip + '.zip';

                document.body.appendChild(downloadLink);
                downloadLink.click();
                document.body.removeChild(downloadLink);
                console.log(data)
            },
            error: function(error) {
            }
        });
    });
});
