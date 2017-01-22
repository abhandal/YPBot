$( document ).ready(function() {
    var json = $.getJSON("assets/json/test.json", function(json) {
        console.log(json.listings[8].address);
        getListingName(json);
        getCity(json);

    });    
});
 


function getListingName(json) {
    
    var listingName = [];
    for(var i = 0; i<json.listings.length; i++) {
        listingName.push(json.listings[i].name);
    }
    var j = 1;

    for(var i = 0; i < listingName.length; i++)
        {
            if(j==9)
                j=2;
            if(listingName[i] != ""){
                text = '<article class="thumb"><a class="image" style="background-image: url(&quot;images/thumbs/0'+j+'.jpg&quot;); cursor: pointer; outline: 0px;"><img src="images/thumbs/0'+j+'.jpg" alt="" style="display: none;"></a><h2>'+listingName[i]+'</h2><p></p></article>'; 
                
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
    console.log(cityName);
}

