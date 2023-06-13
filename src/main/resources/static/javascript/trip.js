//Cookie
let userId = null;
let isMe = false;
const match = window.location.search.match(/userId=(\d+)/);
if (match) {
  userId = match[1];
} else {
  const cookieArr = document.cookie.split("=");
  userId = cookieArr[1];
  isMe = true;
}

let locations = null;

async function getLocations() {
  // Load locations.json at the start of your script
  let response = await fetch("/api/v1/trip/locations");
  let locations = await response.json();

  const registerForm = document.getElementById("register-form");

  console.log(userId);

  let userLon = -1.23501836322248;
  let userLat = 43.16366490907967;

  function calculateCurrentLocation(kms) {
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
      // Update user's location
      userLon = lon;
      userLat = lat;
    } else {
      console.log(
        "No previous location found for the current distance traveled."
      );
    }
  }
  const baseUrl = "http://localhost:8080/api/v1/users";
  const handleSubmit = async (e) => {
    const response = await fetch(`${baseUrl}/${userId}`).catch((err) =>
      console.error(err.message)
    );

    function capitalizeFirstLetter(string) {
      return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
    }

    const userObject = await response.json();
    document.getElementById("displayName").innerText = capitalizeFirstLetter(
      userObject.displayName
    );
    document.getElementById("steps").innerText = userObject.steps;

    // Calculate kms and distance_to_santiago here
    let steps = userObject.steps; // get the steps from userObject
    let kms = steps * 0.00076; // calculate kms
    calculateCurrentLocation(kms);
    let distance_to_santiago = 807 - kms; // calculate distance_to_santiago
    distance_to_santiago = Math.round(distance_to_santiago); // round the result

    // Display distance to Santiago
    document.getElementById(
      "distance"
    ).innerText = `Distance to Santiago: ${distance_to_santiago} km`;

    // Display distance traveled
    document.getElementById(
      "distanceTraveled"
    ).innerText = `Distance traveled: ${Math.round(kms)} km`;

    console.log(userObject);
    console.log("request done");
  };
  await handleSubmit();

  console.log("This is userLon: " + userLon);
  console.log("This is userLat: " + userLat);

  var map = new mapboxgl.Map({
    container: "map",
    style: "mapbox://styles/acova003/ckbfuohe64i6o1in70tmp7rdt",
    // center will be the user location
    center: [userLon, userLat],
    zoom: 12,
  });

  window.map = map;

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
          coordinates: [userLon, userLat],
        },
        properties: {
          title: "Mapbox",
          description: "Current Location",
          image: "current-location",
        },
      },
    ],
  };

  fetch(
    `https://api.opencagedata.com/geocode/v1/json?q=${userLat}+${userLon}&key=4a58715172234dccaac2e51bae09eac1`
  )
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      if (data.results && data.results.length > 0) {
        const city =
          data.results[0].components.city ||
          data.results[0].components.town ||
          data.results[0].components.village;
        console.log(city);
        if (city === undefined) {
          document.getElementById("city").innerText =
            "You're nearly at the next city!";
        } else if (city === "Cidade Vella") {
          document.getElementById("city").innerText = "Santiago de Compostela";
        } else {
          document.getElementById("city").innerText = city;
        }

        // add certificate if the city is santiago
        if (city === "Cidade Vella") {
          const certificateDiv = document.getElementById("certificate");
          certificateDiv.style.display = "";
        }

        // import cities json file
        fetch("../cities.json")
          .then((response) => response.json())
          .then((cityInfo) => {
            // Now you can use your data
            console.log(cityInfo);
            if (cityInfo[city]) {
              const featureDiv = document.getElementById("cityFeature");
              featureDiv.style.display = "";

              const description = cityInfo[city].description;
              const photoRef = cityInfo[city].image;
              const banner = cityInfo[city].banner;
              const credential = cityInfo[city].credential;
              document.getElementById("banner").src = banner;
              document.getElementById("description").innerText = description;
              document.getElementById("photoRef").src = photoRef;
              document.getElementById("credential").src = credential;
              console.log(cityInfo);
              // console.logc(cityInfo[city]);
            }
          })
          .catch((error) => console.error("Error:", error));

        document.getElementById("city").addEventListener("click", function () {
          // Create and show the popup when the element is clicked
          new mapboxgl.Popup({ offset: 25 })
            .setLngLat([userLon, userLat]) // Set the location of the popup
            .setHTML('<img src="' + photoRefUrl + '" />') // Set the content of the popup
            .addTo(map); // Add the popup to the map
        });
      }
    })
    .catch((error) => {
      console.error("Error:", error);
    });

  // add markers to map
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

  map.addControl(new mapboxgl.NavigationControl());

  const updateSteps = async (e) => {
    e.preventDefault();

    const response = await fetch(
      `${baseUrl}/updateSteps/${userId}/${
        document.getElementById("steps").value
      }`,
      { method: "POST" }
    ).catch((err) => console.error(err.message));

    const responseArr = await response.json();

    if (response.status === 200) {
      window.location.replace("/trip.html");
    }
  };

  const subscribe = async (e) => {
    e.preventDefault();

    const errorDiv = document.getElementById("error");
    errorDiv.textContent = "";

    try {
      const response = await fetch(
        `${baseUrl}/subscribe/${
          document.getElementById("subscribee").value
        }/${userId}`,
        { method: "POST" }
      );

      if (!response.ok) {
        const errortext = await response.text();
        throw new Error(errortext);
      }

      updateSubscriberList();
    } catch (err) {
      console.error(err.message);
      const errorDiv = document.getElementById("error");
      errorDiv.textContent = err.message;
      errorDiv.style.color = "red";
    }
  };

  const updateSubscriberList = async () => {
    const response = await fetch(`${baseUrl}/subscribees/${userId}`).catch(
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

  if (isMe) {
    updateSubscriberList();
    const subscriberDiv = document.getElementById("hidden");
    subscriberDiv.style.display = "";
  }
  var stepsForm = document.getElementById("steps-form");
  if (stepsForm) {
    stepsForm.addEventListener("submit", updateSteps);
  } else {
    console.log("Form not found!");
  }

  var subscribeForm = document.getElementById("subscribe-form");
  if (subscribeForm) {
    subscribeForm.addEventListener("submit", subscribe);
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

getLocations();
