<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html> 
<head> 
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
  <title>Bus Routes And Way points</title> 
  <script src="http://maps.google.com/maps/api/js?sensor=false" 
          type="text/javascript"></script>
</head> 
<body>
    <div id="main" style="padding: -40px;">
        <div id="header" style="width:1326px;float: top;height:70px;background:#5B3256;margin-top: 0;margin-top: -7px;padding-bottom: 18px;border: 2px solid #a1a1a1;border-radius: 10px;">
            <h1 style="text-align: center;color: #f5f5f5">Bus Routes For Mumbai</h1>
        </div>
  <div id="map" style="width: 1000px; height: 518px;float: left;border: 2px solid #a1a1a1;border-radius: 10px;margin-top: 2px"></div>
  <div id="sidebar" style="width:322px;height:518px;float: right;background:#bce8f1;overflow: auto;border: 2px solid #a1a1a1;border-radius: 10px;margin-top: 2px;">
      <h1 style="text-align: center;">Bus Stands: </h1>
      <c:forEach items="${send}" var="p" varStatus="counter"> 
          <p style="text-align: center;font-family: sans-serif;font-size:80%;font-weight: bold;">${p.name}</p>
        </c:forEach>
  </div>
      <div id="footer" style="width:1326px;margin-top:526px; height:20px;background:#fff;margin-bottom: -55px">
            <h5 style="text-align: center;color: #000">Designed and Developed by kireet pant,dibyendu @IIIT-DELHI</h5>
        </div>
    </div>

  <script type="text/javascript">
      // This example creates a 2-pixel-wide red polyline showing
// the path of William Kingsford Smith's first trans-Pacific flight between
// Oakland, CA, and Brisbane, Australia.
var j=0;
  var flightPlanCoordinates = [];
  <c:forEach items="${send}" var="p" varStatus="counter"> 
          flightPlanCoordinates[j++]=new google.maps.LatLng(${p.lat}, ${p.lng});
  </c:forEach>
function initialize() {
  var mapOptions = {
    zoom: 13,
    center: flightPlanCoordinates[5],
    mapTypeId: google.maps.MapTypeId.TERRAIN
  };
  

  var map = new google.maps.Map(document.getElementById('map'),
      mapOptions);

   
  var marker, k;  
    for (k = 0; k < flightPlanCoordinates.length; k++) {  
      marker = new google.maps.Marker({
        position:flightPlanCoordinates[k] ,
        map: map
      });   

      
    }
  var flightPath = new google.maps.Polyline({
    path: flightPlanCoordinates,
    geodesic: true,
    strokeColor: '#FF0000',
    strokeOpacity: 1.0,
    strokeWeight: 2
  });

  flightPath.setMap(map);
}

google.maps.event.addDomListener(window, 'load', initialize);
  </script>
</body>
</html>