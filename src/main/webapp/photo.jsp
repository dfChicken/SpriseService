<%-- 
    Document   : index
    Created on : Jan 21, 2017, 10:54:48 PM
    Author     : dfChicken
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Photos Library <3</title>
        <link href='https://fonts.googleapis.com/css?family=Roboto+Mono:400,300' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/3.0.3/normalize.min.css">
        <link href="css/jquery.galereya.css"  rel="stylesheet" type="text/css" />
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script src="js/jquery.galereya.min.js"></script>
        <script>
            $(function () {
                $('#gal1').galereya({
                    // spacing between cells of the masonry grid
                    spacing: 2,
                    // waving visual effect
                    wave: false,
                    // waving visual effect timeout duration
                    waveTimeout: 300,
                    // speed of appearance of cells
                    cellFadeInSpeed: 100,
                    // the name of the general category
                    noCategoryName: 'all',
                    //speed of the slide show
                    slideShowSpeed: 5000
                });
            });
        </script>

        <style>
            body {
                background: #fefefe;
                color: white;
                -webkit-font-smoothing: antialiased;
                -moz-osx-font-smoothing: grayscale;
                text-align: center;

            }
            #container
            {
                position: absolute;
                top: 10%;
                left: 25%;
                right: 25%;
                width: 50%;
                height: auto;
            }
            h2 {
                font-weight: 300;
                margin: 4vh 4vw;
                letter-spacing: 3px;
                color: grey;
                text-transform: uppercase;
            }
        </style>

    </head>

    <body style="background: black;">
        <h2>Photos Library &lt;3</h2>
        <div id="container">
            <div id="gal1">
                <img src="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/thumbnails%2FB612_20161115_172214.jpg?alt=media&token=8be09f24-8d2c-4889-872c-89b3bc77050f" data-fullsrc="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/fullsizes%2FB612_20161115_172214.jpg?alt=media&token=19cf639b-dc3d-4342-b6c5-df031db87ecc" data-desc="" />
                <img src="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/thumbnails%2FB612_20161126_171354.jpg?alt=media&token=c9e5c9ab-0934-4b66-9cc5-4bc6fc67a991" data-fullsrc="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/fullsizes%2FB612_20161126_171354.jpg?alt=media&token=5ea7d5ac-4ac0-4832-9a49-4fc5f0d61edc" data-desc="<3" />
                <img src="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/thumbnails%2FB612_20161223_170640.jpg?alt=media&token=ebe93544-f557-4867-b549-f476c60a9563" data-fullsrc="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/fullsizes%2FB612_20161223_170640.jpg?alt=media&token=38c8d36b-2e0a-436a-beff-e4cd818e5d82" data-desc="<3" />
                <img src="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/thumbnails%2FB612_20161224_150119.jpg?alt=media&token=8f3bc8cc-c347-429f-ae27-736b2be85a81" data-fullsrc="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/fullsizes%2FB612_20161224_150119.jpg?alt=media&token=1de55d93-f5b1-4aeb-b450-1e37e126fcc4" data-desc="<3" />
                <img src="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/thumbnails%2FB612_20161224_160236.jpg?alt=media&token=485d3835-bfd3-4f42-9867-43d757b47820" data-fullsrc="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/fullsizes%2FB612_20161224_160236.jpg?alt=media&token=04097d66-9a33-485c-8b8a-22a3049cf000" data-desc="<3" />
                <img src="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/thumbnails%2FB612_20161224_161635.jpg?alt=media&token=fdec417a-ac81-48fa-af9b-9f6693880e6d" data-fullsrc="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/fullsizes%2FB612_20161224_161635.jpg?alt=media&token=c203a912-5d99-449e-9a4a-6272ac2dace2" data-desc="<3" />
                <img src="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/thumbnails%2FB612_20161224_170756.jpg?alt=media&token=8a9a603f-f102-416f-84b7-bad845bf9acb" data-fullsrc="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/fullsizes%2FB612_20161224_170756.jpg?alt=media&token=2a4911cd-2450-4f0f-a179-5b8cac0b95c0" data-desc="<3" />
                <img src="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/thumbnails%2FB612_20170117_171127.jpg?alt=media&token=927b35c4-05ff-4c55-9820-f5a4dc6fd3ac" data-fullsrc="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/fullsizes%2FB612_20170117_171127.jpg?alt=media&token=3efcbf94-b9e7-4d90-a22a-c85d3db01957" data-desc="<3" />
                <img src="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/thumbnails%2FB612_20170203_170353.jpg?alt=media&token=7afeb948-7129-4455-b84e-49a8d07ff1b6" data-fullsrc="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/fullsizes%2FB612_20170203_170353.jpg?alt=media&token=79a38692-fb7b-4d90-a6b1-7d7b346899a2" data-desc="<3" />
                <img src="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/thumbnails%2FB612_20170218_161618.jpg?alt=media&token=c1771bc8-cc6b-468d-8e68-c810308ae02c" data-fullsrc="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/fullsizes%2FB612_20170218_161618.jpg?alt=media&token=9bea83be-1276-4a11-a6a3-fa9e272a5738" data-desc="<3" />
                <img src="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/thumbnails%2FB612_20170218_165142.jpg?alt=media&token=b9f99b9d-9817-40aa-8137-bf8d9ba88a42" data-fullsrc="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/fullsizes%2FB612_20170218_165142.jpg?alt=media&token=6fb9655f-328d-4a1f-a52c-db8a6d526219" data-desc="<3" />
                <img src="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/thumbnails%2FB612_20170218_182217.jpg?alt=media&token=c6b07f9b-fd4f-4c3b-acfb-4d963d654a66" data-fullsrc="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/fullsizes%2FB612_20170218_182217.jpg?alt=media&token=2cb1ae1b-0f1f-4172-b83d-32e6eaa7e15e" data-desc="<3" />
                <img src="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/thumbnails%2FB612_20170224_163613.jpg?alt=media&token=8bd1d5b0-d62c-4792-a689-6ed5a5e6896d" data-fullsrc="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/fullsizes%2FB612_20170224_163613.jpg?alt=media&token=9e449a04-fa53-4bd8-9224-68e2e1c9dbf4" data-desc="<3" />
                <img src="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/thumbnails%2FB612_20170224_181319.jpg?alt=media&token=dfd23fa4-211f-41ae-86c2-53f7f88f020e" data-fullsrc="https://firebasestorage.googleapis.com/v0/b/socialapp-cc534.appspot.com/o/fullsizes%2FB612_20170224_181319.jpg?alt=media&token=d430fc09-f9c5-4253-beb3-e3015041c5b6" data-desc="<3" />
            </div>
        </div>
    </body>
</html>
