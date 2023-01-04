import { isAutheticated } from "../../auth/helper/index";
import { API } from "../../backend";
// export const getToken = () => {
//   return JSON.parse(localStorage.getItem("sonarAdminJWT"))["access_token"];
// };

export const getCustomers = () => {
  let token = isAutheticated();
  console.log("getCustoemrs token is " + token);
  let headers = new Headers();
  headers.set("Authorization", `Bearer ` + token);

  return (
    fetch(`${API}/admin/getAllCustomers`, {
      method: "GET",
      headers: headers,
    })
      .then((response) => {
        if (response.status === 200) {
          console.log("getallcustomer", response);
          return response.json();
        } else {
          throw new Error("Something went wrong on api server!");
        }
      })
      // .then((data) => {
      //   console.log(data.response);
      // })
      .catch((error) => {
        console.error(error);
      })
  );
};
