//Cookie
const cookieArr = document.cookie.split("=")
const userId = cookieArr[1];

const registerForm = document.getElementById('register-form')

console.log(userId);


mapboxgl.accessToken = 'pk.eyJ1IjoiYWNvdmEwMDMiLCJhIjoiY2tiZnR4NHhiMHo1bDMwbXptbzVpa2ZqYiJ9.-3yiof0FhfqNoWeqMsa-dw';


var userLon, userLat;
if(window.currentLocation) {
    userLon = window.currentLocation.longitude;
    userLat = window.currentLocation.latitude;
} else {
    userLon = 43.16366490907967;
    userLat = -1.23501836322248;
}
const baseUrl = 'http://localhost:8080/api/v1/users';
const handleSubmit = async (e) =>{
  const response = await fetch(`${baseUrl}/${userId}`) 
    .catch(err => console.error(err.message))

  const userObject = await response.json()
  document.getElementById('displayName').innerText = userObject.displayName;
  document.getElementById('steps').innerText = userObject.steps;

  // Calculate kms and distance_to_santiago here
  let steps = userObject.steps; // get the steps from userObject
  let kms = steps * 0.00076; // calculate kms
  let distance_to_santiago = 807 - kms; // calculate distance_to_santiago
  distance_to_santiago = Math.round(distance_to_santiago); // round the result

  // Display distance to Santiago
  document.getElementById('distance').innerText = `Distance to Santiago: ${distance_to_santiago} km`;

  // Display distance traveled
  document.getElementById('distanceTraveled').innerText = `Distance traveled: ${Math.round(kms)} km`;

  console.log(userObject);
  console.log("request done")};
handleSubmit();


var map = new mapboxgl.Map({
  container: 'map',
  style: 'mapbox://styles/acova003/ckbfuohe64i6o1in70tmp7rdt',
  // center will be the user location
  center: [userLon, userLat],
  zoom: 4.5
});

var geojson = {
  type: 'FeatureCollection',
  features: [{
    type: 'Feature',
    geometry: {
      type: 'Point',
      coordinates: [-8.544630, 42.880761]
    },
    properties: {
      title: 'Mapbox',
      description: 'Santiago de Compostela'
    }
  }]
};

var user_location = {
  type: 'FeatureCollection',
  features: [{
    type: 'Feature',
    geometry: {
      type: 'Point',
      coordinates: [userLon, userLat]
    },
    properties: {
      title: 'Mapbox',
      description: 'User progress on the camino'
    }
  }]
};


// add markers to map
geojson.features.forEach(function(marker) {

  // create a HTML element for each feature
  var el = document.createElement('div');
  el.className = 'santiago';

  // make a marker for each feature and add to the map
  new mapboxgl.Marker(el)
    .setLngLat(marker.geometry.coordinates)
    .addTo(map);
});

user_location.features.forEach(function(marker) {

  // create a HTML element for each feature
  var el = document.createElement('div');
  el.className = 'currentLocation';

  // make a marker for each feature and add to the map
  new mapboxgl.Marker(el)
    .setLngLat(marker.geometry.coordinates)
    .addTo(map);
});

map.addControl(new mapboxgl.NavigationControl());

const updateSteps = async (e) =>{
  e.preventDefault()

  const response = await fetch(`${baseUrl}/updateSteps/${userId}/${document.getElementById('steps').value}`, { method: 'POST' })
      .catch(err => console.error(err.message))

  const responseArr = await response.json()

  if (response.status === 200){
      window.location.replace('/trip.html');
  }
}


document.addEventListener('DOMContentLoaded', function(){
  var stepsForm = document.getElementById("steps-form"); 
  if(stepsForm) {
      stepsForm.addEventListener("submit", updateSteps);
  } else {
      console.log("Form not found!");
  }
});
