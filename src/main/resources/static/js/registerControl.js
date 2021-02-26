var checkName, checkLastN, checkUser, checkP, checkPw2 = false;
var lowerCaseLetters = /[a-z]/g;
var upperCaseLetters = /[A-Z]/g;
var underscore = /_/g;
var number = /[0-9]/g;
function areAllValidInputs(){
    return window.checkName === true && window.checkLastN === true &&window.checkUser === true &&window.checkP === true &&window.checkPw2 === true;
}


function exists(id){
    return document.getElementById(id) !== null;
}

function manageButton() {
    if(areAllValidInputs()) {
        if (!exists("btn_reg")){
            var button = document.createElement("BUTTON");
            button.innerHTML="Registrati";
            button.id="btn_reg";
            button.className = "btn btn-primary";
            document.getElementById("myDiv").appendChild(button);
        }
    }else if(exists("btn_reg")){
        document.getElementById("btn_reg").remove();
    }
}

function nameFunction() {
    var name = document.getElementById("nome");

    if(name.value.match(lowerCaseLetters) || name.value.match(upperCaseLetters)) {
        document.getElementById("message1").innerHTML = "";
        window.checkName = true;
    }else {
        document.getElementById("message1").innerHTML = "Il nome può contenere solo maiuscole e minouscole";
        window.checkName = false;
    }
}

function lastnFunc() {
    var lastname = document.getElementById("cognome");
    if(lastname.value.match(window.lowerCaseLetters) || lastname.value.match(window.upperCaseLetters)) {
        document.getElementById("message2").innerHTML = "";
        window.checkLastN = true;
    }else {
        document.getElementById("message2").innerHTML = "Il cognome può contenere solo maiuscole e minouscole";
        window.checkLastN = false;
    }
}

function userFunction() {
    var user = document.getElementById("user");
    if(user.value.match(window.lowerCaseLetters) || user.value.match(window.upperCaseLetters) || user.value.match(window.number) || user.value.match(window.underscore)) {
        document.getElementById("message3").innerHTML = "";
        window.checkUser = true;
    }else {
        document.getElementById("message3").innerHTML = "Lo user può contenere solo maiuscole, minouscole, underscore (_) e numeri";
        window.checkUser = false;
    }
}

function pwFunction() {
    var password = document.getElementById("password");
    if(password.value.match(window.lowerCaseLetters) || password.value.match(window.upperCaseLetters) || password.value.match(window.number) || password.value.match(window.underscore)) {
        if(password.value.length >=8 && password.value.length <=15) {
            document.getElementById("message4").innerHTML = "";
            window.checkP = true;
        }else
        {
            document.getElementById("message4").innerHTML = "La password dev'essere lunga da 8 a 15 caratteri";
        }
    }else {
        document.getElementById("message4").innerHTML = "La password può contenere solo maiuscole, minouscole, underscore (_), numeri";
        window.checkP=false;
    }
}

function checkPw() {
    var pw1 = document.getElementById("password").value;
    var pw2 = document.getElementById("confirmPassword").value;
    if(pw1 === pw2) {
        document.getElementById("message5").innerHTML = "";
        window.checkPw2 = true;
    }else {
        document.getElementById("message5").innerHTML = "Le password devono essere identiche";
        window.checkPw2=false;
    }
}
function category(input) {
    var context = document.querySelector("base").getAttribute("href");
    var url = "";
    if(input.value === "All")
        url = context + "items";
    else
        url = context + "item/search/category?cat="+input.value;
    var options = {method : "GET"};
    fetch(url, options)
        .then(function(response){
            if(!response.ok)
                throw new Error("HTTP error request: " + response.status);
            return response.json();
        })
        .then(function(elements){
            var pars="";
            var container = document.getElementById("products");
            for (var i = 0; i < elements.length; i++) {
                pars += "<div class=\"item col-xs-4 col-lg-4\">\n" +
                    "                <div id=\"single_prod\"  class=\"thumbnail card\">\n" +
                    "                    <!-- IMAGE -->\n" +
                    "                    <div align=\"center\">\n" +
                    "                        <img class=\"card-img-top\" width=\"100%\" height=\"250\" preserveaspectratio=\"xMidYMid slice\" focusable=\"false\" role=\"img\" alt=\"\" src=\""+"item/"+elements[i].id+"/image\">\n" +
                    "                    </div>\n" +
                    "                    <hr>\n" +
                    "                    <!-- FIELDS -->\n" +
                    "                    <div class=\"caption card-body\">\n" +
                    "                        <h4 class=\"group card-title inner list-group-item-heading\">"+elements[i].title+"</h4>\n" +
                    "                        <p class=\"group inner list-group-item-text\"> "+elements[i].description+ "</p>\n" +
                    "                        <!-- OTHER FIELDS & SHOW -->\n" +
                    "                        <div class=\"row\">\n" +
                    "                            <div class=\"col-xs-12 col-md-6\">\n" +
                    "                                <p class=\"lead\">\n" +
                    "                                    <time>"+new Date(elements[i].date).toLocaleDateString("it-IT")+ "</time>\n" +
                    "                                </p>\n" +
                    "                            </div>\n" +
                    "                            <button type=\"submit\" class=\"btn btn-outline-success\" onclick=\"location.href='"+context+"item/"+elements[i].id+"'\" >Visualizza</button>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "                <br>\n" +
                    "            </div>"
            }
            container.innerHTML = pars;
        })
}

function search(input) {
    if (input.value.length < 3 && input.value.length > 0)
        return;
    var context = document.querySelector("base").getAttribute("href");
    // var getUrl = window.location;
    // var baseUrl = getUrl.protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];
    // var url = baseUrl + "item/search?q="+input.value;
    var url = context + "item/search?q="+input.value;
    var options = {method : "GET"};
    fetch(url, options)
        .then(function(response){
            if(!response.ok)
                throw new Error("HTTP error request: " + response.status);
            return response.json();
        })
        .then(function(elements){
            var pars="";
            var container = document.getElementById("products");
            for (var i = 0; i < elements.length; i++) {
                pars += "<div class=\"item col-xs-4 col-lg-4\">\n" +
                    "                <div id=\"single_prod\"  class=\"thumbnail card\">\n" +
                    "                    <!-- IMAGE -->\n" +
                    "                    <div align=\"center\">\n" +
                    "                        <img class=\"card-img-top\" width=\"100%\" height=\"250\" preserveaspectratio=\"xMidYMid slice\" focusable=\"false\" role=\"img\" alt=\"\" src=\""+"item/"+elements[i].id+"/image\">\n" +
                    "                    </div>\n" +
                    "                    <hr>\n" +
                    "                    <!-- FIELDS -->\n" +
                    "                    <div class=\"caption card-body\">\n" +
                    "                        <h4 class=\"group card-title inner list-group-item-heading\">"+elements[i].title+"</h4>\n" +
                    "                        <p class=\"group inner list-group-item-text\"> "+elements[i].description+ "</p>\n" +
                    "                        <!-- OTHER FIELDS & SHOW -->\n" +
                    "                        <div class=\"row\">\n" +
                    "                            <div class=\"col-xs-12 col-md-6\">\n" +
                    "                                <p class=\"lead\">\n" +
                    "                                    <time>"+new Date(elements[i].date).toLocaleDateString("it-IT")+ "</time>\n" +
                    "                                </p>\n" +
                    "                            </div>\n" +
                    "                            <button type=\"submit\" class=\"btn btn-outline-success\" onclick=\"location.href='"+context+"item/"+elements[i].id+"'\" >Visualizza</button>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "                <br>\n" +
                    "            </div>"
            }
            container.innerHTML = pars;
        })

}

