$(document).ready(function() {
    var url = getUrlVars()["userid"];

   var json = $.getJSON("res/"+url+".json", function(json) {
        console.log(json.listings[8].address);
        getListingName(json);
        getCity(json);

   });    
});

function getUrlVars()
{
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}

function getListingName(json) {
    
    var listingName = [];
    for(var i = 0; i<json.listings.length; i++) {
        listingName.push(json.listings[i].name);
    }
    var j = 1;
    var cityName = getCity(json);

   for(var i = 0; i < listingName.length; i++)
        {
            if(j==9)
                j=2;
            if(listingName[i] != "" &cityName[i].city != "" ){
                text = '<article class="thumb"><a class="image" style="background-image: url(&quot;images/thumbs/0'+j+'.jpg&quot;); cursor: pointer; outline: 0px;"><img src="images/thumbs/0'+j+'.jpg" alt="" style="display: none;"></a><h2>'+listingName[i]+'</h2><h2>'+cityName[i].street+' '+cityName[i].city+', ' +cityName[i].prov + ' ' +cityName[i].pcode +'<p></p></article>';
                
                $("#main").append(text);
            }
            j++;
        }
    console.log(listingName);
}

function getCity(json) {
    var cityName = [];
    for(var i=0; i<json.listings.length; i++) {
        cityName.push(json.listings[i].address)
    }
    return cityName;
    console.log(cityName);
}