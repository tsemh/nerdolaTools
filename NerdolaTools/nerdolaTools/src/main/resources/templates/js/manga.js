console.log(typeof jsPDF);


$(document).ready(function() {
    $('#mangaForm').submit(function(e) {
        e.preventDefault();
        var url = $('#urlInput').val();
        var endpoint = $('#endpointSelect').val();

        var requestData = {
            "url": url
        };
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/manga-download/' + endpoint,
            contentType: 'application/json',
            data: JSON.stringify(requestData),
            success: function(data) {
                console.log("Dados recebidos do servidor:", data);

                if (Array.isArray(data) && data.length > 0) {
                    createPDFFromImages(data);
                } else {
                    console.error('Resposta inválida do servidor.');
                }
            },
            error: function(error) {
                console.error(error);
            }
        });
    });
});
function createPDFFromImages(imageUrls) {
    var doc = new jsPDF();
    function processImage(index) {
        if (index < imageUrls.length) {
            var imageUrl = imageUrls[index];
            loadImage(imageUrl, function(imageData) {
                doc.addImage(imageData, 'JPEG', 10, 10, 190, 250); // Ajuste os valores conforme necessário
                doc.addPage();
                processImage(index + 1);
            });
        } else {
            doc.save('downloaded_pdf.pdf');
        }
    }
    processImage(0);
}
function loadImage(url, callback) {
    var img = new Image();
    img.crossOrigin = 'Anonymous'; // Permitir o carregamento de imagens de diferentes domínios
    img.onload = function() {
        var canvas = document.createElement('canvas');
        var ctx = canvas.getContext('2d');
        canvas.width = img.width;
        canvas.height = img.height;
        ctx.drawImage(img, 0, 0, img.width, img.height);
        var imageData = canvas.toDataURL('image/jpeg');
        callback(imageData);
    };
    img.src = url;
}
