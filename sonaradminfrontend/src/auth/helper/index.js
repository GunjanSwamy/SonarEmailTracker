import { API } from "../../backend";

export const signup = (user) => {
  console.log(user);
  /*
 
  
*/
  let response = {};
  return fetch(`${API}/register`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      name: user.name,
      email: user.email,
      password: user.password,
    }),
  })
    .then((response) => {
      console.log(response);
      if (response.status == 200) {
        return response;
      }
      response.error = "Registration failed";
      return response;
    })
    .catch((err) => console.log(err));
};

export const signin = (user) => {
  let headers = new Headers();
  headers.set(
    "Authorization",
    "Basic " + btoa("adminClient" + ":" + "adminSecret")
  );
  let formData = new FormData();
  formData.append("grant_type", "password");
  formData.append("username", user.email);
  formData.append("password", user.password);

  return fetch(`${API}/oauth/token`, {
    method: "POST",
    headers: headers,
    body: formData,
  })
    .then(function (response) {
      console.log("resposen is", response);
      return response.json();
    })

    .catch((err) => console.log(err));
};

export const authenticate = (data, next) => {
  //console.log("sonar admin jwt is ", JSON.stringify(data));
  localStorage.setItem("sonarAdminJWT", JSON.stringify(data));
  next();
};

export const signout = (next) => {
  if (typeof window !== "undefined") {
    localStorage.removeItem("sonarAdminJWT");
    next();

    return fetch(`${API}/signout`, {
      method: "GET",
    })
      .then((response) => console.log("signout success"))
      .catch((err) => console.log(err));
  }
};

export const isAutheticated = () => {
  if (typeof window == "undefined") {
    return false;
  }
  if (
    localStorage.hasOwnProperty("sonarAdminJWT") &&
    localStorage.getItem("sonarAdminJWT")
  ) {
    //console.log(JSON.parse(localStorage.getItem("sonarAdminJWT")).access_token);
    let sonarJWT = localStorage.getItem("sonarAdminJWT");
    console.log(sonarJWT);
    if (sonarJWT) {
      return JSON.parse(localStorage.getItem("sonarAdminJWT")).access_token;
    }
  }
  return false;
};
