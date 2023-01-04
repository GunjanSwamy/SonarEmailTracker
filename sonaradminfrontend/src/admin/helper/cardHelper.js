import { isAutheticated } from "../../auth/helper";
import { API } from "../../backend";

export const changeCustomerType = (customerItem, next) => {
  // convert the customer
  console.log(customerItem);
  console.log("lalala");
  //next();

  let formData = new FormData();
  var obj = new Object();

  formData.append("customerEmail", customerItem.email);
  obj.customerEmail = customerItem.email;
  if (customerItem.customerType === "NON_PREMIUM") {
    formData.append("customerTypeTarget", "PREMIUM");
    obj.customerTypeTarget = "PREMIUM";
  } else {
    formData.append("customerTypeTarget", "NON_PREMIUM");
    obj.customerTypeTarget = "NON_PREMIUM";
  }

  console.log("formData ");
  console.log(JSON.stringify(formData));
  let token = isAutheticated();
  console.log("getCustoemrs token is " + token);
  let headers = new Headers();
  headers.set("Authorization", `Bearer ` + token);
  headers.set("Content-Type", "application/json");

  return fetch(`${API}/admin/convertCustomer`, {
    method: "PUT",
    headers: headers,
    body: JSON.stringify(obj),
  }).then((response) => {
    if (response.status === 200) {
      console.log("converted", response);
      window.location.reload();
      return response.json();
    } else {
      throw new Error("Something went wrong on api server!");
    }
  });
};
