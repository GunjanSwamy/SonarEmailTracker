var token;

window.onload = GetCustomers_values();


function GetCustomers_values() {
  //console.log("I am in func");

  chrome.storage.local.get("authToken1", function (items) {
    if (chrome.runtime.lastError) {
      console.log('key mininput not found');
      return;
    }
    else {

      token = items["authToken1"];
      console.log("fecthed token");
      console.log(token);
      //GetCustomer(token, 'user-email');
    }


    let headers = new Headers();

    headers.set("Authorization", `Bearer ${token}`);

    fetch(`https://ec2-18-119-156-157.us-east-2.compute.amazonaws.com:9999/customer/emailID`, {
      method: 'GET',
      headers: headers,
    })

      .then(response => {

        if (response.status === 200) {

          return response.json();
        }
      })
      .then(resJson => {
        var customerFirstName = resJson.firstName;
        var customerTimeZone = resJson.timezone;
        var customerType = resJson.customerType;
          console.log(' IN MY PROFILE',customerType);
          //customerType = "NON_PREMIUM";
          //if (customerType == "NON_PREMIUM") {
              document.getElementById("user_name").innerHTML = customerFirstName;
              document.getElementById("user_time_zone").innerHTML = customerTimeZone;
              document.getElementById("user_plan").innerHTML = customerType;
          //}

      })
      .catch(error => {
        console.error(error);
      });

  });
}

