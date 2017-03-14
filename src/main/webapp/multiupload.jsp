<%-- 
    Document   : multiupload
    Created on : Feb 10, 2017, 3:06:00 PM
    Author     : dfChicken
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script type="text/javascript">

            $(document).ready(function ()
            {
                $("#uploadBtn").click(function ()
                {
                    $('input[name="file"]').each(function (index, value)
                    {
                        var file = value.files[0];
                        if (file)
                        {
                            var formData = new FormData();
                            formData.append('file', file);
                            $.ajax({
                                url: '/ANDRService/resources/files/upload',
                                type: 'POST',
//                                headers: {
//                                    "Authorization": "Basic YWRtaW5AZ21haWwuY29tOjEyMw=="
//                                },
                                data: formData,
                                cache: false,
                                contentType: false,
                                processData: false,
                                success: function (data, textStatus, jqXHR) {
                                    var message = jqXHR.responseText;
                                    $("#messages").append("<li>" + message + "</li>");
                                },
                                error: function (jqXHR, textStatus, errorThrown) {
                                    $("#messages").append("<li style='color: red;'>" + textStatus + "</li>");
                                }
                            });
                        }
                    });
                });
            });
        </script>
        <title>JSP Page</title>
    </head>
    <body>
        <h1>JAX-RS Multi-file Upload Example</h1>

        <form action="resources/files/upload" method="post" enctype="multipart/form-data">
            <p>
                Select file 1: <input type="file" name="file" size="45"  />
            </p>
            <p>
                Select file 2: <input type="file" name="file" size="45"  />
            </p>
            <p>
                Select file 3: <input type="file" name="file" size="45"  />
            </p>
            <p>
                <input id="uploadBtn" type="button" value="Upload PFD Files" />
            </p>
        </form>

        <ul id="messages">    
        </ul>

    </body>
</html>
