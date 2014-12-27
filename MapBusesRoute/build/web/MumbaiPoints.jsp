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
    <div id="main">
  <div id="map" style="width: 1000px; height: 600px;float: left"></div>
  
  <script type="text/javascript">
      // This example creates a 2-pixel-wide red polyline showing
// the path of William Kingsford Smith's first trans-Pacific flight between
// Oakland, CA, and Brisbane, Australia.

function initialize() {
  var mapOptions = {
    zoom: 13,
    center: new google.maps.LatLng(18.9750, 72.8258),
    mapTypeId: google.maps.MapTypeId.TERRAIN
  };
  

  var map = new google.maps.Map(document.getElementById('map'),
      mapOptions);
var j=0;
  var flightPlanCoordinates = [];
  <c:forEach items="${send}" var="p" varStatus="counter"> 
          flightPlanCoordinates[j++]=new google.maps.LatLng(${p.lat}, ${p.lng});
  </c:forEach>
   
  var marker, k;  
    for (k = 0; k < flightPlanCoordinates.length; k++) {  
      marker = new google.maps.Marker({
        position:flightPlanCoordinates[k] ,
        map: map,
        
      });   

      
    }
 

  flightPath.setMap(map);
}

google.maps.event.addDomListener(window, 'load', initialize);
  </script>
</body>
</html>