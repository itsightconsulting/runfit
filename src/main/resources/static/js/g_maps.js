function resizeMap(h) {
    var nh = (h-240);
    document.getElementById("map_area").style.height = (nh) + "px";
    document.getElementById("map_canvas").style.height = (nh) + "px";
    google.maps.event.trigger(map, "resize");
}
function getstartZoom(user_location) {
    var zooms = [
        ['Portugal', 7],
        ['Brasil', 4],
        ['United States', 4],
        ['United Kingdom', 6],
        ['India', 5],
        ['Canada', 4],
        ['Australia', 4],
        ['Italy', 6],
        ['Philippines', 6],
        ['Switzerland', 7],
        ['South Africa', 5]
    ];
    for (var i = 0; i < zooms.length; i++) {
        if (user_location == zooms[i][0]) {
            return ismobile == 1 ? zooms[i][1] - 1 : zooms[i][1];
        }
    }
    return 2;
}

function getUserCoutryCoords(user_location) {
    var coords = [
        ['Portugal',        {lat: 39.19, lng: -7.83}],
        ['Brasil',          {lat: -14.16, lng: -52.79}],
        ['United States',   {lat: 37.10, lng: -104.12}],
        ['United Kingdom',  {lat: 53.11, lng: -2.31}],
        ['India',           {lat: 21.57, lng: 81.08}],
        ['Canada',          {lat: 54.95, lng: -100.75}],
        ['Australia',       {lat: -24.51, lng: 137.43}],
        ['Italy',           {lat: 41.96, lng: 11.80}],
        ['Philippines',     {lat: 11.77, lng: 121.87}],
        ['Switzerland',     {lat: 46.60, lng: 8.25}],
        ['South Africa',    {lat: -28.93, lng: 24.23}]
    ];
    for (var i = 0; i < coords.length; i++) {
        if (user_location == coords[i][0]) {
            return coords[i][1];
        }
    }
    return null;
}

var url_points = null;
var u_location = 'Peru';
console.log("user location: " + u_location);
var start_point = null;
var start_zoom = getstartZoom(u_location); //0-20
var mapTypeId = 'hybrid';

function init_map() {
    var geocoder = new google.maps.Geocoder();
    geocoder.geocode({ 'address': u_location }, function(results, status){
        console.log(results);
        if (status == google.maps.GeocoderStatus.OK) {
            start_point = results[0].geometry.location;
            initialize();
        } else {
            new_user_location = getUserCoutryCoords(u_location);
            console.log("user location new: " + new_user_location);
            if (new_user_location) {
                start_point = new_user_location;
            } else {
                start_point = {lat: 24.38, lng: -42.61}; // all globe
                start_zoom = 4; // zoom all globe
            }

            initialize();
        }
    });
}

var input_circles = false;
var geocoder = null,
    map = null,
    lastClickTime, clckTimeOut, circles = [],
    active_circle = null,
    initial_radius = 100,
    loaded = !1;

function initialize() {
    var a = {
        zoom: start_zoom
        ,center: start_point
        ,mapTypeId: mapTypeId
        ,draggableCursor: "crosshair"
        ,tilt: 0,
        streetViewControl: false,
        fullscreenControl: false
        //,gestureHandling: 'greedy'
    };

    map = new google.maps.Map(document.getElementById("map_canvas"), a);
    geocoder = new google.maps.Geocoder;
    createPrototypes();

    geocoder = new google.maps.Geocoder;
    google.maps.event.addListener(map, "click", function(a) {
        mapClick(a.latLng);
    });
    google.maps.event.addListener(map, "dblclick", function(a) {
        mapClick(a.latLng);
    });
    input_circles && createInitialCircles(map, input_circles);
    loaded = !0;
    saveLink();
}

function mapClick(a) {
    var b = (new Date).getTime();
    if (10 > b - lastClickTime) return 0;
    lastClickTime = b;
    clckTimeOut ? (window.clearTimeout(clckTimeOut), clckTimeOut = null) : clckTimeOut = window.setTimeout(function() {
        singleClick(a)
    }, 500)
}

function singleClick(a) {
    window.clearTimeout(clckTimeOut);
    clckTimeOut = null;
    if (circles.length == 0) {
        createCircleTool(map, a, "Circle #" + circles.length);
    }
}

function createCircleTool(a, b, f, c) {
    var e = new DistanceWidget(a, b, f, c);displayInfo(e);
    google.maps.event.addListener(e, "distance_changed", function() {
        displayInfo(e);
        setInputRadius(e);
    });
    google.maps.event.addListener(e, "position_changed", function() {
        displayInfo(e);
    });
    circles.push(e);
    active_circle && active_circle.set("active", !1);
    active_circle = e;
    saveLink();
    loaded && 1 == circles.length && zoomToAllCircles()
}

function createInitialCircles(a, b) {
    len = b.length;
    for (i = 0; i < len; i++) {
        circle = b[i],
            point = new google.maps.LatLng(circle[1], circle[2]),
            createCircleTool(a, point, "", circle[0]);
    }
    loaded = !0;
    zoomToAllCircles();
}

function zoomToPoly() {
    zoomToAllCircles();
}

function DistanceWidget(a, b, f, c) {
    this.set("map", a);
    this.set("position", b);
    this.set("active", !0);
    this.set("name", f);
    a = new google.maps.Marker({
        draggable: !0,
        title: "Mover"
    });
    a.bindTo("map", this);
    a.bindTo("position", this);
    radius = initial_radius;
    c = new RadiusWidget(radius);
    this.set("radiusWidget", c);
    c.bindTo("map", this);
    c.bindTo("active", this);
    c.bindTo("center", this, "position");
    this.bindTo("distance", c);
    this.bindTo("bounds", c);
    var e = this;
    google.maps.event.addListener(a, "click", function() {
        active_circle.set("active", !1);
        e.set("active", !0);
        active_circle = e
    });
    google.maps.event.addListener(a, "dragend", function() {
        active_circle.set("active", !1);
        e.set("active", !0);
        active_circle = e
    })
}

function RadiusWidget(a) {
    var b = new google.maps.Circle({
        strokeWeight: 1,
        strokeColor: "#0000ff",
        fillColor: "#4285f4",
        fillOpacity: .5
    });
    this.set("circle", b);
    this.set("distance", a);
    this.bindTo("bounds", b);
    b.bindTo("center", this);
    b.bindTo("map", this);
    b.bindTo("radius", this);
    this.addSizer_()
}

function createPrototypes() {
    DistanceWidget.prototype = new google.maps.MVCObject;
    RadiusWidget.prototype = new google.maps.MVCObject;
    RadiusWidget.prototype.distance_changed = function() {
        this.set("radius", this.get("distance"))
    };
    RadiusWidget.prototype.addSizer_ = function() {
        var a = new google.maps.Marker({
            map: this.get("map"),
            draggable: !0,
            title: "Arrastrar"
        });
        this.set("sizer", a);
        a.bindTo("map", this);
        a.bindTo("position", this, "sizer_position");
        a.bindTo("active", this);
        var b = this;
        google.maps.event.addListener(a, "drag",
            function() {
                b.setDistance()
            });
        google.maps.event.addListener(a, "active_changed", function() {
            b.get("active") ? b.showSizer() : b.hideSizer()
        })
    };
    RadiusWidget.prototype.hideSizer = function() {
        if (sizer = this.get("sizer")) sizer.unbind("map"), sizer.setMap(null)
    };
    RadiusWidget.prototype.showSizer = function() {
        this.get("sizer") && (sizer = this.get("sizer"), sizer.bindTo("map", this))
    };
    RadiusWidget.prototype.center_changed = function() {
        var a = this.get("bounds");
        a && (a = a.getNorthEast().lng(), a = new google.maps.LatLng(this.get("center").lat(),
            a), this.set("sizer_position", a));
        saveLink()
    };
    RadiusWidget.prototype.distanceBetweenPoints_ = function(a, b) {
        return a && b ? d = google.maps.geometry.spherical.computeDistanceBetween(a, b) : 0
    };
    RadiusWidget.prototype.setDistance = function() {
        var a = this.get("sizer_position"),
            b = this.get("center"),
            a = this.distanceBetweenPoints_(b, a);
        this.set("distance", a);
        saveLink()
    }
}

function clearInfoDiv() {
    document.getElementById("status").innerHTML = '';
}
function displayInfo(a) {

    var d = a.get("distance"),
        h = d / 1E3,
        k = 6.21371E-4 * d,
        f = 3.28084 * d,
        c = "<b>Coordenadas:</b> " + a.get("position")
            + "<br/>"
            + "<b>Radio:</b> "
            + d.toFixed(2) + " Metros | "
            + h.toFixed(2) + " Km | "
            + k.toFixed(2) + " Millas | "
            + f.toFixed(0) + " Pies"
            +"<span id='RadioCirculo'>"+d.toFixed(2)+"</span>"
            +"<span id='Coordenadas'>"+ a.get("position")+"</span>";

    document.getElementById("status").innerHTML = c;
}

function setInputRadius(a) {
    meters = a.get("distance");
    input_value = 1E3;
    input_value = meters;
}

function deleteActiveCircle() {
    if (active_circle)
        for (active_circle.set("map", null), len = circles.length, i = 0; i < len; i++) {
            active_circle == circles[i] && (circles.splice(i, 1), active_circle = null)
        }
}

function clearMap() {
    deleteActiveCircle();
    $("#addressInput").val('');
    document.getElementById("status").innerHTML = '';
}

function saveLink() {
    if (loaded) {
        len = circles.length;
        data = [];
        for (i = 0; i < len; i++) {
            var a = circles[i];
            data[i] = [];
            data[i].push(parseFloat(a.get("radiusWidget").get("distance").toFixed(2)));
            data[i].push(parseFloat(a.get("position").lat().toFixed(7)));
            data[i].push(parseFloat(a.get("position").lng().toFixed(7)));
            data[i].push(a.get("radiusWidget").get("circle").get("fillColor"));
            data[i].push(a.get("radiusWidget").get("circle").get("strokeColor"));
            data[i].push(a.get("radiusWidget").get("circle").get("fillOpacity"))
        }
        circle_define_string =
            encodeURIComponent(JSON.stringify(data));
        url = 'https://www.calcmaps.com/es/map-radius/?';
        url += "&points=" + encodeURIComponent(circle_define_string);
        //$("#embed_url_div").show();
        //$("#embed_url").val(url);
    }
}

function zoomToAllCircles() {
    bounds = new google.maps.LatLngBounds;
    len = circles.length;
    data = [];
    for (i = 0; i < len; i++) {
        bounds.union(circles[i].get("radiusWidget").get("bounds"));
    }
    map.fitBounds(bounds)
}

setTimeout(() => {
    createPrototypes();
    google.maps.event.addListener(map, "click", function (a) {
        mapClick(a.latLng);
    });
}, 500);

