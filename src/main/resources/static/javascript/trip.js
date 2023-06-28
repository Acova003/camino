const DEFAULT_LON = -1.23501836322248;
const DEFAULT_LAT = 43.16366490907967;
const BASE_URL = "http://localhost:8080/api/v1/users";

async function main() {
  const currentUser = getUser();
  const locations = await getLocations();
  const serverUser = await getServerUser(currentUser.id);
  const distance_to_santiago = getDistanceToSantiago(serverUser.kms);

  updateUserInfo(serverUser, distance_to_santiago);

  const currentLocation = calculateCurrentLocation(locations, serverUser.kms);
  const currentCity = await getCityByLocation(currentLocation);

  const map = createMap(currentLocation);
  showMapFeatures(map, currentLocation);

  const cities = await getCitiesData();
  const citiesDeco = getCityDecorations(cities, currentCity);

  updateCityInfo(currentCity, citiesDeco);

  if (currentUser.isMe) {
    showSubscriberList();
    updateSubscriberList(currentUser);
  }

  setupForms(currentUser);
}

main();

function getUser() {
  const match = window.location.search.match(/userId=(\d+)/);
  if (match) {
    return { id: match[1], isMe: false };
  } else {
    const cookieArr = document.cookie.split("=");
    return { id: cookieArr[1], isMe: true };
  }
}

async function getServerUser(userId) {
  const response = await fetch(`${BASE_URL}/${userId}`);
  const userObject = await response.json();

  const steps = userObject.steps;
  const kms = steps * 0.00076;

  userObject.kms = kms;

  return userObject;
}

function createMap([lon, lat], zoom) {
  var map = new mapboxgl.Map({
    container: "map",
    style: "mapbox://styles/acova003/ckbfuohe64i6o1in70tmp7rdt",
    // center will be the user location
    center: [lon, lat],
    zoom: zoom || 12,
  });

  map.addControl(new mapboxgl.NavigationControl());

  window.map = map;

  return map;
}

function showMapFeatures(map, [lon, lat]) {
  var geojson = {
    type: "FeatureCollection",
    features: [
      {
        type: "Feature",
        geometry: {
          type: "Point",
          coordinates: [-8.54463, 42.880761],
        },
        properties: {
          title: "Mapbox",
          description: "Santiago de Compostela",
          image: "santiago",
        },
      },
      {
        type: "Feature",
        geometry: {
          type: "Point",
          coordinates: [-6.361367369309478, 42.48920595281204],
        },
        properties: {
          title: "Mapbox",
          description: "Cruz de Ferro",
          image: "cruz-de-ferro",
        },
      },
      {
        type: "Feature",
        geometry: {
          type: "Point",
          coordinates: [lon, lat],
        },
        properties: {
          title: "Mapbox",
          description: "Current Location",
          image: "current-location",
        },
      },
    ],
  };

  geojson.features.forEach(function (marker) {
    // create a HTML element for each feature
    var el = document.createElement("div");
    el.className = marker.properties.image;

    // make a marker for each feature and add to the map
    new mapboxgl.Marker(
      el,
      marker.properties.image == "santiago" ||
      marker.properties.image == "current-location"
        ? { anchor: "bottom" }
        : undefined
    )
      .setLngLat(marker.geometry.coordinates)
      .addTo(map);
  });
}

async function getLocations() {
  let response = await fetch("/api/v1/trip/locations");
  let locations = await response.json();

  return locations;
}

async function getCitiesData() {
  const response = await fetch("../cities.json");
  const citiesData = await response.json();

  return citiesData;
}

function getCityDecorations(cities, cityName) {
  return cities[cityName];
}

function calculateCurrentLocation(locations, kms) {
  let previousKey = null;
  let previousValue = null;
  Object.keys(locations).forEach(function (key) {
    let keyAsNumber = Number(key);
    if (keyAsNumber < kms) {
      previousKey = key;
      previousValue = locations[key];
    } else {
      // stop iteration
      return false;
    }
  });

  if (previousValue !== null) {
    // use previousValue (which is the latitude and longitude)
    let lat = previousValue.lat;
    let lon = previousValue.lon;

    return [lon, lat];
  } else {
    throw new Error(
      "No previous location found for the current distance traveled."
    );
  }
}

async function getCityByLocation([lon, lat]) {
  const response = await fetch(
    `https://api.opencagedata.com/geocode/v1/json?q=${lat}+${lon}&key=4a58715172234dccaac2e51bae09eac1`
  );
  const data = await response.json();

  if (data.results && data.results.length > 0) {
    const city =
      data.results[0].components.city ||
      data.results[0].components.town ||
      data.results[0].components.village;

    if (city === "Cidade Vella") {
      return "Santiago de Compostela";
    } else {
      return city;
    }
  }
}

function getDistanceToSantiago(kms) {
  let distance_to_santiago = 807 - kms;
  return Math.round(distance_to_santiago);
}

function capitalizeFirstLetter(string) {
  if (!string) return "";

  return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
}

function updateUserInfo(userObject, distance_to_santiago) {
  document.getElementById("displayName").innerText = capitalizeFirstLetter(
    userObject.displayName
  );
  document.getElementById("steps").innerText = userObject.steps;

  // Display distance to Santiago
  document.getElementById(
    "distance"
  ).innerText = `Distance to Santiago: ${distance_to_santiago} km`;

  // Display distance traveled
  document.getElementById(
    "distanceTraveled"
  ).innerText = `Distance traveled: ${Math.round(userObject.kms)} km`;
}

function updateCityInfo(cityName, cityDeco) {
  if (cityName) {
    document.getElementById("city").innerText = cityName;

    if (city === "Santiago de Compostela") {
      const certificateDiv = document.getElementById("certificate");
      certificateDiv.style.display = "";
    }

    if (cityDeco) {
      const featureDiv = document.getElementById("cityFeature");
      featureDiv.style.display = "";

      document.getElementById("banner").src = cityDeco.banner;
      document.getElementById("description").innerText = cityDeco.description;
      document.getElementById("photoRef").src = cityDeco.image;
      document.getElementById("credential").src = cityDeco.credential;
    }
  } else {
    document.getElementById("city").innerText =
      "You're nearly at the next city!";
  }
}

function createCityPopup(map, location, cityDeco) {
  document.getElementById("city").addEventListener("click", function () {
    // Create and show the popup when the element is clicked
    new mapboxgl.Popup({ offset: 25 })
      .setLngLat(location) // Set the location of the popup
      .setHTML('<img src="' + cityDeco.image + '" />') // Set the content of the popup
      .addTo(map); // Add the popup to the map
  });
}

const updateSteps = async (e, currentUser) => {
  e.preventDefault();

  const response = await fetch(
    `${BASE_URL}/updateSteps/${currentUser.id}/${
      document.getElementById("steps").value
    }`,
    { method: "POST" }
  ).catch((err) => console.error(err.message));

  const responseArr = await response.json();

  if (response.status === 200) {
    window.location.replace("/trip.html");
  }
};

const subscribe = async (e, currentUser) => {
  e.preventDefault();

  const errorDiv = document.getElementById("error");
  errorDiv.textContent = "";

  try {
    const response = await fetch(
      `${BASE_URL}/subscribe/${document.getElementById("subscribee").value}/${
        currentUser.id
      }`,
      { method: "POST" }
    );

    if (!response.ok) {
      const errortext = await response.text();
      throw new Error(errortext);
    }

    updateSubscriberList(currentUser);
  } catch (err) {
    const errorDiv = document.getElementById("error");
    errorDiv.textContent = err.message;
    errorDiv.style.color = "red";
  }
};

const updateSubscriberList = async (user) => {
  const response = await fetch(`${BASE_URL}/subscribees/${user.id}`).catch(
    (err) => console.error(err.message)
  );

  // get the subscriber list from the response
  const subscriptionList = await response.json();
  if (subscriptionList.length > 0) {
    document.getElementById("subscriberListHeader").style.display = "block";

    let subscriberUL = document.getElementById("subscriberList");

    subscriberUL.innerHTML = "";

    for (const subscription of subscriptionList) {
      let button = document.createElement("button");
      button.textContent = subscription.subscribee.displayName;

      button.addEventListener("click", function () {
        window.location.href = `/trip.html?userId=${subscription.subscribee.id}`;
      });

      button.className = "btn btn-light me-2";

      subscriberUL.appendChild(button);

      // html += `<button><id="subscriberButton" class="subscriber" a href="/trip.html?userId=${subscription.subscribee.id}">${subscription.subscribee.displayName}</a></button>`;
    }
  }

  // update the subscriber list on the page
  // document.getElementById("subscriberList").innerHTML = html;
};

function setupForms(currentUser) {
  var stepsForm = document.getElementById("steps-form");
  if (stepsForm) {
    stepsForm.addEventListener("submit", function (e) {
      updateSteps(e, currentUser);
    });
  } else {
    console.log("Form not found!");
  }

  var subscribeForm = document.getElementById("subscribe-form");
  if (subscribeForm) {
    subscribeForm.addEventListener("submit", function (e) {
      subscribe(e, currentUser);
    });
  } else {
    console.log("Form not found!");
  }

  var logoutButton = document.getElementById("logOut");
  logoutButton.addEventListener("click", function () {
    document.cookie = "userId=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    window.location.replace("/");
  });

  var dashboardButton = document.getElementById("dashboard");
  logoutButton.addEventListener("click", function () {
    document.cookie = "userId=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    window.location.replace("/trip.html");
  });
}

function showSubscriberList() {
  const subscriberDiv = document.getElementById("hidden");
  subscriberDiv.style.display = "";
}
